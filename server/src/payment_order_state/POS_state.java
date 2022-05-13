package payment_order_state;

import payment_order_state.states.*;

public abstract class POS_state implements IPOS {

    public static enum POS_types {
        not_register,
        pending,
        expiration_date_timeout,
        terminated,
        confirmada,
        no_pay_method
    }

    public static POS_state getState(POS obj, POS_types type) {
        switch (type) {
            case not_register:
                return new not_register(obj);
            case pending:
                return new pending(obj);
            case expiration_date_timeout:
                return new expiration_date_timeout(obj);
            case terminated:
                return new terminated(obj);
            case confirmada:
                return new confirmada(obj);
            case no_pay_method:
                return new no_pay_method(obj);
            default:
                return new not_register(obj);
        }
    }

    String type;
    String code;

    public POS pos;

    public POS_state(POS pos, String code, String type) {
        this.type = type;
        this.code = code;
        this.pos = pos;
    }

    public void noPermited() throws Exception {
        throw new Exception("No permited: Se encuenta en estado: " + type);
    }
}
