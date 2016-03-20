package cl.bennu.plcbus.enums;

/**
 * Created with IntelliJ IDEA.
 * User: Mac
 * Date: 22/06/13
 * Time: 22:54
 */
public enum CommUseEnum {

    RS232_CD ("Frame Start Bit", "NO USE")
    , RS232_RXD ("Receive Data", "Use to receive Data")
    , RS232_TXD ("Transmit Data", "Use to transmit Data")
    , RS232_DTR ("Data Terminal Ready", "NO USE")
    , GND ("Ground", "Ground")
    , RS232_DSR ("Data Set Ready", "NO USE")
    , RS232_RTS ("Request To Send", "NO USE")
    , RS232_CTS ("Clear To Send", "NO USE")
    , RS232_RI ("Ring Indicate", "NO USE")
    ;

    private final String command;
    private final String code;

    CommUseEnum(String command, String code) {
        this.command = command;
        this.code = code;
    }

    public String getCommand() {
        return command;
    }

    public String getCode() {
        return code;
    }

}
