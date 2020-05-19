package com.sonb.client;

import org.springframework.stereotype.Service;
import util.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Łukasz Zachariasz
 */

@Service
public class ClientService {

    private final List<String> trackerIpList = new ArrayList<>();

    private final Map<String, File> fileIdToFile = new HashMap<>();

    private Integer sleepValue = 50;

    private final ClientToTrackerConnector clientToTrackerConnector;

    public ClientService(ClientToTrackerConnector clientToTrackerConnector) {
        this.clientToTrackerConnector = clientToTrackerConnector;
    }


    public Integer getSleepValue() {
        return sleepValue;
    }

    public void setSleepValue(Integer sleepValue) {
        this.sleepValue = sleepValue;
    }

    public Torrent createTorrent(ClientFileRequest clientFileRequest) {
        Torrent torrent = new Torrent();
        String fileId = UUID.randomUUID().toString();
        torrent.setFileId(fileId);
        torrent.setHumanName(clientFileRequest.getHumanName());
        torrent.setTrackerIps(clientToTrackerConnector.getTrackerList());
        File file = createFile(clientFileRequest);
        torrent.setPieceNumbers(file.getFileSize());
        fileIdToFile.put(fileId, file);
        clientToTrackerConnector.registerTorrent(torrent);
        return torrent;
    }

    private File createFile(ClientFileRequest clientFileRequest) {
        var file = new File();
        file.setHumanName(clientFileRequest.getHumanName());
        file.setFileSize(clientFileRequest.getValue().size());

        AtomicInteger atomicInteger = new AtomicInteger(0);

        file.setPartIdToPartContent(clientFileRequest.getValue()
                .stream()
                .collect(Collectors.toMap(s -> atomicInteger.getAndIncrement(),
                        s -> PartContent.of(PartContentStatus.EXISTING, s))));

        return file;
    }

    public String downloadPart(String fileId, int partId) {
        sleep();
        PartContent partContent = fileIdToFile.get(fileId)
                .getPartIdToPartContent()
                .get(partId);

        if (PartContentStatus.EXISTING.equals(partContent.getPartContentStatus())) {
            return partContent.getData();
        }

        throw new RuntimeException("Not found part of this file!");
    }

    public List<PartIdWithPartStatus> partIdWithStatuses(String fileId) {
        return fileIdToFile.get(fileId)
                .getPartIdToPartContent()
                .entrySet()
                .stream()
                .map(integerPartContentEntry ->
                        new PartIdWithPartStatus(integerPartContentEntry.getKey(),
                                integerPartContentEntry.getValue().getPartContentStatus()))
                .collect(Collectors.toList());
    }

    void sleep() {
        try {
            Thread.sleep(sleepValue);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    public void removeFileFromClient(String fileId, String trackerId) {
        fileIdToFile.remove(fileId);
        clientToTrackerConnector.removeFileFromClient(fileId, trackerId, fetchMyIp());
    }

    private String fetchMyIp() {
        return "NOT YET IMPLEMENTED";
    }
}
