package cl.bennu.plcbus.domain;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: _Camilo
 * Date: 19-03-14
 * Time: 09:12 PM
 */
public class StatusResponse  implements Serializable {

    private Boolean status;
    private Long dimmerFadeRate;
    private Long brightLevel;

    @Override
    public String toString() {
        return "StatusResponse{" +
                "status=" + status +
                ", dimmerFadeRate=" + dimmerFadeRate +
                ", brightLevel=" + brightLevel +
                '}';
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getDimmerFadeRate() {
        return dimmerFadeRate;
    }

    public void setDimmerFadeRate(Long dimmerFadeRate) {
        this.dimmerFadeRate = dimmerFadeRate;
    }

    public Long getBrightLevel() {
        return brightLevel;
    }

    public void setBrightLevel(Long brightLevel) {
        this.brightLevel = brightLevel;
    }
}
