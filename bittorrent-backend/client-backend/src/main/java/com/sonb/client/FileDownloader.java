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

    private final ThreadLocalRandom threadLocalRandom;

    private final ClientToClientConnector clientToClientConnector;
    private final ClientToTrackerConnector clientToTrackerConnector;

    public FileDownloader(ClientToClientConnector clientToClientConnector,
                          ClientToTrackerConnector clientToTrackerConnector) {
        this.clientToClientConnector = clientToClientConnector;
        this.clientToTrackerConnector = clientToTrackerConnector;
        threadLocalRandom = ThreadLocalRandom.current();
    }

    public void downloadFile(List<String> clientIps, String fileId, File notCompleteFile, Integer downloadDelay, AtomicReference<Boolean> allClientHaveFullFile) {
        int numberOfParts = notCompleteFile.getFileSize();

        new Thread(() -> {
            ArrayList<Integer> shuffleOrderListOfPartNumbers = IntStream.range(0, numberOfParts)
                    .boxed()
                    .collect(Collectors.toCollection(ArrayList::new));
            Collections.shuffle(shuffleOrderListOfPartNumbers);
            shuffleOrderListOfPartNumbers
                    .forEach(partNumber -> downloadPart(clientIps, partNumber, fileId, notCompleteFile, downloadDelay, allClientHaveFullFile));

            if (!FileExistenceStatus.DOWNLOADED.equals(notCompleteFile.getFileExistenceStatus())) {
                notCompleteFile.setFileExistenceStatus(FileExistenceStatus.TRY_AGAIN);
            }

        }).start();
    }

    private void downloadPart(List<String> clientIps, Integer partId, String fileId,
                              File notCompleteFile, Integer downloadDelay,
                              AtomicReference<Boolean> allClientHaveFullFile) {

        // TODO: refactor sleep and add in "infinity" loop downloading to try again when not complete file

        try {
            Thread.sleep(downloadDelay);
        } catch (InterruptedException ie) {
            throw new RuntimeException("Error:", ie);
        }

        int clientPickedNumber = threadLocalRandom.nextInt(0, clientIps.size());

        String clientPickedIp = clientIps.get(clientPickedNumber);

        String part = clientToClientConnector.downloadPart(clientPickedIp, fileId, partId);

        PartContent partContent = notCompleteFile.getPartIdToPartContent().get(partId);

        partContent.setPartContentStatus(PartContentStatus.EXISTING);
        partContent.setSourceClientIp(clientPickedIp);
        partContent.setData(part);

        notCompleteFile.checkIsCompleteFileDownloaded();
    }
}
