package util;

/**
 * @author Łukasz Zachariasz
 */

public class TrackerInfo {

    String address;

    TrackerStatus trackerStatus;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TrackerStatus getTrackerStatus() {
        return trackerStatus;
    }

    public void setTrackerStatus(TrackerStatus trackerStatus) {
        this.trackerStatus = trackerStatus;
    }

}
