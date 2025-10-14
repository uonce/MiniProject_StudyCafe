package dto;

import java.time.LocalDate;

public class PassResponseDTO {
    private int passId;
    private String planName;
    private String planType; // ENUM('TIME_PASS', 'PERIOD_PASS')
    private Integer remainingTime; // 분 단위
    private LocalDate expireDate;

    public PassResponseDTO() {}

    public PassResponseDTO(int passId, String planName, String planType, Integer remainingTime, LocalDate expireDate) {
        this.passId = passId;
        this.planName = planName;
        this.planType = planType;
        this.remainingTime = remainingTime;
        this.expireDate = expireDate;
    }

    public int getPassId() {
        return passId;
    }

    public void setPassId(int passId) {
        this.passId = passId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public Integer getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(Integer remainingTime) {
        this.remainingTime = remainingTime;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }
}
