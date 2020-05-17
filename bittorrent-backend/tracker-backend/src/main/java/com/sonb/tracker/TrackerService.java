package com.sonb.tracker;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public void registerTorrent(Torrent torrent, String clientIp) {
        fileIdToClients.put(torrent.getFileId(), generateFirstClient(torrent, clientIp));
    }

    private List<Client> generateFirstClient(Torrent torrent, String clientIp) {
        Client client = new Client();
        client.setClientIp(clientIp);
        client.setNumbersOfParts(torrent.getNumberOfParts());

        Map<Integer, PartStatus> collect = IntStream.iterate(0, operand -> operand < torrent.getNumberOfParts(), operand -> operand++)
                .mapToObj(value -> value)
                .collect(Collectors.toMap(o -> o, __ -> PartStatus.DOWNLOADED));

        //client.setPartIdToStatus(collect);
        return new ArrayList<>() {{
            add(client);
        }};
    }

}
