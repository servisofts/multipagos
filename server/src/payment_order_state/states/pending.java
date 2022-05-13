package payment_order_state.states;

import java.text.ParseException;
import java.util.Date;

import org.json.JSONObject;

import Component.config;
import Component.payment_order;
import Servisofts.SUtil;
import multipagos.Multipagos;
import payment_order_state.POS;
import payment_order_state.POS_state;

public class pending extends POS_state {

    public pending(POS pos) {
        super(pos, POS_types.pending.name(), "Pendiente de confirmacion");
    }

    @Override
    public void sincronize() {

        try {
    

            System.out.println("Sincronizando orden de pago" + this.pos.getData().getString("pay_order_number"));
            // System.out.println(this.pos.getData());
            JSONObject obj_sinc = new JSONObject();
            obj_sinc.put("data", this.pos.getData());
            obj_sinc.put("servicio", new JSONObject().put("key", this.pos.getData().getString("key_servicio")));

            Multipagos mp = config.getMultipagosByKeyServicio(this.pos.getData().getString("key_servicio"));
            JSONObject po = mp.getOrden()
                    .get_pay_order_by_number(Integer.parseInt(this.pos.getData().getString("pay_order_number")));
            if (po == null) {
                throw new Exception("No se encontro la orden de pago en la API de Multipagos");
            }
            if (po.getString("status_order").equals("Confirmada")) {
                this.pos.changeState(POS_types.confirmada, "sincronize");
                System.out.println("Se confirmo la orden de pago");
                return;
            }

            if (!this.pos.getData().has("expiration_date")) {
                return;
            }
            if (this.pos.getData().isNull("expiration_date")) {
                return;
            }
            String expiration_date = this.pos.getData().getString("expiration_date");
            if (expiration_date.length() <= 0) {
                return;
            }
            Date fecha_d = SUtil.parseTimestamp(expiration_date);
            if (fecha_d.compareTo(new Date()) < 0) {
                this.pos.changeState(POS_types.expiration_date_timeout, "sincronize");
                System.out.println("sincronize::expiration_date_timeout");
                // this.pos.sincronize();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void register(JSONObject data) {
        // TODO Auto-generated method stub

    }

}
