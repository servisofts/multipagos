import org.json.JSONObject;

import http.Http;
import multipagos.Client;
import multipagos.Multipagos;
import multipagos.ptype.PTEfectivo;
import multipagos.ptype.PaymentType;

public class App {
    public static void main(String[] args) throws Exception {

        Multipagos multipago = new Multipagos("https://stagingmultipago.multipago.com/",
                "https://staginggatewaypagos.multipago.com/", "TAPEKE", "tapeke", "8a01f2aba40d169146151e055ac23a71");

        // JSONObject reso = multipago.getOrden().get_pay_order_by_number(75738);
        // System.out.println(reso);
        Client cli = new Client("Ricatdo", "Paz", "6392496", "75395848", "rickypazd@icloud.com", "", "");

        PaymentType pType = new PTEfectivo(cli, "gloss", "75738", "5");
        System.out.println(pType.toJson());

    }
}
