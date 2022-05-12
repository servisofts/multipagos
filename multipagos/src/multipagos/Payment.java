package multipagos;

import org.json.JSONObject;

import http.Http;

public class Payment {
    private Multipagos multipagos;
    private String url;

    private JSONObject loginData;

    public Payment(Multipagos multipagos, String url) {
        this.multipagos = multipagos;
        this.url = url;
    }

    public boolean isLogin() {
        return loginData != null;
    }

    public JSONObject login() {
        JSONObject obj = new JSONObject();
        obj.put("username", multipagos.getProvider());
        obj.put("password", multipagos.getUid());
        JSONObject data = Http.send(this.url + "api/v1/access/login", obj);
        if (data.has("statusCode")) {
            if (data.getString("statusCode").equals("10000")) {
                this.loginData = data.getJSONObject("data");
                return this.loginData;
            }
        }
        return null;
    }
}
