package util;

/**
 * @author Łukasz Zachariasz
 */

public class PartIdWithPartStatus {

    int partId;

    PartContentStatus partContentStatus;


    public PartIdWithPartStatus(Integer key, PartContentStatus value) {
        this.partId = key;
        this.partContentStatus = value;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public PartContentStatus getPartContentStatus() {
        return partContentStatus;
    }

    public void setPartContentStatus(PartContentStatus partContentStatus) {
        this.partContentStatus = partContentStatus;
    }
}
