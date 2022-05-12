package payment_order_state.states;

import java.sql.SQLException;

import org.json.JSONObject;

import Component.config;
import Servisofts.SPGConect;
import multipagos.Multipagos;
import payment_order_state.POS;
import payment_order_state.POS_state;

public class not_register extends POS_state {

    public not_register(POS pos) {
        super(pos, POS_types.not_register.name(), "No registrado en la base de datos");
    }

    @Override
    public void sincronize() {
        System.out.println("Sincronizando este component");

    }

    @Override
    public void register(JSONObject data) throws Exception {
        Multipagos mp = config.getMultipagosByKeyServicio(this.pos.getData().getString("key_servicio"));
        JSONObject pay_order = this.pos.getData();
        pay_order.put("key_config", mp.getKey_config());

        JSONObject orden = mp.getOrden().create_pay_order(data);
        if (orden == null) {
            throw new Exception("No se pudo crear la orden de pago");
        }

        // String state = orden.getJSONObject("pay_order").getJSONObject("status").getString("name");

        // // if (state.equals("Pediente")) {
        // //     pay_order.put("state", "pending");
        // // }

        pay_order.put("pay_order_number", orden.getJSONObject("pay_order").getInt("pay_order_number") + "");
        pay_order.put("pay_official_number", orden.getJSONObject("pay_order").getInt("pay_official_number") + "");
        orden.getJSONObject("pay_order").remove("status");
        pay_order.put("data", orden);
        SPGConect.insertObject("payment_order", pay_order);
        this.pos.changeState(POS_types.pending, "register");
        
        this.pos.setData(pay_order);

    }
}
