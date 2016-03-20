package cl.bennu.plcbus.comm;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 02-07-13
 * Time: 06:22 PM
 */
public class PLCBus {

    public static void main(String[] args) throws Exception {
        IPLCBusController plcBusController = PLCBusController.getInstance(args[0]);

        System.out.println("IPLCBusController : " + plcBusController.getClass().getName());
    }
}
