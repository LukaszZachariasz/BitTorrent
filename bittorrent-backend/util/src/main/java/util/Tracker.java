package util;

import java.util.List;
import java.util.Map;

/**
 * @author Łukasz Zachariasz
 */

public class Tracker {

    Map<String, List<Client>> fileIdToClients;

    public Map<String, List<Client>> getFileIdToClients() {
        return fileIdToClients;
    }

    public void setFileIdToClients(Map<String, List<Client>> fileIdToClients) {
        this.fileIdToClients = fileIdToClients;
    }

}
