package util;

/**
 * @author Łukasz Zachariasz
 */

public class Client {

    String clientIp;

    public Client(String clientIp) {
        this.clientIp = clientIp;
    }


    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

}
