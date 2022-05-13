package multipagos;

import org.json.JSONArray;
import org.json.JSONObject;

import Component.payment_type.PaymentTypes;
import multipagos.http.Http;

public class Order {
    private Multipagos multipagos;
    private String url;
    private String Bearer;

    public Order(Multipagos multipagos, String url) {
        this.multipagos = multipagos;
        this.url = url;
    }

    public boolean isLogin() {
        return Bearer != null;
    }

    public String login() {
        JSONObject obj = new JSONObject();
        obj.put("provider", multipagos.getProvider());
        obj.put("uid", multipagos.getUid());
        JSONObject data = Http.send(this.url + "api/v2/get_token", obj);
        if (data.has("status")) {
            if (data.getString("status").equals("OK")) {
                this.Bearer = data.getString("data");
                return this.Bearer;
            }
        }
        return null;
    }

    // private JSONObject create_item_selected(int id, String descripcion, String
    // unitary_price, String quantity) {
    // JSONObject obj = new JSONObject();
    // obj.put("id", id);
    // obj.put("description", descripcion);
    // obj.put("unitary_price", unitary_price);
    // obj.put("quantity", quantity);
    // return obj;
    // }

    private JSONObject create_payment_data(JSONArray items) throws Exception {
        JSONObject obj = new JSONObject();
        if (items.length() <= 0) {
            throw new Exception("items::Arrays is empty");
        }
        // items.put(create_item_selected(1, "Producto 1", "5", "1"));
        obj.put("item_selecteds", items);
        JSONObject payment_receiver = new JSONObject();
        payment_receiver.put("id_local", "");
        payment_receiver.put("name", "");
        payment_receiver.put("ci", "");
        payment_receiver.put("account_type", "");
        payment_receiver.put("account_num", "");
        payment_receiver.put("bank", "");
        obj.put("payment_receiver", payment_receiver);
        return obj;
    }

    // private JSONObject create_client_data(String name, String last_name, String
    // ci, String phone, String email,
    // String bussiness_name, String nit) {
    // JSONObject obj = new JSONObject();
    // obj.put("name", name);
    // obj.put("last_name", last_name);
    // obj.put("ci", ci);
    // obj.put("phone", phone);
    // obj.put("email", email);
    // obj.put("bussiness_name", bussiness_name);
    // obj.put("nit", nit);
    // return obj;
    // }

    public JSONObject create_pay_order(JSONObject order_data) throws Exception {

        if (order_data == null) {
            throw new Exception("data::Object is null");
        }
        if (!order_data.has("client")) {
            throw new Exception("client::Object is null");
        }
        if (!order_data.has("items")) {
            throw new Exception("items::Array is null");
        }

        if (!isLogin()) {
            login();
        }
        JSONObject obj = new JSONObject();
        obj.put("service", new JSONObject().put("code", multipagos.getServiceCode()));
        String payType = "";
        switch (PaymentTypes.valueOf(order_data.getString("payment_type"))) {
            case QR:
                payType = "bcp_qr";
                break;
            case CreditCard:
                payType = "cyber_source";
                break;
            case TigoMoney:
                payType = "tigo_money";
                break;
            default:
                throw new Exception("Payment type not found");
        }
        obj.put("payment_data", create_payment_data(order_data.getJSONArray("items")).put("extraData",
                new JSONObject().put("pay_channel_code", payType)));
        obj.put("client", order_data.getJSONObject("client"));
        // obj.put("url_confirm", "https://tapekeapp.com/api/");
        
        JSONObject data = Http.send(this.url + "api/v2/external_services/create_payorder_app", obj, this.Bearer);
        System.out.println(obj);
        System.out.println(data);
        // JSONObject data = Http.send(this.url + "api/v2/external_services/payorders",
        // obj, this.Bearer);
        if (data.has("status")) {
            if (data.getString("status").equals("OK")) {
                return data.getJSONObject("data");
            } else {
                throw new Exception(data.getString("data"));
            }
        }
        return null;
    }

    public JSONObject confirm(String pay_official_number) {
        if (!isLogin()) {
            login();
        }
        JSONObject obj = new JSONObject();
        obj.put("pay_official_number", pay_official_number);
        JSONObject data = Http.send(this.url + "api/v2/external_services/confirm_pay_order", obj, this.Bearer);
        if (data.has("status")) {
            if (data.getString("status").equals("OK")) {
                return data.getJSONObject("data");
            }
        }
        return null;
    }

    public JSONObject cancel(String pay_official_number) {
        if (!isLogin()) {
            login();
        }
        JSONObject obj = new JSONObject();
        obj.put("pay_official_number", pay_official_number);
        JSONObject data = Http.send(this.url + "api/v2/external_services/cancel_pay_order", obj, this.Bearer);
        if (data.has("status")) {
            if (data.getString("status").equals("OK")) {
                return data.getJSONObject("data");
            }
        }
        return null;
    }

    public JSONObject get_pay_order_by_number(int number) {
        if (!isLogin()) {
            login();
        }
        JSONObject obj = new JSONObject();
        obj.put("pay_order_number", number);
        JSONObject data = Http.send(this.url + "api/v2/getPayOrderByNumber", obj, this.Bearer);
        if (data.has("status")) {
            if (data.getString("status").equals("OK")) {
                return data.getJSONObject("data");
            }
        }
        return null;
    }
}
