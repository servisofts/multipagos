package multipagos.type;

import org.json.JSONObject;

import multipagos.Client;
import multipagos.Payment;

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
}
