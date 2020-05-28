package com.sonb.client;

import util.File;
import util.PartContent;
import util.PartContentStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Łukasz Zachariasz
 */

public class FileDownloader {

    private final ThreadLocalRandom threadLocalRandom;

    private final ClientToClientConnector clientToClientConnector;

    public FileDownloader(ClientToClientConnector clientToClientConnector) {
        this.clientToClientConnector = clientToClientConnector;
        threadLocalRandom = ThreadLocalRandom.current();
    }

    public void downloadFile(List<String> clientIps, String fileId, File notCompleteFile, Integer downloadDelay) {
        int numberOfParts = notCompleteFile.getFileSize();

        new Thread(() -> {
            ArrayList<Integer> shuffleOrderListOfPartNumbers = IntStream.range(0, numberOfParts)
                    .boxed()
                    .collect(Collectors.toCollection(ArrayList::new));
            Collections.shuffle(shuffleOrderListOfPartNumbers);
            shuffleOrderListOfPartNumbers
                    .forEach(partNumber -> downloadPart(clientIps, partNumber, fileId, notCompleteFile, downloadDelay));
        }).start();
    }

    private void downloadPart(List<String> clientIps, Integer partId, String fileId,
                              File notCompleteFile, Integer downloadDelay) {
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
