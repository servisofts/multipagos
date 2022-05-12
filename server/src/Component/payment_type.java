package Component;

import java.util.Arrays;

import org.json.JSONObject;

import Server.SSSAbstract.SSSessionAbstract;

public class payment_type {
    public static final String COMPONENT = "payment_type";

    public static enum PaymentTypes {
        Efectivo,
        QR,
        TigoMoney,
        CreditCard
    }

    public static void onMessage(JSONObject obj, SSSessionAbstract session) {
        switch (obj.getString("type")) {
            case "getAll":
                getAll(obj, session);
                break;
        }
    }

    public static void getAll(JSONObject obj, SSSessionAbstract session) {
        try {
            // String consulta = "select get_all('" + COMPONENT + "') as json";
            // JSONObject data = SPGConect.ejecutarConsultaObject(consulta);
            obj.put("data", Arrays.asList(PaymentTypes.values()));
            obj.put("estado", "exito");
        } catch (Exception e) {
            obj.put("estado", "error");
            e.printStackTrace();
        }
    }

}
