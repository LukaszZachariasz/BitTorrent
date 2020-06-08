package util;

import java.util.Map;

/**
 * @author Łukasz Zachariasz
 */

public class File {

    String humanName;

    int fileSize;

    Map<Integer, PartContent> partIdToPartContent;

    FileExistenceStatus fileExistenceStatus;


    public String getHumanName() {
        return humanName;
    }

    public void setHumanName(String humanName) {
        this.humanName = humanName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public Map<Integer, PartContent> getPartIdToPartContent() {
        return partIdToPartContent;
    }

    public void setPartIdToPartContent(Map<Integer, PartContent> partIdToPartContent) {
        this.partIdToPartContent = partIdToPartContent;
    }

    String getFileContent() {
        return partIdToPartContent.values()
                .stream()
                .map(partContent -> partContent.data)
                .reduce(String::concat)
                .orElseThrow(() -> new IllegalStateException("File is empty!"));
    }

    public FileExistenceStatus getFileExistenceStatus() {
        return fileExistenceStatus;
    }

    public void setFileExistenceStatus(FileExistenceStatus fileExistenceStatus) {
        this.fileExistenceStatus = fileExistenceStatus;
    }

    public void checkIsCompleteFileDownloaded() {
        if (isFileComplete()) {
            System.out.println("File has been downloaded!");
            fileExistenceStatus = FileExistenceStatus.DOWNLOADED;
        }
    }

    private boolean isFileComplete() {
        return partIdToPartContent
                .values()
                .stream()
                .allMatch(el -> el.getPartContentStatus().equals(PartContentStatus.EXISTING));
    }
}
