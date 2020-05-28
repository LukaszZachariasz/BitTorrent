package util;

/**
 * @author Łukasz Zachariasz
 */

public class PartContent {

    PartContentStatus partContentStatus;

    String sourceClientIp;

    String data;

    public static PartContent nonExistingPartContent() {
        PartContent partContent = new PartContent();
        partContent.setData(null);
        partContent.setPartContentStatus(PartContentStatus.WAITING_FOR_DOWNLOAD);

        return partContent;
    }

    public static PartContent of(PartContentStatus partContentStatus, String data, String sourceClientIp) {
        PartContent partContent = new PartContent();
        partContent.setData(data);
        partContent.setPartContentStatus(partContentStatus);
        partContent.setSourceClientIp(sourceClientIp);

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

    public String getSourceClientIp() {
        return sourceClientIp;
    }

    public void setSourceClientIp(String sourceClientIp) {
        this.sourceClientIp = sourceClientIp;
    }
}
