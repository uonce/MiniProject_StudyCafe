package dto;

import java.time.LocalDateTime;

public class EntryLogDTO {
    private int logId;
    private int seatId;
    private int passId;
    private String entryStatus; // ENUM('IN', 'OUT')
    private LocalDateTime startTime;
    private LocalDateTime expectedEndTime;
    private LocalDateTime endTime;
    private int extraFee;

    public EntryLogDTO() {}

    public EntryLogDTO(int logId, int seatId, int passId, String entryStatus,
                       LocalDateTime startTime, LocalDateTime expectedEndTime,
                       LocalDateTime endTime, int extraFee) {
        this.logId = logId;
        this.seatId = seatId;
        this.passId = passId;
        this.entryStatus = entryStatus;
        this.startTime = startTime;
        this.expectedEndTime = expectedEndTime;
        this.endTime = endTime;
        this.extraFee = extraFee;
    }

    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }

    public int getSeatId() { return seatId; }
    public void setSeatId(int seatId) { this.seatId = seatId; }

    public int getPassId() { return passId; }
    public void setPassId(int passId) { this.passId = passId; }

    public String getEntryStatus() { return entryStatus; }
    public void setEntryStatus(String entryStatus) { this.entryStatus = entryStatus; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getExpectedEndTime() { return expectedEndTime; }
    public void setExpectedEndTime(LocalDateTime expectedEndTime) { this.expectedEndTime = expectedEndTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public int getExtraFee() { return extraFee; }
    public void setExtraFee(int extraFee) { this.extraFee = extraFee; }

    @Override
    public String toString() {
        return "EntryLogDTO [logId=" + logId + ", seatId=" + seatId + ", passId=" + passId +
                ", entryStatus=" + entryStatus + ", startTime=" + startTime +
                ", expectedEndTime=" + expectedEndTime + ", endTime=" + endTime +
                ", extraFee=" + extraFee + "]";
    }
}
