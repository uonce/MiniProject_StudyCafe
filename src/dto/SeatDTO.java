package dto;

public class SeatDTO {
    private int seatId;
    private String seatNo;
    private String seatStatus; // ENUM('AVAILABLE', 'OCCUPIED')

    public SeatDTO() {}

    public SeatDTO(int seatId, String seatNo, String seatStatus) {
        this.seatId = seatId;
        this.seatNo = seatNo;
        this.seatStatus = seatStatus;
    }

    public int getSeatId() { return seatId; }
    public void setSeatId(int seatId) { this.seatId = seatId; }

    public String getSeatNo() { return seatNo; }
    public void setSeatNo(String seatNo) { this.seatNo = seatNo; }

    public String getSeatStatus() { return seatStatus; }
    public void setSeatStatus(String seatStatus) { this.seatStatus = seatStatus; }

    @Override
    public String toString() {
        return "SeatDTO [seatId=" + seatId + ", seatNo=" + seatNo + ", status=" + seatStatus + "]";
    }
}
