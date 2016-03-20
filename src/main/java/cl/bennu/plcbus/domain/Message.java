package cl.bennu.plcbus.domain;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 29-06-13
 * Time: 03:35 AM
 */
public class Message implements Serializable, Comparable {

    private byte stx;
    private byte length;
    private DataMessage dataMessage;
    private byte etx;

    private Boolean requieredResponse;

    public Message() {

    }

    public Message(DataMessage data) {
        dataMessage = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "stx=" + stx +
                ", length=" + length +
                ", dataMessage=" + dataMessage +
                ", etx=" + etx +
                '}';
    }

    public Boolean getRequieredResponse() {
        return requieredResponse;
    }

    public void setRequieredResponse(Boolean requieredResponse) {
        this.requieredResponse = requieredResponse;
    }

    public byte getStx() {
        return stx;
    }

    public void setStx(byte stx) {
        this.stx = stx;
    }

    public byte getLength() {
        return length;
    }

    public void setLength(byte length) {
        this.length = length;
    }

    public DataMessage getDataMessage() {
        return dataMessage;
    }

    public void setDataMessage(DataMessage dataMessage) {
        this.dataMessage = dataMessage;
    }

    public byte getEtx() {
        return etx;
    }

    public void setEtx(byte etx) {
        this.etx = etx;
    }

    @Override
    public int compareTo(Object o) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
