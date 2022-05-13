package Component;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import Component.payment_type.PaymentTypes;
import Server.SSSAbstract.SSSessionAbstract;
import Servisofts.SPGConect;
import Servisofts.SUtil;
import multipagos.Multipagos;
import payment_order_state.POS;
import payment_order_state.POS_state.POS_types;

public class payment_order {
    public static final String COMPONENT = "payment_order";

    public static void onMessage(JSONObject obj, SSSessionAbstract session) {
        try {
            switch (obj.getString("type")) {
                case "getAll":
                    getAll(obj, session);
                    break;
                case "getByKey":
                    getByKey(obj, session);
                    break;
                case "sinc_state":
                    sinc_state(obj, session);
                    break;
                case "registro":
                    registro(obj, session);
                    break;
                case "pay_method":
                    pay_method(obj, session);
                    break;
                case "getByNumber":
                    getByNumber(obj, session);
                    break;
                case "confirm":
                    confirm(obj, session);
                    break;
                case "cancel":
                    cancel(obj, session);
                    break;
            }
        } catch (Exception e) {
            obj.put("estado", "error");
            obj.put("error", e.getMessage());
            // e.printStackTrace();
        }
    }

    public static void getAll(JSONObject obj, SSSessionAbstract session) throws SQLException {
        String consulta = "select get_all('" + COMPONENT + "') as json";
        JSONObject data = SPGConect.ejecutarConsultaObject(consulta);
        obj.put("data", data);
        obj.put("estado", "exito");
    }

    public static void registro(JSONObject obj, SSSessionAbstract session) throws Exception {
        String key_servicio = obj.getJSONObject("servicio").getString("key");
        JSONObject data = obj.getJSONObject("data");
        JSONObject pay_order = new JSONObject();
        pay_order.put("key", SUtil.uuid());
        pay_order.put("key_servicio", key_servicio);
        pay_order.put("fecha_on", SUtil.now());
        pay_order.put("estado", 1);
        pay_order.put("glosa", data.getString("glosa"));
        // pay_order.put("expiration_date", data.getString("expiration_date"));
        pay_order.put("expiration_time", data.getInt("expiration_time"));
        POS pos = new POS(pay_order);
        pos.register(data);
        obj.put("data", pos.toJson());
        obj.put("estado", "exito");

    }

    public static void getByKey(JSONObject obj, SSSessionAbstract session) throws Exception {
        String key_servicio = obj.getJSONObject("servicio").getString("key");
        String key_payment_order = obj.getString("key_payment_order");
        if (key_payment_order.isEmpty()) {
            throw new Exception("No se encontro la orden de pago");
        }
        obj.put("data", SPGConect
                .ejecutarConsultaObject("select payment_order_get_detalle('" + key_payment_order + "') as json"));
        obj.put("estado", "exito");
    }

    public static void sinc_state(JSONObject obj, SSSessionAbstract session) throws Exception {
        if (!obj.has("data")) {
            getByKey(obj, session);
        }

        if (!obj.getJSONObject("data").has("pay_order_number")) {
            getByKey(obj, session);
        }
        JSONObject pay_order = obj.getJSONObject("data");
        String pay_order_number = pay_order.getString("pay_order_number");

        String key_servicio = obj.getJSONObject("servicio").getString("key");
        Multipagos mp = config.getMultipagosByKeyServicio(key_servicio);
        JSONObject po = mp.getOrden().get_pay_order_by_number(Integer.parseInt(pay_order_number));
        if (po == null) {
            throw new Exception("No se encontro la orden de pago en la API de Multipagos");
        }
        if (!pay_order.getString("state").equals(po.getString("status_order"))) {
            pay_order.put("state", po.getString("status_order"));
            if (po.getString("status_order").equals("Confirmada")) {
                pay_order.put("fecha_on", SUtil.now());
            }
            // JSONObject poedit = new JSONObject();
            // poedit.put("key", pay_order.getString("key"));
            // poedit.put("state", pay_order.getString("state"));
            // SPGConect.editObject("payment_order", poedit);

        }
        obj.put("data", pay_order);
        obj.put("estado", "exito");

    }

    public static void getByNumber(JSONObject obj, SSSessionAbstract session) {
        String key_servicio = obj.getJSONObject("servicio").getString("key");
        // String key_payment_order = obj.getString("key_payment_order");

        Multipagos mp = config.getMultipagosByKeyServicio(key_servicio);
        JSONObject po = mp.getOrden().get_pay_order_by_number(obj.getInt("pay_order_number"));
        if (po == null) {
            obj.put("estado", "error");
            obj.put("mensaje", "No se encontro la orden de pago");
            return;
        }
        obj.put("data", po);
        obj.put("estado", "exito");
    }

    public static void confirm(JSONObject obj, SSSessionAbstract session) throws Exception {
        getByKey(obj, session);
        JSONObject pay_order = obj.getJSONObject("data");
        String pay_official_number = pay_order.getString("pay_official_number");
        String key_servicio = obj.getJSONObject("servicio").getString("key");
        Multipagos mp = config.getMultipagosByKeyServicio(key_servicio);
        JSONObject orden = mp.getOrden().confirm(pay_official_number);
        if (orden == null) {
            throw new Exception("No se pudo confirmar la orden de pago");
        }
        pay_order.put("state", orden.getJSONObject("status").getString("name"));
        orden.remove("status");
        pay_order.put("data", pay_order.getJSONObject("data").put("pay_order", orden));
        SPGConect.editObject("payment_order", pay_order);
        obj.put("data", pay_order);
        obj.put("estado", "exito");
    }

    public static void cancel(JSONObject obj, SSSessionAbstract session) throws Exception {
        getByKey(obj, session);
        JSONObject pay_order = obj.getJSONObject("data");
        String pay_official_number = pay_order.getString("pay_official_number");
        String key_servicio = obj.getJSONObject("servicio").getString("key");
        Multipagos mp = config.getMultipagosByKeyServicio(key_servicio);
        JSONObject orden = mp.getOrden().cancel(pay_official_number);
        if (orden == null) {
            throw new Exception("No se pudo confirmar la orden de pago");
        }
        pay_order.put("state", orden.getJSONObject("status").getString("name"));
        orden.remove("status");
        pay_order.put("data", pay_order.getJSONObject("data").put("pay_order", orden));
        SPGConect.editObject("payment_order", pay_order);
        obj.put("data", pay_order);
        obj.put("estado", "exito");
    }

    public static void pay_method(JSONObject obj, SSSessionAbstract session) throws Exception {

        if (obj.isNull("key_payment_order")) {
            throw new Exception("key_payment_order::String not found");
        }
        if (obj.isNull("pay_method")) {
            throw new Exception("pay_method::String not found");
        }
        String pay_method = obj.getString("pay_method");
        if (pay_method.isEmpty()) {
            throw new Exception("pay_method::String is empty");
        }

        String key_servicio = obj.getJSONObject("servicio").getString("key");

        JSONObject pay_order = SPGConect.ejecutarConsultaObject(
                "select get_by('payment_order','key','" + obj.getString("key_payment_order") + "') as json");
        if (pay_order.isNull("key")) {
            throw new Exception("key_payment_order::String not found in database");
        }

        Multipagos mp = config.getMultipagosByKeyServicio(key_servicio);
        JSONObject pt = mp.getPago().createPayment(PaymentTypes.valueOf(pay_method), pay_order);

        JSONObject paym = new JSONObject();
        paym.put("key", SUtil.uuid());
        paym.put("key_payment_order", pay_order.getString("key"));
        paym.put("data", pt);
        paym.put("estado", 1);
        paym.put("fecha_on", SUtil.now());
        SPGConect.insertObject("payment_method", paym);

        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date
        cal.add(Calendar.SECOND, pay_order.getInt("expiration_time")); // adds one hour
        cal.getTime(); // returns new date object plus one hour
        String expiration_date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").format(cal.getTime());
        JSONObject pay_order_edit = new JSONObject();
        pay_order_edit.put("key", pay_order.getString("key"));
        pay_order_edit.put("expiration_date", expiration_date);
        SPGConect.editObject("payment_order", pay_order_edit);
      
        obj.put("data", pt);
        obj.put("estado", "exito");
    }
}
