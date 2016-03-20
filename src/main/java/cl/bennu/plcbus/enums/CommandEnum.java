package cl.bennu.plcbus.enums;

/**
 * Created with IntelliJ IDEA.
 * User: Mac
 * Date: 22/06/13
 * Time: 22:54
 */
public enum CommandEnum {

    ALL_UNITS_OFF ("ALL_UNITS_OFF", (byte) 0x00)
    , ALL_LIGHTS_ON ("ALL_LIGHTS_ON", (byte) 0x01)
    , ON ("ON", (byte) 0x02)
    , OFF ("OFF", (byte) 0x03)
    , DIM ("DIM", (byte) 0x04)
    , BRIGHT ("BRIGHT", (byte) 0x05)
    , ALL_LIGHTS_OFF ("ALL_LIGHTS_OFF", (byte) 0x06)
    , ALL_USER_LIGHTS_ON ("ALL_USER_LIGHTS_ON", (byte) 0x07)
    , ALL_USER_UNITS_OFF ("ALL_USER_UNITS_OFF", (byte) 0x08)
    , ALL_USER_LIGHTS_OFF ("ALL_USER_LIGHTS_OFF", (byte) 0x09)
    , BLINK ("BLINK", (byte) 0x0A)
    , FADE_STOP ("FADE_STOP", (byte) 0x0B)
    , PRESET_DIM ("PRESET_DIM", (byte) 0x0C)
    , STATUS_ON ("STATUS_ON", (byte) 0x0D)
    , STATUS_OFF ("STATUS_OFF", (byte) 0x0E)
    , STATUS_REQUEST ("STATUS_REQUEST", (byte) 0x0F)
    , RX_MASTER_ADDR_SETUP ("RX_MASTER_ADDR_SETUP", (byte) 0x10)
    , TX_MASTER_ADDR_SETUP ("TX_MASTER_ADDR_SETUP", (byte) 0x11)
    , SCENE_ADDR_SETUP ("SCENE_ADDR_SETUP", (byte) 0x12)
    , SCENE_ADDR_ERASE ("SCENE_ADDR_ERASE", (byte) 0x13)
    , ALL_SCENES_ADDR_ERASE ("ALL_SCENES_ADDR_ERASE", (byte) 0x14)
    , FOR_FUTURE_1 ("FOR_FUTURE_1", (byte) 0x15)
    , FOR_FUTURE_2 ("FOR_FUTURE_1", (byte) 0x16)
    , FOR_FUTURE_3 ("FOR_FUTURE_1", (byte) 0x17)
    , GET_SIGNAL_STRENGTH ("GET_SIGNAL_STRENGTH", (byte) 0x18)
    , GET_NOISE_STRENGTH ("GET_NOISE_STRENGTH", (byte) 0x19)
    , REPORT_SIGNAL_STRENGTH ("REPORT_SIGNAL_STRENGTH", (byte) 0x1A)
    , REPORT_NOISE_STRENGTH ("REPORT_NOISE_STRENGTH", (byte) 0x1B)
    , GET_ALL_ID_PULSE ("GET_ALL_ID_PULSE", (byte) 0x1C)
    , GET_ONLY_ON_ID_PULSE ("GET_ONLY_ON_ID_PULSE", (byte) 0x1D)
    , REPORT_ALL_ID_PULSE ("REPORT_ALL_ID_PULSE", (byte) 0x1E)
    , REPORT_ON_ID_PULSE ("REPORT_ON_ID_PULSE", (byte) 0x1F)
    ;

    private final String command;
    private final Byte code;

    CommandEnum(String command, Byte code) {
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
