package com.sonb.tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Łukasz Zachariasz
 */

public class Torrent {

    //tylko to jest uzywane do rejestracji
    String fileId; //uuid

    //infoZTorrenta
    String humanName;
    int pieceNumbers;

    public String getHumanName() {
        return humanName;
    }

    public void setHumanName(String humanName) {
        this.humanName = humanName;
    }

    public int getPieceNumbers() {
        return pieceNumbers;
    }

    //FIXME: not needed in torrent registration in tracker
    List<TrackerInfo> trackerInfoList = new ArrayList<>();

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

}
