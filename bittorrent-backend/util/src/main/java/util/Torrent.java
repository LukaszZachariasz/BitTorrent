package util;

import java.util.ArrayList;
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

    //FIXME: not needed in torrent registration in tracker
    List<TrackerInfo> trackerInfoList = new ArrayList<>();

    public String getHumanName() {
        return humanName;
    }

    public void setHumanName(String humanName) {
        this.humanName = humanName;
    }

    public int getPieceNumbers() {
        return pieceNumbers;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public int getNumberOfParts() {
        return pieceNumbers;
    }

    public void setPieceNumbers(int pieceNumbers) {
        this.pieceNumbers = pieceNumbers;
    }

    public List<TrackerInfo> getTrackerInfoList() {
        return trackerInfoList;
    }

    public void setTrackerInfoList(List<TrackerInfo> trackerInfoList) {
        this.trackerInfoList = trackerInfoList;
    }

    public List<String> getTrackerIps() {
        return trackerIps;
    }

    public void setTrackerIps(List<String> trackerIps) {
        this.trackerIps = trackerIps;
    }
}
