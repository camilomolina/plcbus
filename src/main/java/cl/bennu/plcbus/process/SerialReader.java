package cl.bennu.plcbus.process;

import cl.bennu.plcbus.comm.PLCBusController;
import cl.bennu.plcbus.constant.Constant;
import cl.bennu.plcbus.domain.DataMessage;
import cl.bennu.plcbus.domain.Message;
import cl.bennu.plcbus.enums.CommandEnum;
import cl.bennu.plcbus.enums.ProtocolEnum;
import cl.bennu.plcbus.helper.PLCBusHelper;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 29-06-13
 * Time: 03:40 AM
 */
public class SerialReader extends Thread {
    private InputStream inputStream;
    private boolean running = true;
    private static final Map<String, Message> hash = new HashMap<String, Message>();
    private static Long restartCount = 0L;

    //private MessageOrquester messageOrquester;

    public SerialReader(InputStream in) {
        this.inputStream = in;
        //this.messageOrquester = messageOrquester;
    }

    public void run() {
        System.out.println("Started SerialReader");

        byte[] buffer = new byte[1];
        byte byteReader;

        int len;
        try {
            int step = 0;
            DataMessage data = new DataMessage();
            Message message = new Message(data);

            long time = 0L;
            int i = 1;
            while (running && (len = this.inputStream.read(buffer)) > -1) {
                boolean finish = false;
                if (len > 0) {
                    byteReader = buffer[0];
                    //System.out.println("Analisis -" + buffer.length + "- :: step={" + step + "}, byte={" + byteReader + "}");
                    switch (step) {
                        case 0:
                            if (byteReader == ProtocolEnum.STX.getCode()) {
                                data = new DataMessage();
                                message = new Message(data);

                                // tiempo en que se recibe primer bit
                                time = new Date().getTime();

                                step = 0;
                                message.setStx(byteReader);
                                step++;
                            }
                            break;
                        case 1:
                            message.setLength(byteReader);
                            step++;
                            break;
                        case 2:
                            data.setUserCode(byteReader);
                            step++;
                            break;
                        case 3:
                            data.setHomeUnit(byteReader);
                            step++;
                            break;
                        case 4:
                            data.setCommand(byteReader);
                            step++;
                            break;
                        case 5:
                            data.setDataOne(byteReader);
                            step++;
                            break;
                        case 6:
                            data.setDataTwo(byteReader);
                            step++;
                            break;
                        case 7:
                            data.setRxtxSwitch(byteReader);
                            step++;
                            break;
                        case 8:
                            message.setEtx(byteReader);
                            finish = true;
                            step = 0;

                            //this.inputStream.close();
                            break;
                    }

                    if (finish) {
                        BitSet bitSet = PLCBusHelper.fromByte(message.getDataMessage().getRxtxSwitch());
                        System.out.println("READER : " + message + " " + bitSet + "::" + Integer.toBinaryString(message.getDataMessage().getRxtxSwitch()) + "::");
                        if (message.getLength() == ProtocolEnum.LENGTH_RECEIVED.getCode()) {
                               /*
                                  DATA_RX_TX_SWITCH
                                  R_ID_SW BIT 6 - El ID de retroalimentación de datos recibidos se guardan en DATOS1 DATOS2
                                            1:Terminado de recibir el ID de señal
                                            0:No llego ID de señal

                                  R_ACK_SW BIT 5 - Bit de ACK
                                            1:ACK Recibida
                                            0:No llego ACK

                                  R_ITSELF BIT 4 - Bit significa que la señal transmitida es recibida por sí misma con éxito
                                            1:Señal PLCBus se transmite a si mismo
                                            0:Señal PLCBus se transmite desde el exterior

                                  R_RISC BIT 3 - Bit para la transferencia entre PLCBus, "1" es la señal PLCBus puede ser transferidos a Protocolo RISC
                                            1:Concuerda con el comando
                                            0:NO concuerda con el comando

                                  R_SW BIT 2 - Bit de respuesta PLCBus
                                            1:Recibe correctamente
                                            0:No recibe

                                */

                            //if (!bitSet.get(4) || bitSet.get(6)) {
                            if (!bitSet.get(4)) {
                                if (bitSet.get(6)) {
                                    // status masivo
                                    hash.put(PLCBusHelper.createKey(message), message);
                                } else if (bitSet.get(2) && bitSet.get(3)) {
                                    // status
                                    hash.put(PLCBusHelper.createKey(message), message);
                                }
                            }
                        }
                    }
                }

                if (finish) {
                    //System.out.println("Revision de finish");
                    // el set de bits no deben tardar mas de 1 segundo, de ser asi, se perdio un bit y el
                    // mensaje esta corrupto, tipicamente este falla esta en el plcbus 1141, y debe ser reconectado
                    // al puerto de comunicaciones (rs232)
                    long t2 = new Date().getTime();
                    //System.out.println("time : " + time + ", t2 : " + t2 + ", if : " + (time != 0 && time + 1000L < t2) + " ");
                    if (time != 0 && time + 1000L < t2) {
                        System.out.println("Limpiando hash y reiniciando plc desde serialReader, instancia" + this.hashCode());

                        // contador de reinicios
                        restartCount++;

                        // limpia map's
                        hash.clear();

                        // indica que debera reiniciarse el plcbus
                        PLCBusController.restart = Boolean.TRUE;
                        try {
                            System.out.println("Reiniciando plc : " + PLCBusController.restart);
                            PLCBusController.getInstance(PLCBusController.PORT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        } catch (Exception e) {
            //..
        }
    }

    public Message receivedMessage(Message message, int cycle) {
        String key = PLCBusHelper.createKey(message);

        int i = 0;
        while (true) {
            if (hash.containsKey(key)) {
                return hash.remove(key);
            } else {
                // quebramos de inmediato para el chequeo de sensores que se hace en unidades
                // de tiempo muy bajas
                if (cycle == 0) return null;
                try {
                    TimeUnit.SECONDS.sleep(2);
                    //Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i == cycle) return null;
                /*
                if ((time + 5) < (System.currentTimeMillis() / 1000)) {
                    return null;
                }
                */
            }
            i++;
        }
    }

    public void stop(Message message) {
        running = false;
    }

    public Map viewMessageQueue() {
        return hash;
    }

    public Long restartPLCBusCount() {
        return restartCount;
    }
}
