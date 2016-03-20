package cl.bennu.plcbus.process;

import cl.bennu.plcbus.constant.Constant;
import cl.bennu.plcbus.domain.Message;
import cl.bennu.plcbus.helper.PLCBusHelper;

import java.io.OutputStream;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 29-06-13
 * Time: 03:45 AM
 */
public class SerialWriter extends Thread {

    private OutputStream outputStream;
    private boolean running = true;
    private LinkedList<Message> messageList = new LinkedList<Message>();
    public BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
    //private MessageOrquester messageOrquester;


    public SerialWriter(OutputStream out) {
        this.outputStream = out;
        //this.messageOrquester = messageOrquester;
    }

    public void run() {
        try {
            System.out.println("Started SerialWriter");

            byte[] command;
                /*
                COMMMAND
                LINK BIT 7 - Direccion extendida
                        1: Extiende
                        0: NO extiende

                REPRQ BIT 6 - Lineas de alimentaciòn de tres fases
                        1: Tres fases
                        0: Dos fases

                ACK_PULSE BIT 5 - Direccion extendida
                        1: Solicita ACK
                        0: Sin ACK
                */
            while (running) {
                Message message = queue.take();

                command = new byte[]{message.getStx(), message.getLength(), message.getDataMessage().getUserCode(), message.getDataMessage().getHomeUnit(), message.getDataMessage().getCommand(), message.getDataMessage().getDataOne(), message.getDataMessage().getDataTwo(), message.getEtx()};

                this.outputStream.write(command);
                this.outputStream.flush();
                //this.outputStream.close();

                System.out.println("WRITER (" + queue.size() + ") : " + message);

                TimeUnit.MILLISECONDS.sleep(Constant.SERIAL_WRITE_SLEEPTIME);
                //Thread.sleep(Constant.SERIAL_WRITE_SLEEPTIME);
            }
        } catch (Exception e) {
            //..
        }
    }

    public Boolean sendMessage(Message message) {
        //System.out.println("QUEUE SIZE : " + queue.size());
        queue.offer(message);
        return PLCBusHelper.receivedResponse(message);
    }

    public void stop(Message message) {
        running = false;
    }

}
