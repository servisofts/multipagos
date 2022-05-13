package multipagos.type;

import org.json.JSONObject;

import multipagos.Client;
import multipagos.Payment;
import payment_order_state.POS;
import payment_order_state.POS_state.POS_types;

public class PTQR extends PaymentType {

    public PTQR(Payment p, Client cliente, String gloss,
            String payOrderNumber, String totalAmmount) {
        super(p, cliente.getCi(), cliente.getEmail(), cliente.getName(), cliente.getLast_name(), gloss,
                cliente.getNit(),
                payOrderNumber, cliente.getPhone(), totalAmmount, "bcp_qr");

    }

    @Override
    public JSONObject toJson() {
        return super.toJson().put("extraData", new JSONObject());
    }
    @Override
    public JSONObject onSucces(JSONObject response) {
        new POS(this.getPaymentData()).changeState(POS_types.pending,"onSucces create QR");
        return response.getJSONObject("data");
    }
}
