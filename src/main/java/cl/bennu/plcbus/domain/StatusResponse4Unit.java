package cl.bennu.plcbus.domain;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 19-03-14
 * Time: 09:12 PM
 */
public class StatusResponse4Unit implements Serializable {

    private String homeLetter;
    private Boolean unit1;
    private Boolean unit2;
    private Boolean unit3;
    private Boolean unit4;
    private Boolean unit5;
    private Boolean unit6;
    private Boolean unit7;
    private Boolean unit8;
    private Boolean unit9;
    private Boolean unit10;
    private Boolean unit11;
    private Boolean unit12;
    private Boolean unit13;
    private Boolean unit14;
    private Boolean unit15;
    private Boolean unit16;

    @Override
    public String toString() {
        return "StatusResponse4Unit{" +
                "homeLetter='" + homeLetter + '\'' +
                ", unit1=" + unit1 +
                ", unit2=" + unit2 +
                ", unit3=" + unit3 +
                ", unit4=" + unit4 +
                ", unit5=" + unit5 +
                ", unit6=" + unit6 +
                ", unit7=" + unit7 +
                ", unit8=" + unit8 +
                ", unit9=" + unit9 +
                ", unit10=" + unit10 +
                ", unit11=" + unit11 +
                ", unit12=" + unit12 +
                ", unit13=" + unit13 +
                ", unit14=" + unit14 +
                ", unit15=" + unit15 +
                ", unit16=" + unit16 +
                '}';
    }

    public String getHomeLetter() {
        return homeLetter;
    }

    public void setHomeLetter(String homeLetter) {
        this.homeLetter = homeLetter;
    }

    public Boolean getUnit1() {
        return unit1;
    }

    public void setUnit1(Boolean unit1) {
        this.unit1 = unit1;
    }

    public Boolean getUnit2() {
        return unit2;
    }

    public void setUnit2(Boolean unit2) {
        this.unit2 = unit2;
    }

    public Boolean getUnit3() {
        return unit3;
    }

    public void setUnit3(Boolean unit3) {
        this.unit3 = unit3;
    }

    public Boolean getUnit4() {
        return unit4;
    }

    public void setUnit4(Boolean unit4) {
        this.unit4 = unit4;
    }

    public Boolean getUnit5() {
        return unit5;
    }

    public void setUnit5(Boolean unit5) {
        this.unit5 = unit5;
    }

    public Boolean getUnit6() {
        return unit6;
    }

    public void setUnit6(Boolean unit6) {
        this.unit6 = unit6;
    }

    public Boolean getUnit7() {
        return unit7;
    }

    public void setUnit7(Boolean unit7) {
        this.unit7 = unit7;
    }

    public Boolean getUnit8() {
        return unit8;
    }

    public void setUnit8(Boolean unit8) {
        this.unit8 = unit8;
    }

    public Boolean getUnit9() {
        return unit9;
    }

    public void setUnit9(Boolean unit9) {
        this.unit9 = unit9;
    }

    public Boolean getUnit10() {
        return unit10;
    }

    public void setUnit10(Boolean unit10) {
        this.unit10 = unit10;
    }

    public Boolean getUnit11() {
        return unit11;
    }

    public void setUnit11(Boolean unit11) {
        this.unit11 = unit11;
    }

    public Boolean getUnit12() {
        return unit12;
    }

    public void setUnit12(Boolean unit12) {
        this.unit12 = unit12;
    }

    public Boolean getUnit13() {
        return unit13;
    }

    public void setUnit13(Boolean unit13) {
        this.unit13 = unit13;
    }

    public Boolean getUnit14() {
        return unit14;
    }

    public void setUnit14(Boolean unit14) {
        this.unit14 = unit14;
    }

    public Boolean getUnit15() {
        return unit15;
    }

    public void setUnit15(Boolean unit15) {
        this.unit15 = unit15;
    }

    public Boolean getUnit16() {
        return unit16;
    }

    public void setUnit16(Boolean unit16) {
        this.unit16 = unit16;
    }
}
