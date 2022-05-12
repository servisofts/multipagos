package multipagos;

public class Client {

    private String name; // firstName
    private String last_name; // lastName
    private String ci; // document
    private String phone;
    private String email; // email
    private String business_name; // firstName
    private String nit; // nit

    public Client(String name, String last_name, String ci, String phone, String email, String business_name,
            String nit) {
        this.name = name;
        this.last_name = last_name;
        this.ci = ci;
        this.phone = phone;
        this.email = email;
        this.business_name = business_name;
        this.nit = nit;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public String getCi() {
        return ci;
    }

    public String getEmail() {
        return email;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getName() {
        return name;
    }

    public String getNit() {
        return nit;
    }

    public String getPhone() {
        return phone;
    }

}
