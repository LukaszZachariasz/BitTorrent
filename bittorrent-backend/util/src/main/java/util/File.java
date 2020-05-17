package util;

import java.util.Map;

/**
 * @author Łukasz Zachariasz
 */

public class File {

    String humanName;

    int fileSize;

    Map<Integer, PartContent> partIdToPartContent;


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
        return partIdToPartContent.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .map(partContent -> partContent.data)
                .reduce(String::concat)
                .orElseThrow(() -> new IllegalStateException("File is empty!"));
    }
}
