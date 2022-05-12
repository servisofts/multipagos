package payment_order_state.states;

import java.io.Console;

import org.json.JSONObject;

import Server.SSSAbstract.SSServerAbstract;
import Server.SSSAbstract.SSSessionAbstract;
import Servisofts.SConsole;
import payment_order_state.POS;
import payment_order_state.POS_state;

public class terminated extends POS_state {

    public terminated(POS pos) {
        super(pos, POS_types.terminated.name(), "Terminado");
    }

    @Override
    public void sincronize() {
        
    }

    @Override
    public void register(JSONObject data) {
        // TODO Auto-generated method stub

    }
}
