package com.sonb.tracker;

import org.springframework.stereotype.Service;
import util.Client;
import util.RegisterTorrentRq;
import util.TrackerStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Łukasz Zachariasz
 */

@Service
public class TrackerService {


    private final TrackerStatus trackerStatus = TrackerStatus.UP;

    private final List<String> clientsList;

    private final Map<String, List<Client>> fileIdToClients;


    public TrackerService() {
        this.clientsList = new ArrayList<>();
        this.fileIdToClients = new HashMap<>();
    }

    public List<String> getClients(String fileId) {
        return fileIdToClients.get(fileId)
                .stream()
                .map(Client::getClientIp)
                .collect(Collectors.toList());
    }

    public void registerTorrent(RegisterTorrentRq registerTorrentRq, String clientIp) {
        fileIdToClients.put(registerTorrentRq.getFileId(), generateFirstClient(registerTorrentRq, clientIp));
    }

    private List<Client> generateFirstClient(RegisterTorrentRq registerTorrentRq, String clientIp) {
        Client client = new Client();
        client.setClientIp(clientIp);

        return new ArrayList<>() {{
            add(client);
        }};
    }

    public void removeFileFromClient(String fileId, String clientIp) {
        List<Client> clients = fileIdToClients.get(fileId);
        clients.removeIf(client -> client.getClientIp().equals(clientIp));
    }

}
