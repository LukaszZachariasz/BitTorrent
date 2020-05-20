package util;

/**
 * @author Łukasz Zachariasz
 */

public class PartContent {

    PartContentStatus partContentStatus;

    String data;

    public static PartContent nonExistingPartContent() {
        PartContent partContent = new PartContent();
        partContent.setData(null);
        partContent.setPartContentStatus(PartContentStatus.WAITING_FOR_DOWNLOAD);

        return partContent;
    }

    public static PartContent of(PartContentStatus partContentStatus, String data) {
        PartContent partContent = new PartContent();
        partContent.setData(data);
        partContent.setPartContentStatus(partContentStatus);

        return partContent;
    }

    public PartContentStatus getPartContentStatus() {
        return partContentStatus;
    }

    public void setPartContentStatus(PartContentStatus partContentStatus) {
        this.partContentStatus = partContentStatus;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
