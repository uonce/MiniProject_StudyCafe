package dto;

import java.time.LocalDateTime;

public class ExtraFeeDTO {
    int extraFee;
    LocalDateTime startTime;
    LocalDateTime endTime;

    public ExtraFeeDTO() {}

    public ExtraFeeDTO(int extraFee, LocalDateTime startTime, LocalDateTime endTime) {
        this.extraFee = extraFee;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getExtraFee() {
        return extraFee;
    }

    public void setExtraFee(int extraFee) {
        this.extraFee = extraFee;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
