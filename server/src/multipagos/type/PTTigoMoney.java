package multipagos.type;

import org.json.JSONObject;

import multipagos.Client;
import multipagos.Payment;
import payment_order_state.POS;
import payment_order_state.POS_state.POS_types;

public class PTTigoMoney extends PaymentType {

    public PTTigoMoney(Payment p, Client cliente, String gloss,
            String payOrderNumber, String totalAmmount) {
        super(p, cliente.getPhone(), cliente.getEmail(), cliente.getName(), cliente.getLast_name(), gloss,
                cliente.getNit(),
                payOrderNumber, cliente.getPhone(), totalAmmount, "tigo_money");

    }

    @Override
    public JSONObject toJson() {
        return super.toJson().put("extraData", new JSONObject());
    }

    @Override
    public JSONObject onSucces(JSONObject response) {
        new POS(this.getPaymentData()).changeState(POS_types.pending, "onSucces create TIGO  money");
        getPayment().getMultipagos().getOrden().confirm(this.getPayOrderNumber());
        return new JSONObject().put("type","La respuesta me daba error");
    }
}
