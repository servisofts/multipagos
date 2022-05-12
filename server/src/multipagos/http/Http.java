package multipagos.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

public class Http {

    public static JSONObject send(String urls, JSONObject data) {
        return send(urls, data, null);
    }

    public static JSONObject send(String urls, JSONObject data, String Authorization) {
        try {
            URL url = new URL(urls);
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            if (Authorization != null) {
                con.setRequestProperty("Authorization", Authorization);
            }
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = data.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String resp = "";
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                resp += inputLine;
            }
            br.close();
            return new JSONObject(resp);
        } catch (MalformedURLException e) {
            System.out.println(e.getLocalizedMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}