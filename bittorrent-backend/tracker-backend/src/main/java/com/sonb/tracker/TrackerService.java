package com.sonb.tracker;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

    private final RestTemplate restTemplate;


    public TrackerService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.clientsList = new ArrayList<>();
        this.fileIdToClients = new HashMap<>();
    }

    public List<String> getClients(String fileId) {
        removeClientWhoDontHaveAFile(fileId);
        return fileIdToClients.getOrDefault(fileId, new ArrayList<>())
                .stream()
                .map(Client::getClientIp)
                .collect(Collectors.toList());
    }

    private void removeClientWhoDontHaveAFile(String fileId) {
        fileIdToClients.getOrDefault(fileId, new ArrayList<>())
                .removeIf(client -> !checkFileExistenceInClient(client.getClientIp(), fileId));
    }

    @SuppressWarnings("ConstantConditions")
    private boolean checkFileExistenceInClient(String clientIp, String fileId) {
        String url = clientIp + "/checkFileExistence/" + fileId;
        Boolean object = restTemplate.getForObject(url, Boolean.class);
        System.out.println(String.format("Client on ip: %s Now have not a file: %s", clientIp, fileId));
        return object;
    }

    public void registerTorrent(RegisterTorrentRq registerTorrentRq) {
        fileIdToClients.put(registerTorrentRq.getFileId(), generateFirstClient(registerTorrentRq));
    }

    private List<Client> generateFirstClient(RegisterTorrentRq registerTorrentRq) {
        Client client = new Client(registerTorrentRq.getClientIp());

        return new ArrayList<>() {{
            add(client);
        }};
    }

    public void removeFileFromClient(String fileId, String clientIp) {
        List<Client> clients = fileIdToClients.get(fileId);
        clients.removeIf(client -> client.getClientIp().equals(clientIp));
    }

    public void registerFileOwnerClient(String fileId, String clientIp) {
        fileIdToClients.get(fileId).add(new Client(clientIp));
    }

}
