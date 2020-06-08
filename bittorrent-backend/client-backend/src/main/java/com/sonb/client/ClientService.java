package com.sonb.client;

import org.springframework.stereotype.Service;
import util.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Łukasz Zachariasz
 */

@Service
public class ClientService {

    private final List<String> trackerIpList = new ArrayList<>();

    private final Map<String, File> fileIdToFile = new HashMap<>();

    private AtomicReference<Boolean> allClientHaveFullFile;

    private final ClientIpFetcher clientIpFetcher;
    private final ClientToTrackerConnector clientToTrackerConnector;
    private final ClientToClientConnector clientToClientConnector;
    private final ClientFileDownloader clientFileDownloader;

    public ClientService(ClientToTrackerConnector clientToTrackerConnector,
                         ClientToClientConnector clientToClientConnector,
                         ClientIpFetcher clientIpFetcher) {
        this.clientIpFetcher = clientIpFetcher;
        this.clientToTrackerConnector = clientToTrackerConnector;
        this.clientToClientConnector = clientToClientConnector;
        this.clientFileDownloader = new ClientFileDownloader(clientToClientConnector, clientToTrackerConnector);
        this.allClientHaveFullFile = new AtomicReference<>(Boolean.FALSE);
    }

    public void setAllClientHaveFullFile(Boolean stateOfHaveCompleteFile) {
        allClientHaveFullFile.set(stateOfHaveCompleteFile);
    }

    public Integer getSimulatedDownloadDelay() {
        return ClientControlBehavior.getSimulateDownloadDelay();
    }

    public void setSimulatedDownloadDelay(Integer simulatedDownloadDelay) {
        ClientControlBehavior.setSimulateDownloadDelay(simulatedDownloadDelay);
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
                        s -> PartContent.of(PartContentStatus.EXISTING, s, clientIpFetcher.getClientIp()))));

        return file;
    }

    public String downloadPart(String fileId, int partId) {

        ClientControlBehavior.simulateDownloadDelay();

        File file = fileIdToFile.get(fileId);

        if (FileExistenceStatus.NON_EXISTING.equals(file.getFileExistenceStatus())) {
            throw new RuntimeException("Part of this file not found!");
        }

        PartContent partContent = file
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

    public void removeFileFromClient(String fileId, Integer trackerId) {
        fileIdToFile.get(fileId).setFileExistenceStatus(FileExistenceStatus.NON_EXISTING);
        clientToTrackerConnector.removeFileFromClient(fileId, trackerId, clientIpFetcher.getClientIp());
    }

    public List<FileInfo> allPartIdWithStatuses() {
        return fileIdToFile.entrySet().stream()
                .map(this::mapFileToFileInfo)
                .collect(Collectors.toList());
    }


    public FileInfo mapFileToFileInfo(Map.Entry<String, File> idWithFile) {
        FileInfo fileInfo = new FileInfo();
        File file = idWithFile.getValue();

        fileInfo.setTorrentId(idWithFile.getKey());
        fileInfo.setHumanName(file.getHumanName());
        fileInfo.setFileSize(file.getFileSize());
        fileInfo.setFileExistenceStatus(file.getFileExistenceStatus());
        fileInfo.setPartIdToPartContentWithClientSourceIp(map(file.getPartIdToPartContent()));

        return fileInfo;
    }

    public Map<Integer, PartContestStatusWithClientSourceIp> map(Map<Integer, PartContent> idToPartContent) {
        return idToPartContent.entrySet().stream()
                .map(integerPartContentEntry -> new AbstractMap.SimpleEntry<>(
                        integerPartContentEntry.getKey(),
                        new PartContestStatusWithClientSourceIp(
                                integerPartContentEntry.getValue().getPartContentStatus(),
                                integerPartContentEntry.getValue().getSourceClientIp())))
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
        clientFileDownloader.downloadFile(clientIps, torrent.getFileId(), notCompletedFile, allClientHaveFullFile);
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

    public void restoreFile(String fileId) {
        fileIdToFile.get(fileId).setFileExistenceStatus(FileExistenceStatus.DOWNLOADED);
        clientToTrackerConnector.registerFileOwnerClient(fileId);
    }

    public boolean returnCheckFileExistence(String fileId) {
        File file = fileIdToFile.get(fileId);

        if (file == null) {
            return false;
        }

        return !FileExistenceStatus.NON_EXISTING.equals(file.getFileExistenceStatus());
    }
}
