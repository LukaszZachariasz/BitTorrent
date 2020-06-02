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

    Map<Integer, PartContestStatusWithClientSourceIp> partIdToPartContentWithClientSourceIp;


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

    public FileExistenceStatus getFileExistenceStatus() {
        return fileExistenceStatus;
    }

    public void setFileExistenceStatus(FileExistenceStatus fileExistenceStatus) {
        this.fileExistenceStatus = fileExistenceStatus;
    }

    public Map<Integer, PartContestStatusWithClientSourceIp> getPartIdToPartContentWithClientSourceIp() {
        return partIdToPartContentWithClientSourceIp;
    }

    public void setPartIdToPartContentWithClientSourceIp(Map<Integer,
            PartContestStatusWithClientSourceIp> partIdToPartContentWithClientSourceIp) {
        this.partIdToPartContentWithClientSourceIp = partIdToPartContentWithClientSourceIp;
    }
}
