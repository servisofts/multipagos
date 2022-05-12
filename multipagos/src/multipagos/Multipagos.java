package multipagos;

public class Multipagos {
    private Order orden;
    private Payment pago;
    private String serviceCode;
    private String provider;
    private String uid;

    public Multipagos(String url, String url_pago, String serviceCode, String provider, String uid) {
        this.orden = new Order(this, url);
        this.pago = new Payment(this, url_pago);
        this.serviceCode = serviceCode;
        this.provider = provider;
        this.uid = uid;
    }

    public Order getOrden() {
        return orden;
    }

    public Payment getPago() {
        return pago;
    }

    public String getProvider() {
        return provider;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public String getUid() {
        return uid;
    }
}
