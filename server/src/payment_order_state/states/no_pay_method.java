package payment_order_state.states;

import org.json.JSONObject;

import payment_order_state.POS;
import payment_order_state.POS_state;

public class no_pay_method extends POS_state {

    public no_pay_method(POS pos) {
        super(pos, POS_types.no_pay_method.name(), "No pay method");
    }

    @Override
    public void sincronize() {
      
    }

    @Override
    public void register(JSONObject data) {
        // TODO Auto-generated method stub

    }
}
