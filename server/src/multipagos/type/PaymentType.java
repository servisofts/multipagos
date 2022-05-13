package multipagos.type;

import org.json.JSONException;
import org.json.JSONObject;

import multipagos.Payment;
import multipagos.http.Http;
import payment_order_state.POS;

public abstract class PaymentType {
    private String businessName;
    private String document;
    private String email;
    private String firstName;
    private String lastName;
    private String gloss;
    private String nit;
    private String payOrderNumber;
    private String phone;
    private String totalAmmount;
    private String payChanelCode;
    private Payment payment;

    private JSONObject paymentData;

    public PaymentType(Payment payment, String document, String email, String firstName, String lastName, String gloss,
            String nit,
            String payOrderNumber, String phone, String totalAmmount, String payChanelCode) {
        this.payment = payment;
        this.document = document;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gloss = gloss;
        this.nit = nit;
        this.payOrderNumber = payOrderNumber;
        this.phone = phone;
        this.totalAmmount = totalAmmount;
        this.payChanelCode = payChanelCode;
        this.businessName = payment.getMultipagos().getServiceCode();
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("businessName", businessName);
        obj.put("document", document);
        obj.put("email", email);
        obj.put("firstName", firstName);
        obj.put("lastName", lastName);
        obj.put("gloss", gloss);
        obj.put("nit", nit);
        obj.put("payOrderNumber", payOrderNumber);
        obj.put("phone", phone);
        obj.put("totalAmmount", totalAmmount);
        obj.put("payChanelCode", payChanelCode);
        return obj;
    }

    public void setPaymentData(JSONObject paymentData) {
        this.paymentData = paymentData;
    }

    public JSONObject getPaymentData() {
        return paymentData;
    }

    public Payment getPayment() {
        return payment;
    }
    public String getPayOrderNumber() {
        return payOrderNumber;
    }
    public abstract JSONObject onSucces(JSONObject response);

    public JSONObject send() throws JSONException, Exception {
        if (!payment.isLogin()) {
            payment.login();
        }
        JSONObject obj = this.toJson();

        JSONObject data = Http.send(payment.getUrl() + "api/v1/pay", obj, payment.getBearer());
        System.out.println(obj);
        System.out.println(data);
        if (data.has("status")) {
            if (data.getInt("status") == 200) {
                if (data.getString("statusCode").equals("10000")) {
                    return this.onSucces(data);
                } else {
                    throw new Exception(data.getString("message"));
                }
            }
        }
        throw new Exception("PaymentType send error");
    }
}
