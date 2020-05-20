package util;

import java.util.Map;

/**
 * @author Mateusz Zakrzewski
 */
public class FileInfo {

    int fileSize;

    String torrentId;

    String humanName;

    FileExistenceStatus fileExistenceStatus;

    Map<Integer, PartContentStatus> partIdToPartContent;


    public String getTorrentId() {
        return torrentId;
    }

    public void setTorrentId(String torrentId) {
        this.torrentId = torrentId;
    }

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

    public Map<Integer, PartContentStatus> getPartIdToPartContent() {
        return partIdToPartContent;
    }

    public void setPartIdToPartContent(Map<Integer, PartContentStatus> partIdToPartContent) {
        this.partIdToPartContent = partIdToPartContent;
    }

    public FileExistenceStatus getFileExistenceStatus() {
        return fileExistenceStatus;
    }

    public void setFileExistenceStatus(FileExistenceStatus fileExistenceStatus) {
        this.fileExistenceStatus = fileExistenceStatus;
    }
}
