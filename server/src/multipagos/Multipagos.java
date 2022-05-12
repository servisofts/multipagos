package multipagos;

public class Multipagos {
    private Order orden;
    private Payment pago;
    private String serviceCode;
    private String provider;
    private String uid;
    private String key_config;

    public Multipagos(String key_config, String url, String url_pago, String serviceCode, String provider, String uid) {
        this.key_config = key_config;
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

    public void setOrden(Order orden) {
        this.orden = orden;
    }

    public void setPago(Payment pago) {
        this.pago = pago;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKey_config() {
        return this.key_config;
    }

    public void setKey_config(String key_config) {
        this.key_config = key_config;
    }

}
