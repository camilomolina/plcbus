package cl.bennu.plcbus.domain;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 29-06-13
 * Time: 03:37 AM
 */
public class DataMessage implements Serializable {

    private byte userCode;
    private byte homeUnit;
    private byte command;
    private byte dataOne;
    private byte dataTwo;
    private byte rxtxSwitch;

    @Override
    public String toString() {
        return "DataMessage{" +
                "userCode=" + userCode +
                ", homeUnit=" + homeUnit +
                ", command=" + command +
                ", dataOne=" + dataOne +
                ", dataTwo=" + dataTwo +
                ", rxtxSwitch=" + rxtxSwitch +
                '}';
    }

    public byte getUserCode() {
        return userCode;
    }

    public void setUserCode(byte userCode) {
        this.userCode = userCode;
    }

    public byte getHomeUnit() {
        return homeUnit;
    }

    public void setHomeUnit(byte homeUnit) {
        this.homeUnit = homeUnit;
    }

    public byte getCommand() {
        return command;
    }

    public void setCommand(byte command) {
        this.command = command;
    }

    public byte getDataOne() {
        return dataOne;
    }

    public void setDataOne(byte dataOne) {
        this.dataOne = dataOne;
    }

    public byte getDataTwo() {
        return dataTwo;
    }

    public void setDataTwo(byte dataTwo) {
        this.dataTwo = dataTwo;
    }

    public byte getRxtxSwitch() {
        return rxtxSwitch;
    }

    public void setRxtxSwitch(byte rxtxSwitch) {
        this.rxtxSwitch = rxtxSwitch;
    }
}
