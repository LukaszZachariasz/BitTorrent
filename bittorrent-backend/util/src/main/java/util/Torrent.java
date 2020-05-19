package util;

import java.util.List;

/**
 * @author Łukasz Zachariasz
 */

public class Torrent {

    //Using to register as uuid
    String fileId;

    //Info from torrent
    String humanName;

    int pieceNumbers;

    List<String> trackerIps;


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getHumanName() {
        return humanName;
    }

    public void setHumanName(String humanName) {
        this.humanName = humanName;
    }

    public int getPieceNumbers() {
        return pieceNumbers;
    }

    public void setPieceNumbers(int pieceNumbers) {
        this.pieceNumbers = pieceNumbers;
    }

    public List<String> getTrackerIps() {
        return trackerIps;
    }

    public void setTrackerIps(List<String> trackerIps) {
        this.trackerIps = trackerIps;
    }
}
