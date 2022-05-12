package multipagos.ptype;

import org.json.JSONObject;

public abstract class PaymentType {
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

    public PaymentType(String document, String email, String firstName, String lastName, String gloss, String nit,
            String payOrderNumber, String phone, String totalAmmount, String payChanelCode) {
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
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
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
}
