package multipagos.type;

import org.json.JSONObject;

import multipagos.Client;
import multipagos.Payment;

public class PTEfectivo extends PaymentType {

    public PTEfectivo(Payment p,Client cliente, String gloss,
            String payOrderNumber, String totalAmmount) {
        super(p,cliente.getCi(), cliente.getEmail(), cliente.getName(), cliente.getLast_name(), gloss, cliente.getNit(),
                payOrderNumber, cliente.getPhone(), totalAmmount, "presencial");

    }

    @Override
    public JSONObject toJson() {
        return super.toJson().put("extraData", new JSONObject());
    }
}
