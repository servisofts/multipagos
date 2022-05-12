import Servisofts.Servisofts;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            Servisofts.DEBUG = false;
            Servisofts.Manejador = Manejador::onMessage;
            multipagos.sincronizador.hilo.init();
            Servisofts.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
