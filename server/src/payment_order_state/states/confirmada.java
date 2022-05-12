package payment_order_state.states;

import java.io.Console;

import org.json.JSONObject;

import Server.SSSAbstract.SSServerAbstract;
import Server.SSSAbstract.SSSessionAbstract;
import Servisofts.SConsole;
import payment_order_state.POS;
import payment_order_state.POS_state;

public class confirmada extends POS_state {

    public confirmada(POS pos) {
        super(pos, POS_types.confirmada.name(), "Terminado");
    }

    @Override
    public void sincronize() {
        String key_serivicio = this.pos.getData().getString("key_servicio");
        SSSessionAbstract sesion = SSServerAbstract.getSessionByKeyServicio(key_serivicio);
        if (sesion == null) {
            return;
        }
        JSONObject objSend = new JSONObject();
        objSend.put("component", "payment_order");
        objSend.put("type", "on_change_state");
        objSend.put("data", this.pos.getData());
        objSend.put("estado", "cargando");
        JSONObject resp = sesion.sendSync(objSend, 3000);
        if (resp.getString("estado").equals("exito")) {
            this.pos.changeState(POS_types.terminated, "sincronize::on_change_state");
        }
    }

    @Override
    public void register(JSONObject data) {
        // TODO Auto-generated method stub

    }
}
