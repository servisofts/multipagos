package Component;

import org.json.JSONArray;
import org.json.JSONObject;

import Server.SSSAbstract.SSSessionAbstract;
import Servisofts.SPGConect;
import Servisofts.SUtil;
import multipagos.Multipagos;

public class config {
    public static final String COMPONENT = "config";

    public static void onMessage(JSONObject obj, SSSessionAbstract session) {
        switch (obj.getString("type")) {
            case "getAll":
                getAll(obj, session);
                break;
            case "registro":
                registro(obj, session);
                break;
            case "editar":
                editar(obj, session);
                break;
        }
    }

    public static void getAll(JSONObject obj, SSSessionAbstract session) {
        try {
            String consulta = "select get_all('" + COMPONENT + "') as json";
            JSONObject data = SPGConect.ejecutarConsultaObject(consulta);
            obj.put("data", data);
            obj.put("estado", "exito");
        } catch (Exception e) {
            obj.put("estado", "error");
            e.printStackTrace();
        }
    }

    public static void registro(JSONObject obj, SSSessionAbstract session) {
        try {
            JSONObject data = obj.getJSONObject("data");
            data.put("key", SUtil.uuid());
            data.put("estado", 1);
            data.put("fecha_on", SUtil.now());
            data.put("key_usuario", obj.getString("key_usuario"));
            SPGConect.insertArray(COMPONENT, new JSONArray().put(data));
            obj.put("data", data);
            obj.put("estado", "exito");
        } catch (Exception e) {
            obj.put("estado", "error");
            e.printStackTrace();
        }
    }

    public static void editar(JSONObject obj, SSSessionAbstract session) {
        try {
            JSONObject data = obj.getJSONObject("data");
            SPGConect.editObject(COMPONENT, data);
            obj.put("data", data);
            obj.put("estado", "exito");
        } catch (Exception e) {
            obj.put("estado", "error");
            e.printStackTrace();
        }
    }

    public static JSONObject getByKeyServicio(String key_servicio) {
        JSONObject data = new JSONObject();
        data.put("servicio", new JSONObject().put("key", key_servicio));
        getByKeyServicio(data, null);
        return data.getJSONObject("data");
    }

    public static Multipagos getMultipagosByKeyServicio(String key_servicio) {
        JSONObject data = new JSONObject();
        data.put("servicio", new JSONObject().put("key", key_servicio));
        getByKeyServicio(data, null);
        JSONObject config = data.getJSONObject("data");
        Multipagos multipagos = new Multipagos(config.getString("key"), config.getString("url"),
                config.getString("url_pago"),
                config.getString("service_code"), config.getString("provider"), config.getString("uid"));
        return multipagos;
    }

    public static void getByKeyServicio(JSONObject obj, SSSessionAbstract session) {
        try {
            JSONObject servicio = obj.getJSONObject("servicio");
            String consulta = "select get_by('" + COMPONENT + "','key_servicio','" + servicio.getString("key")
                    + "') as json";
            JSONObject data = SPGConect.ejecutarConsultaObject(consulta);
            obj.put("data", data);
            obj.put("estado", "exito");
        } catch (Exception e) {
            obj.put("estado", "error");
            e.printStackTrace();
        }
    }

}
