package util;

/**
 * @author Łukasz Zachariasz
 */

public class PartContestStatusWithClientSourceIp {

    private PartContentStatus partContentStatus;

    private String sourceClientIp;


    public PartContestStatusWithClientSourceIp() {
    }

    public PartContestStatusWithClientSourceIp(PartContentStatus partContentStatus, String sourceClientIp) {
        this.partContentStatus = partContentStatus;
        this.sourceClientIp = sourceClientIp;
    }

    public PartContentStatus getPartContentStatus() {
        return partContentStatus;
    }

    public void setPartContentStatus(PartContentStatus partContentStatus) {
        this.partContentStatus = partContentStatus;
    }

    public String getSourceClientIp() {
        return sourceClientIp;
    }

    public void setSourceClientIp(String sourceClientIp) {
        this.sourceClientIp = sourceClientIp;
    }
}
