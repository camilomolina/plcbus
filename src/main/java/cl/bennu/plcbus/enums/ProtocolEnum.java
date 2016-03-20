package cl.bennu.plcbus.enums;

/**
 * Created with IntelliJ IDEA.
 * User: Mac
 * Date: 22/06/13
 * Time: 22:54
 */
public enum ProtocolEnum {

    STX ("Frame Start Bit", (byte) 0x02)
    , ETX ("Frame End Bit", (byte) 0x03)

    , LENGTH_SEND ("..", (byte) 0x05)
    , LENGTH_RECEIVED ("..", (byte) 0x06)
    ;

    private final String command;
    private final Byte code;

    ProtocolEnum(String command, Byte code) {
        this.command = command;
        this.code = code;
    }

    public String getCommand() {
        return command;
    }

    public Byte getCode() {
        return code;
    }

}
