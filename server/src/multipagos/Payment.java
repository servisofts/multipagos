package multipagos;

import org.json.JSONObject;

import Component.payment_type.PaymentTypes;
import multipagos.http.Http;
import multipagos.type.PTCreditCard;
import multipagos.type.PTEfectivo;
import multipagos.type.PTQR;
import multipagos.type.PTTigoMoney;
import multipagos.type.PaymentType;

public class Payment {
    private Multipagos multipagos;
    private String url;
    private String bearer;
    private JSONObject loginData;

    public Payment(Multipagos multipagos, String url) {
        this.multipagos = multipagos;
        this.url = url;
    }

    public boolean isLogin() {
        return loginData != null;
    }

    public JSONObject login() {
        JSONObject obj = new JSONObject();
        obj.put("username", multipagos.getProvider());
        obj.put("password", multipagos.getUid());
        JSONObject data = Http.send(this.url + "api/v1/access/login", obj);
        if (data.has("statusCode")) {
            if (data.getString("statusCode").equals("10000")) {
                this.loginData = data.getJSONObject("data");
                this.bearer = this.loginData.getString("accessToken");
                return this.loginData;
            }
        }
        return null;
    }

    public JSONObject createPayment(PaymentTypes type, JSONObject paymentData) throws Exception {
        String payOrderNumber = paymentData.getString("pay_official_number");
        JSONObject pay_order = paymentData.getJSONObject("data").getJSONObject("pay_order");
        double totalAmmount = pay_order.getDouble("total_amount");
        Client cli = new Client(pay_order.get("client_name").toString(), pay_order.get("client_last_name").toString(),
                pay_order.get("client_ci").toString(), pay_order.get("client_phone").toString(),
                pay_order.get("client_email").toString(), pay_order.get("client_business_name").toString(),
                pay_order.get("client_nit").toString());
        PaymentType paymentType = null;
        String glosa = "Pago prueba";
        switch (type) {
            case Efectivo:
                paymentType = new PTEfectivo(this, cli, glosa, payOrderNumber, totalAmmount + "");
                break;
            case QR:
                paymentType = new PTQR(this, cli, glosa, payOrderNumber, totalAmmount + "");
                break;
            case CreditCard:
                paymentType = new PTCreditCard(this, cli, glosa, payOrderNumber, totalAmmount + "");
                break;
            case TigoMoney:
                paymentType = new PTTigoMoney(this, cli, glosa, payOrderNumber, totalAmmount + "");
                break;
            default:
                throw new Exception("Payment type not found");

        }
        if (paymentType != null) {
            paymentType.setPaymentData(paymentData);
            return paymentType.send();
        }
        throw new Exception("Error desconocido on pay_method");
    }

    public Multipagos getMultipagos() {
        return multipagos;
    }

    public String getUrl() {
        return url;
    }

    public String getBearer() {
        return bearer;
    }
}
