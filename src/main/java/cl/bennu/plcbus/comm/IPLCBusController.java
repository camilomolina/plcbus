package cl.bennu.plcbus.comm;

import cl.bennu.plcbus.domain.StatusResponse;
import cl.bennu.plcbus.domain.StatusResponse4Unit;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 29-06-13
 * Time: 03:59 AM
 */
public interface IPLCBusController {

    void on(String homeUnit);

    void off(String homeUnit);

    StatusResponse status(String homeUnit);

    StatusResponse status(String homeUnit, Boolean massive);

    Long signalLevel(String homeUnit);

    Long noiseLevel(String homeUnit);

    void erase();

    void scenesAddressErase(String homeUnit);

    void onAll();

    void offAll();

    void dim(String homeUnit, Long dimmerFadeRate);

    void bright(String homeUnit, Long brightFadeRate);

    void blink(String homeUnit, Long interval);

    //void fadeStop(String homeUnit);
    void presetDim(String homeUnit, Long brightnessLevel, Long dimmerFadeRate);

    //void statusOn(String homeUnit, Long brightnessLevel, Long dimmerFadeRate);
    StatusResponse4Unit enquiryONAddress(String homeUnit);

    StatusResponse check(String homeUnit);

    Map viewMessageQueue();

    Long restartPLCBusCount();

    void _15(String homeUnit);

    void _16(String homeUnit);

    void _17(String homeUnit);

}
