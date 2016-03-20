package cl.bennu.plcbus.helper;

import cl.bennu.plcbus.constant.Constant;
import cl.bennu.plcbus.domain.DataMessage;
import cl.bennu.plcbus.domain.Message;
import cl.bennu.plcbus.enums.CommandEnum;
import cl.bennu.plcbus.enums.ProtocolEnum;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 29-06-13
 * Time: 03:42 AM
 */
public class PLCBusHelper {

    private static Map<String, Byte> homeUnit;

    static {
        //String homeCode[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"}; // no utilizamos la i,j,k por que byte llega hasta 127
        String homeCode[] = {"A", "B", "C", "D", "E", "F", "G", "H"};
        int unitCode = 0;
        homeUnit = new HashMap<String, Byte>();   // hashMap contenedor de las direcciones de dispositivos

        for (String hc : homeCode) {
            for (Integer i = 1; i <= 11; i++) {
                homeUnit.put(hc + i, (byte) unitCode++);
            }
            unitCode += 5;
        }
    }


    public static BitSet fromByte(byte b) {
        BitSet bits = new BitSet(8);
        for (int i = 0; i < 8; i++) {
            bits.set(i, (b & 1) == 1);
            b >>= 1;
        }
        return bits;
    }

    public static Map<String, Byte> getHomeUnit() {
        return homeUnit;
    }

    public static Message prependMessage() {
        Message message = new Message(new DataMessage());
        message.setStx(ProtocolEnum.STX.getCode());
        message.setLength(ProtocolEnum.LENGTH_SEND.getCode());

        message.getDataMessage().setUserCode(homeUnit.get(Constant.DEFAULT_USER));
        message.getDataMessage().setHomeUnit((byte) 0x00);
        message.getDataMessage().setCommand((byte) 0x00);
        message.getDataMessage().setDataOne((byte) 0x00);
        message.getDataMessage().setDataTwo((byte) 0x00);
        message.getDataMessage().setRxtxSwitch((byte) 0x00);

        message.setEtx(ProtocolEnum.ETX.getCode());

        return message;
    }

    public static String createKey(Message message) {
        return message.getDataMessage().getHomeUnit() + "//" + message.getDataMessage().getUserCode();
    }

    public static Boolean receivedResponse(Message message) {
        if (CommandEnum.STATUS_REQUEST.getCode() == message.getDataMessage().getCommand()
                || CommandEnum.GET_NOISE_STRENGTH.getCode() == message.getDataMessage().getCommand()
                || CommandEnum.GET_SIGNAL_STRENGTH.getCode() == message.getDataMessage().getCommand()
                || CommandEnum.GET_ALL_ID_PULSE.getCode() == message.getDataMessage().getCommand()
                || CommandEnum.GET_ONLY_ON_ID_PULSE.getCode() == message.getDataMessage().getCommand()
                ) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }



}
