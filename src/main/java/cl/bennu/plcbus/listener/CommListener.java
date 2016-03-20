package cl.bennu.plcbus.listener;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 11-12-13
 * Time: 04:44 PM
 */
public class CommListener implements SerialPortEventListener {

    public InputStream inputStream;

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        switch (serialPortEvent.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                byte[] readBuffer = new byte[14];

                try {
                    while (inputStream.available() > 0) {
                        int numBytes = inputStream.read(readBuffer);
                    }
                    System.out.println("::::::::LISTENER=" + Arrays.toString(readBuffer));
                    String txt = "";
                    for (byte aReadBuffer : readBuffer) {
                        txt += aReadBuffer;
                    }
                    System.out.println("::::::::LISTENER=" + txt);    //Se muestra por pantalla lo que llego en el buffer
                    //de entrada
                } catch (IOException e) {
                }
                break;
        }

    }
}
