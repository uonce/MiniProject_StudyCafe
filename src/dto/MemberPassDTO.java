package dto;

import java.time.LocalDate;

public class MemberPassDTO {
    private int passId;
    private int memberId;
    private int planId;
    private Integer remainingTime; // 분 단위
    private LocalDate expireDate;
    private boolean isActive;
    private boolean selected;

    public MemberPassDTO() {}

    public MemberPassDTO(int passId, int memberId, int planId, Integer remainingTime, LocalDate expireDate, boolean isActive, boolean selected) {
        this.passId = passId;
        this.memberId = memberId;
        this.planId = planId;
        this.remainingTime = remainingTime;
        this.expireDate = expireDate;
        this.isActive = isActive;
        this.selected = selected;
    }

    public int getPassId() { return passId; }
    public void setPassId(int passId) { this.passId = passId; }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public int getPlanId() { return planId; }
    public void setPlanId(int planId) { this.planId = planId; }

    public Integer getRemainingTime() { return remainingTime; }
    public void setRemainingTime(Integer remainingTime) { this.remainingTime = remainingTime; }

    public LocalDate getExpireDate() { return expireDate; }
    public void setExpireDate(LocalDate expireDate) { this.expireDate = expireDate; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "MemberPassDTO [passId=" + passId + ", memberId=" + memberId +
                ", planId=" + planId + ", remain=" + remainingTime + ", expire=" + expireDate +
                ", active=" + isActive + "]";
    }
}

