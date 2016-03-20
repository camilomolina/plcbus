package cl.bennu.plcbus.comm;

import cl.bennu.plcbus.domain.*;
import cl.bennu.plcbus.enums.CommandEnum;
import cl.bennu.plcbus.helper.PLCBusHelper;
import cl.bennu.plcbus.listener.CommListener;
import cl.bennu.plcbus.process.SerialReader;
import cl.bennu.plcbus.process.SerialWriter;
import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TooManyListenersException;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 29-06-13
 * Time: 04:00 AM
 */
public class PLCBusController implements IPLCBusController {

    private static final int BPS = 9600;
    private static SerialReader serialReader;
    private static SerialWriter serialWriter;
    public static String PORT;
    private static CommPort COMMPORT;
    public static Boolean restart = Boolean.FALSE;
    //private MessageOrquester messageOrquester = new MessageOrquester();

    //private static SoftReference<IPLCBusController> instance = null;
    private static IPLCBusController instance;

    public static synchronized IPLCBusController getInstance(String port) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException, InterruptedException {
        if (instance == null) instance = new PLCBusController(port);
        if (restart) {
            COMMPORT.disableReceiveFraming();
            COMMPORT.disableReceiveThreshold();
            COMMPORT.disableReceiveTimeout();
            COMMPORT.close();

            System.out.println("(1) Al momento de reiniciar la cola tenia : " + serialWriter.queue.size() + " elementos");
            Iterator<Message> iterator = serialWriter.queue.iterator();
            System.out.println("(2) Al momento de reiniciar la cola tenia : " + serialWriter.queue.size() + " elementos");

            serialWriter.stop(new Message());
            serialReader.stop(new Message());

            //serialWriter.stop();
            //serialReader.stop();

            serialWriter.interrupt();
            serialReader.interrupt();

            serialWriter = null;
            serialReader = null;

            instance = new PLCBusController(port);

            // recupera mensajes que estaban en cola
            while(serialWriter != null && iterator != null && iterator.hasNext()) {
                Message message = iterator.next();
                System.out.println("Recuperando mensaje : " + message);
                serialWriter.queue.offer(message);
            }

            System.out.println("Reiniciando PLCBus");
            restart = Boolean.FALSE;
        }
        return instance;
    }

    private PLCBusController(String port) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException, InterruptedException {
        //PORT = port;
        System.out.println("PortIdentifiers : " + CommPortIdentifier.getPortIdentifiers());
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(port);
        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Error: Port is currently in use");
        } else {
            COMMPORT = portIdentifier.open(this.getClass().getName(), 2000);
            PORT = port;

            if (COMMPORT instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) COMMPORT;
                serialPort.setSerialPortParams(BPS, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                //serialPort.notifyOnDataAvailable(true);
                //serialPort.disableReceiveFraming();

                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();

                /*
                CommListener commListener = new CommListener();
                commListener.inputStream = in;
                try {
                    //serialPort.addEventListener(commListener);
                } catch (Exception e) {
                    System.out.println("Error en listener");
                }
                */
                serialReader = new SerialReader(in);
                serialWriter = new SerialWriter(out);

                serialWriter.start();
                serialReader.start();

                //serialWriter.join();
                //serialReader.join();
            } else {
                System.out.println("Error: Only serial ports.");
            }
        }
    }

    @Override
    public Long noiseLevel(String homeUnit) {
        byte hu = PLCBusHelper.getHomeUnit().get(homeUnit);

        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setHomeUnit(hu);
        message.getDataMessage().setCommand(CommandEnum.GET_NOISE_STRENGTH.getCode());

        Long result = 0L;

        Boolean response = serialWriter.sendMessage(message);
        if (response) {
            Message messageReceived = serialReader.receivedMessage(message, 3);

            if (messageReceived != null) {
                result = (long) messageReceived.getDataMessage().getDataOne();
            }
        }

        return result;
    }

    @Override
    public void erase() {
        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setCommand(CommandEnum.ALL_SCENES_ADDR_ERASE.getCode());

        serialWriter.sendMessage(message);
    }

    @Override
    public void scenesAddressErase(String homeUnit) {
        byte hu = PLCBusHelper.getHomeUnit().get(homeUnit);

        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setHomeUnit(hu);
        message.getDataMessage().setCommand(CommandEnum.ALL_SCENES_ADDR_ERASE.getCode());

        serialWriter.sendMessage(message);
    }

    @Override
    public Long signalLevel(String homeUnit) {
        byte hu = PLCBusHelper.getHomeUnit().get(homeUnit);

        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setHomeUnit(hu);
        message.getDataMessage().setCommand(CommandEnum.GET_SIGNAL_STRENGTH.getCode());

        Long result = 0L;

        Boolean response = serialWriter.sendMessage(message);
        if (response) {
            Message messageReceived = serialReader.receivedMessage(message, 3);

            if (messageReceived != null) {
                result = (long) messageReceived.getDataMessage().getDataOne();
            }
        }

        return result;
    }

    @Override
    public StatusResponse status(String homeUnit) {
        return status(homeUnit, 3);
    }

    @Override
    public StatusResponse status(String homeUnit, Boolean massive) {
        if (massive) {
            return status(homeUnit, 5);
        } else {
            return status(homeUnit);
        }
    }

    private StatusResponse status(String homeUnit, int cycle) {
        byte hu = PLCBusHelper.getHomeUnit().get(homeUnit);

        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setHomeUnit(hu);
        message.getDataMessage().setCommand(CommandEnum.STATUS_REQUEST.getCode());

        StatusResponse statusResponse = null;
        Boolean status = false;
        Long brightnessLevel = null;
        Long dimmerFadeRate = null;

        Boolean response = serialWriter.sendMessage(message);
        if (response) {
            statusResponse = new StatusResponse();
            Message messageReceived = serialReader.receivedMessage(message, cycle);

            if (messageReceived != null) {
                if (messageReceived.getDataMessage().getCommand() == CommandEnum.STATUS_ON.getCode()) {
                    status = true;
                }
                brightnessLevel = (long) messageReceived.getDataMessage().getDataOne();
                dimmerFadeRate = (long) messageReceived.getDataMessage().getDataTwo();
            }

            statusResponse.setStatus(status);
            statusResponse.setBrightLevel(brightnessLevel);
            statusResponse.setDimmerFadeRate(dimmerFadeRate);
        }
        return statusResponse;
    }

    @Override
    public void off(String homeUnit) {
        byte hu = PLCBusHelper.getHomeUnit().get(homeUnit);

        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setHomeUnit(hu);
        message.getDataMessage().setCommand(CommandEnum.OFF.getCode());

        serialWriter.sendMessage(message);
    }

    @Override
    public void on(String homeUnit) {
        byte hu = PLCBusHelper.getHomeUnit().get(homeUnit);

        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setHomeUnit(hu);
        message.getDataMessage().setCommand(CommandEnum.ON.getCode());

        serialWriter.sendMessage(message);
    }

    @Override
    public void onAll() {
        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setCommand(CommandEnum.ALL_USER_LIGHTS_ON.getCode());

        serialWriter.sendMessage(message);
    }

    @Override
    public void offAll() {
        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setCommand(CommandEnum.ALL_USER_LIGHTS_OFF.getCode());

        serialWriter.sendMessage(message);
    }

    @Override
    public void dim(String homeUnit, Long dimmerFadeRate) {
        byte hu = PLCBusHelper.getHomeUnit().get(homeUnit);

        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setCommand(CommandEnum.DIM.getCode());
        message.getDataMessage().setHomeUnit(hu);
        message.getDataMessage().setDataOne(dimmerFadeRate.byteValue());

        serialWriter.sendMessage(message);
    }

    @Override
    public void bright(String homeUnit, Long brightFadeRate) {
        byte hu = PLCBusHelper.getHomeUnit().get(homeUnit);

        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setCommand(CommandEnum.BRIGHT.getCode());
        message.getDataMessage().setHomeUnit(hu);
        message.getDataMessage().setDataOne(brightFadeRate.byteValue());

        serialWriter.sendMessage(message);
    }

    @Override
    public void blink(String homeUnit, Long interval) {
        byte hu = PLCBusHelper.getHomeUnit().get(homeUnit);

        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setCommand(CommandEnum.BLINK.getCode());
        message.getDataMessage().setHomeUnit(hu);
        message.getDataMessage().setDataOne(interval.byteValue());

        serialWriter.sendMessage(message);
    }

    /*
    @Override
    public void fadeStop(String homeUnit) {
        byte hu = PLCBusHelper.getHomeUnit().get(homeUnit);

        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setCommand(CommandEnum.FADE_STOP.getCode());
        message.getDataMessage().setHomeUnit(hu);
        message.getDataMessage().setDataOne(brightnessLevel.byteValue());
        message.getDataMessage().setDataTwo(fadeRate.byteValue());

        serialWriter.sendMessage(message);
    }
    */
    @Override
    public void presetDim(String homeUnit, Long brightnessLevel, Long dimmerFadeRate) {
        byte hu = PLCBusHelper.getHomeUnit().get(homeUnit);

        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setCommand(CommandEnum.PRESET_DIM.getCode());
        message.getDataMessage().setHomeUnit(hu);
        message.getDataMessage().setDataOne(brightnessLevel.byteValue());
        message.getDataMessage().setDataTwo(dimmerFadeRate.byteValue());

        serialWriter.sendMessage(message);
    }

    @Override
    public StatusResponse4Unit enquiryONAddress(String homeUnit) {
        byte hu = PLCBusHelper.getHomeUnit().get(homeUnit + "1");

        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setCommand(CommandEnum.GET_ONLY_ON_ID_PULSE.getCode());
        message.getDataMessage().setHomeUnit(hu);

        StatusResponse4Unit statusResponse = null;
        Boolean response = serialWriter.sendMessage(message);
        if (response) {
            statusResponse = new StatusResponse4Unit();
            Message messageReceived = serialReader.receivedMessage(message, 3);

            if (messageReceived != null) {
                BitSet bitSetDataTwo = PLCBusHelper.fromByte(messageReceived.getDataMessage().getDataTwo());
                statusResponse.setUnit1(bitSetDataTwo.get(0));
                statusResponse.setUnit2(bitSetDataTwo.get(1));
                statusResponse.setUnit3(bitSetDataTwo.get(2));
                statusResponse.setUnit4(bitSetDataTwo.get(3));
                statusResponse.setUnit5(bitSetDataTwo.get(4));
                statusResponse.setUnit6(bitSetDataTwo.get(5));
                statusResponse.setUnit7(bitSetDataTwo.get(6));
                statusResponse.setUnit8(bitSetDataTwo.get(7));

                BitSet bitSetDataOne = PLCBusHelper.fromByte(messageReceived.getDataMessage().getDataOne());
                statusResponse.setUnit9(bitSetDataOne.get(0));
                statusResponse.setUnit10(bitSetDataOne.get(1));
                statusResponse.setUnit11(bitSetDataOne.get(2));
                statusResponse.setUnit12(bitSetDataOne.get(3));
                statusResponse.setUnit13(bitSetDataOne.get(4));
                statusResponse.setUnit14(bitSetDataOne.get(5));
                statusResponse.setUnit15(bitSetDataOne.get(6));
                statusResponse.setUnit16(bitSetDataOne.get(7));
            }
            statusResponse.setHomeLetter(homeUnit.substring(0, 1));
        }

        return statusResponse;
    }

    @Override
    public StatusResponse check(String homeUnit) {
        byte hu = PLCBusHelper.getHomeUnit().get(homeUnit);

        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setHomeUnit(hu);
        message = serialReader.receivedMessage(message, 0);
        if (message == null) return null;

        boolean status = false;
        if (message.getDataMessage().getCommand() == CommandEnum.ON.getCode()) {
            status = true;
        }

        StatusResponse statusResponse = new StatusResponse();
        statusResponse.setStatus(status);
        return statusResponse;
    }

    public Map viewMessageQueue() {
        return serialReader.viewMessageQueue();
    }

    @Override
    public Long restartPLCBusCount() {
        return serialReader.restartPLCBusCount();
    }

    @Override
    public void _15(String homeUnit) {
        byte hu = PLCBusHelper.getHomeUnit().get(homeUnit);

        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setCommand(CommandEnum.FOR_FUTURE_1.getCode());
        message.getDataMessage().setHomeUnit(hu);

        serialWriter.sendMessage(message);
    }

    @Override
    public void _16(String homeUnit) {
        byte hu = PLCBusHelper.getHomeUnit().get(homeUnit);

        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setCommand(CommandEnum.FOR_FUTURE_2.getCode());
        message.getDataMessage().setHomeUnit(hu);

        serialWriter.sendMessage(message);
    }

    @Override
    public void _17(String homeUnit) {
        byte hu = PLCBusHelper.getHomeUnit().get(homeUnit);

        Message message = PLCBusHelper.prependMessage();
        message.getDataMessage().setCommand(CommandEnum.FOR_FUTURE_3.getCode());
        message.getDataMessage().setHomeUnit(hu);

        serialWriter.sendMessage(message);
    }
}
