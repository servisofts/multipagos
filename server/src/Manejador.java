import Servisofts.SConsole;
import org.json.JSONObject;

import Component.config;
import Component.payment_order;
import Component.payment_type;
import Server.SSSAbstract.SSSessionAbstract;

public class Manejador {
    public static void onMessage(JSONObject obj, SSSessionAbstract session) {
        if (session != null) {
            SConsole.log(session.getIdSession(), "\t|\t", obj.getString("component"), obj.getString("type"));
        } else {
            SConsole.log("NoSocketSession", "\t|\t", obj.getString("component"), obj.getString("type"));
        }
        if (obj.isNull("component")) {
            return;
        }
        switch (obj.getString("component")) {
            case config.COMPONENT:
                config.onMessage(obj, session);
                break;
            case payment_type.COMPONENT:
                payment_type.onMessage(obj, session);
                break;
            case payment_order.COMPONENT:
                payment_order.onMessage(obj, session);
                break;
        }
    }
}
