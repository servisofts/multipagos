package multipagos.type;

import org.json.JSONArray;
import org.json.JSONObject;

import multipagos.Client;
import multipagos.Payment;

public class PTCreditCard extends PaymentType {

    public PTCreditCard(Payment p, Client cliente, String gloss,
            String payOrderNumber, String totalAmmount) {
        super(p, cliente.getCi(), cliente.getEmail(), cliente.getName(), cliente.getLast_name(), gloss,
                cliente.getNit(),
                payOrderNumber, cliente.getPhone(), totalAmmount, "cyber_source");

    }

    @Override
    public JSONObject toJson() {
        JSONArray componentData = new JSONArray();
        JSONArray extraData = new JSONArray();
        return super.toJson().put("componentData", componentData).put("extraData", extraData);
    }

    @Override
    public JSONObject onSucces(JSONObject response) {
        // TODO Auto-generated method stub
        return null;
    }
}
