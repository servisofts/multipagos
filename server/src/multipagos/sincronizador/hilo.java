package multipagos.sincronizador;

import org.json.JSONArray;
import org.json.JSONObject;

import Servisofts.SPGConect;
import payment_order_state.POS;

public class hilo extends Thread {
    public static final int DELAY_MILLIS = 5 * 1000;

    public static void init() {
        new hilo().start();
    }

    private boolean isRun;

    public hilo() {
        isRun = true;
    }

    @Override
    public void run() {
        super.run();
        while (isRun) {
            try {
                Thread.sleep(DELAY_MILLIS);
                sincronizarEstados();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sincronizarEstados() {
        try {
            JSONArray arr = SPGConect.ejecutarConsultaArray(
                    "select array_to_json(array_agg(sq1.*)) as json from (select * from payment_order where state NOT IN ('terminated', 'no_pay_method') and estado = 1) sq1");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                new POS(obj).sincronize();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
