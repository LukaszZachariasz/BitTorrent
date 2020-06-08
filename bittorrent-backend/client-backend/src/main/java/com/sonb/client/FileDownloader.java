package com.sonb.client;

import util.File;
import util.FileExistenceStatus;
import util.PartContent;
import util.PartContentStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Łukasz Zachariasz
 */

public class FileDownloader {

    private static final int MAX_RETRIES_TO_DOWNLOAD_PART = 10;

    private final ThreadLocalRandom threadLocalRandom;

    private final ClientToClientConnector clientToClientConnector;
    private final ClientToTrackerConnector clientToTrackerConnector;
    private final ClientManagement clientManagement;

    public FileDownloader(ClientToClientConnector clientToClientConnector,
                          ClientToTrackerConnector clientToTrackerConnector) {
        this.clientToClientConnector = clientToClientConnector;
        this.clientToTrackerConnector = clientToTrackerConnector;
        this.threadLocalRandom = ThreadLocalRandom.current();
        this.clientManagement = new ClientManagement();
    }

    public void downloadFile(List<String> clientIps, String fileId, File notCompleteFile, Integer downloadDelay,
                             AtomicReference<Boolean> allClientHaveFullFile) {
        int numberOfParts = notCompleteFile.getFileSize();

        new Thread(() -> {
            ArrayList<Integer> shuffleOrderListOfPartNumbers = IntStream.range(0, numberOfParts)
                    .boxed()
                    .collect(Collectors.toCollection(ArrayList::new));
            Collections.shuffle(shuffleOrderListOfPartNumbers);
            shuffleOrderListOfPartNumbers
                    .forEach(partNumber ->
                            downloadPart(clientIps, partNumber, fileId,
                                    notCompleteFile, downloadDelay, allClientHaveFullFile));

            if (!FileExistenceStatus.DOWNLOADED.equals(notCompleteFile.getFileExistenceStatus())) {
                notCompleteFile.setFileExistenceStatus(FileExistenceStatus.TRY_AGAIN);
            }

        }).start();
    }

    private void downloadPart(List<String> clientIps, Integer partId, String fileId,
                              File notCompleteFile, Integer downloadDelay,
                              AtomicReference<Boolean> allClientHaveFullFile) {

        // TODO: refactor sleep and add in "infinity" loop downloading to try again when not complete file


        if (Boolean.TRUE.equals(allClientHaveFullFile.get())) {
            tryDownloadPartOfFileJustOnce(clientIps, partId, fileId, notCompleteFile, downloadDelay);
        } else {
            tryDownloadPartOfFileContinuous(clientIps, partId, fileId, notCompleteFile, downloadDelay, allClientHaveFullFile);
        }
    }

    private void tryDownloadPartOfFileJustOnce(List<String> clientIps, Integer partId, String fileId,
                                               File notCompleteFile, Integer downloadDelay) {

        PartContent partContent = notCompleteFile.getPartIdToPartContent().get(partId);
        if (PartContentStatus.EXISTING.equals(partContent.getPartContentStatus())) {
            return;
        }
        delayDownloading(downloadDelay);
        int clientPickedNumber = clientManagement.getAvailableClientNumber(clientIps);
        String clientPickedIp = clientIps.get(clientPickedNumber);
        String part = clientToClientConnector.downloadPart(clientPickedIp, fileId, partId);
        partContent.setPartContentStatus(PartContentStatus.EXISTING);
        partContent.setSourceClientIp(clientPickedIp);
        partContent.setData(part);
        notCompleteFile.checkIsCompleteFileDownloaded();
    }

    private void tryDownloadPartOfFileContinuous(List<String> clientIps, Integer partId, String fileId,
                                                 File notCompleteFile, Integer downloadDelay,
                                                 AtomicReference<Boolean> allClientHaveFullFile) {

        int numberOfRetriesFromOneClientIp = 0;
        List<String> newClientIps = new ArrayList<>(clientIps);

        while (Boolean.FALSE.equals(allClientHaveFullFile.get())) {
            delayDownloading(downloadDelay);

            if (numberOfRetriesFromOneClientIp == MAX_RETRIES_TO_DOWNLOAD_PART) {
                numberOfRetriesFromOneClientIp = 0;
                // Fetching new other potential owners of this file
                newClientIps = clientToTrackerConnector.clientOwnersForFileId(fileId);
            }

            // In loop try to download also with new clientIps list
            try {
                tryDownloadPartOfFileJustOnce(newClientIps, partId, fileId, notCompleteFile, downloadDelay);
                System.out.println(String.format("Downloaded for file: %s Part number: %s", fileId, partId));
                break;
            } catch (Exception e) {
                System.out.println(String.format("Can't download for file: %s Part number: %s", fileId, partId));
                System.out.println("File is still not complete, retrying...");
            }
            numberOfRetriesFromOneClientIp++;
        }
    }

    private void delayDownloading(Integer downloadDelay) {
        try {
            Thread.sleep(downloadDelay);
        } catch (InterruptedException ie) {
            throw new RuntimeException("Error:", ie);
        }
    }
}
