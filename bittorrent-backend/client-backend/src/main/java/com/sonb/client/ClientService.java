package com.sonb.client;

import org.springframework.stereotype.Service;
import util.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Łukasz Zachariasz
 */

@Service
public class ClientService {

    private final List<String> trackerIpList = new ArrayList<>();

    private final Map<String, File> fileIdToFile = new HashMap<>();

    private Integer simulatedDownloadDelay = 5000; // five sec. delay for download part of file

    private final ClientToTrackerConnector clientToTrackerConnector;
    private final ClientToClientConnector clientToClientConnector;
    private final FileDownloader fileDownloader;

    public ClientService(ClientToTrackerConnector clientToTrackerConnector,
                         ClientToClientConnector clientToClientConnector) {
        this.clientToTrackerConnector = clientToTrackerConnector;
        this.clientToClientConnector = clientToClientConnector;
        this.fileDownloader = new FileDownloader(clientToClientConnector);
    }


    public Integer getSimulatedDownloadDelay() {
        return simulatedDownloadDelay;
    }

    public void setSimulatedDownloadDelay(Integer simulatedDownloadDelay) {
        this.simulatedDownloadDelay = simulatedDownloadDelay;
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
        file.setFileExistenceStatus(FileExistenceStatus.DOWNLOADED);
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
            Thread.sleep(simulatedDownloadDelay);
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

    public List<FileInfo> allPartIdWithStatuses() {
        return fileIdToFile.entrySet().stream()
                .map(this::mapFileToFileInfo)
                .collect(Collectors.toList());
    }


    public FileInfo mapFileToFileInfo(Map.Entry<String, File> idWithFile) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setTorrentId(idWithFile.getKey());
        File file = idWithFile.getValue();
        fileInfo.setHumanName(file.getHumanName());
        fileInfo.setFileSize(file.getFileSize());
        fileInfo.setFileExistenceStatus(file.getFileExistenceStatus());
        fileInfo.setPartIdToPartContent(map(file.getPartIdToPartContent()));
        return fileInfo;
    }

    public Map<Integer, PartContentStatus> map(Map<Integer, PartContent> idToPartContent) {
        return idToPartContent.entrySet().stream()
                .map(integerPartContentEntry -> new AbstractMap.SimpleEntry<>(integerPartContentEntry.getKey(), integerPartContentEntry.getValue().getPartContentStatus()))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    public void downloadFile(Torrent torrent) {
        System.out.println("Client as owners of file assigned in torrent file:");
        List<String> clientIps = clientToTrackerConnector.clientOwnersForFileId(torrent.getFileId());

        if (clientIps.size() == 0) {
            fileIdToFile.put(torrent.getFileId(), createNotExistingFile(torrent));
        }

        File notCompletedFile = createNotCompletedFile(torrent);
        fileIdToFile.put(torrent.getFileId(), notCompletedFile);
        clientToTrackerConnector.registerFileOwnerClient(torrent.getFileId());
        fileDownloader.downloadFile(clientIps, torrent.getFileId(), notCompletedFile, simulatedDownloadDelay);
    }

    private File createNotCompletedFile(Torrent torrent) {
        File file = new File();
        file.setHumanName(torrent.getHumanName());
        file.setFileExistenceStatus(FileExistenceStatus.NOT_COMPLETE);
        file.setFileSize(torrent.getPieceNumbers());

        Map<Integer, PartContent> partIdToPartContent = IntStream.range(0, torrent.getPieceNumbers())
                .boxed()
                .collect(Collectors.toMap(partId -> partId, __ -> PartContent.nonExistingPartContent()));

        file.setPartIdToPartContent(partIdToPartContent);

        return file;
    }

    private File createNotExistingFile(Torrent torrent) {
        File file = new File();
        file.setHumanName(torrent.getHumanName());
        file.setFileExistenceStatus(FileExistenceStatus.NON_EXISTING);
        file.setPartIdToPartContent(new HashMap<>());
        file.setFileSize(torrent.getPieceNumbers());
        return file;
    }

}
