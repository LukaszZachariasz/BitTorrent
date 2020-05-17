package util;

import java.util.Map;

/**
 * @author Łukasz Zachariasz
 */

public class Client {

    String clientIp;

    int numbersOfParts;

    Map<Integer, PartStatus> partIdToStatus;


    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public int getNumbersOfParts() {
        return numbersOfParts;
    }

    public void setNumbersOfParts(int numbersOfParts) {
        this.numbersOfParts = numbersOfParts;
    }

    public Map<Integer, PartStatus> getPartIdToStatus() {
        return partIdToStatus;
    }

    public void setPartIdToStatus(Map<Integer, PartStatus> partIdToStatus) {
        this.partIdToStatus = partIdToStatus;
    }
}
