package cl.bennu.plcbus.comm;

import cl.bennu.plcbus.domain.StatusResponse4Unit;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 02-07-13
 * Time: 06:22 PM
 */
public class PLCBusControllerTest {
    private static final String PORT = "COM4";

    public static void main(String[] args) throws Exception {
        IPLCBusController plcBusController;
        if (args == null || args.length == 0) {
            plcBusController = PLCBusController.getInstance(PORT);
        } else {
            plcBusController = PLCBusController.getInstance(args[0]);
        }

        //Scanner scanner = new Scanner(System.in);

        //plcBusController.scenesAddressErase("A9");
        //plcBusController.scenesAddressErase("A10");

        //plcBusController.on("B5");
        //plcBusController.on("B6");


        //plcBusController.on("A13");
        //plcBusController.on("A14");

        //plcBusController.off("A2");
        //plcBusController.off("A3");
        //plcBusController.off("A4");
        //plcBusController.scenesAddressErase("A3");
        //System.out.println("plcBusController:" + plcBusController.erase());
        //System.out.println("plcBusController:" + plcBusController.signalLevel("A1"));
        //plcBusController.on("A3");
        //System.out.println("plcBusController:" + plcBusController.status("A5"));

        //plcBusController.on("A3");

        //System.out.println("plcBusController:" + plcBusController.status("A2"));
        //System.out.println("plcBusController:" + plcBusController.status("A1"));
        //plcBusController.on("A4");
        //System.out.println("plcBusController:" + plcBusController.signalLevel("A1"));

        //plcBusController.off("A2");
        //plcBusController.status("A1");
        //plcBusController.on("D5");
        //plcBusController.onAll();

        //plcBusController.fadeStop("D5", 100L, 100L);
        //plcBusController.dim("D5", 0L);
        //plcBusController.dim("D5", 100L);
        //plcBusController.dim("D6", 1L);

        //plcBusController.dim("D5", 0L);
        //plcBusController.bright("D5", 80L);

        //plcBusController.status("D5");

        //plcBusController.off("D5");
        //plcBusController.on("D5");
        //plcBusController.off("D5");
        //plcBusController.off("D6");
        //plcBusController.off("D9");
        //plcBusController.on("D10");
        //plcBusController.presetDim("D5", 20L, 3L);

        //plcBusController.blink("D5", 0L);
        plcBusController.presetDim("D5", 100L, 0L);
        //plcBusController.presetDim("D6", 100L, 10L);

        //System.out.println(new Long((byte) 0x1F));
        //System.out.println(new Long(31).byteValue());


        StatusResponse4Unit statusResponse4Unit = plcBusController.enquiryONAddress("D1");
        System.out.println(statusResponse4Unit);

    }
}
