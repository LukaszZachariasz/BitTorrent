package com.sonb.client;

import util.File;
import util.PartContent;
import util.PartContentStatus;

import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Łukasz Zachariasz
 */

public class FileDownloader {

    private final ClientToClientConnector clientToClientConnector;

    public FileDownloader(ClientToClientConnector clientToClientConnector) {
        this.clientToClientConnector = clientToClientConnector;
    }

    public void downloadFile(List<String> clientIps, String fileId, File notCompleteFile, Integer downloadDelay) {
        int numberOfParts = notCompleteFile.getFileSize();

        new Thread(() -> IntStream.range(0, numberOfParts)
                .boxed()
                .forEach(partNumber -> downloadPart(clientIps, partNumber, fileId, notCompleteFile, downloadDelay))
        ).start();
    }

    private void downloadPart(List<String> clientIps, Integer partId, String fileId,
                              File notCompleteFile, Integer downloadDelay) {
        try {
            Thread.sleep(downloadDelay);
        } catch (InterruptedException ie) {
            throw new RuntimeException("Error:", ie);
        }

        // TODO: improve client find
        String part = clientToClientConnector.downloadPart(clientIps.get(0), fileId, partId);
        PartContent partContent = notCompleteFile.getPartIdToPartContent().get(partId);
        partContent.setPartContentStatus(PartContentStatus.EXISTING);
        partContent.setData(part);
        notCompleteFile.checkIsCompleteFileDownloaded();
    }
}
