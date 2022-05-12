package multipagos.ptype;

import multipagos.Client;

public class PTEfectivo extends PaymentType {

    public PTEfectivo(Client cliente, String gloss,
            String payOrderNumber, String totalAmmount) {
        super(cliente.getCi(), cliente.getEmail(), cliente.getName(), cliente.getLast_name(), gloss, cliente.getNit(),
                payOrderNumber, cliente.getPhone(), totalAmmount, "presencial");
                
    }

}
