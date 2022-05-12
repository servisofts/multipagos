package payment_order_state;

import java.sql.SQLException;

import org.json.JSONObject;

import Servisofts.SPGConect;
import Servisofts.SUtil;

public class POS implements IPOS {

    private String key;
    private JSONObject data;

    private POS_state state;

    public POS(JSONObject data) {
        this.data = data;
        this.key = data.getString("key");
        if (data.has("state")) {
            this.state = POS_state.getState(this, POS_state.POS_types.valueOf(data.getString("state")));
        } else {
            this.state = POS_state.getState(this, POS_state.POS_types.not_register);
        }

    }

    public void changeState(POS_state.POS_types state, String action) {
        String old_state = this.state.code + "";
        this.state = POS_state.getState(this, state);
        this.data.put("state", this.state.code);
        JSONObject movimiento = new JSONObject();
        movimiento.put("key", SUtil.uuid());
        movimiento.put("key_payment_order", this.key);
        movimiento.put("fecha_on", SUtil.now());
        movimiento.put("estado", 1);
        movimiento.put("old_state", old_state);
        movimiento.put("state", this.state.code);
        movimiento.put("action", action);
        try {
            SPGConect.insertObject("payment_order_state", movimiento);
            JSONObject obj_to_edit = new JSONObject();
            obj_to_edit.put("key", this.key);
            obj_to_edit.put("state", this.state.code);
            SPGConect.editObject("payment_order", obj_to_edit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getData() {
        return data;
    }
    public void setData(JSONObject data) {
        this.data = data;
    }
    public JSONObject toJson(){
        return this.data;
    }

    @Override
    public void sincronize() throws Exception {
        this.state.sincronize();
    }

    @Override
    public void register(JSONObject data) throws Exception {
        this.state.register(data);
    }

}
