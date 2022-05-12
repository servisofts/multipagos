package payment_order_state;

import org.json.JSONObject;

public interface IPOS {
    public void register(JSONObject data) throws Exception;
    public void sincronize() throws Exception;
}
