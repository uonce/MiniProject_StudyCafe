package service;

import dao.MemberDAO;
import dao.SeatDAO;
import dto.SeatDTO;

import java.sql.SQLException;
import java.util.List;

public class SeatService {
    private SeatDAO dao;

    public SeatService() {
        this.dao = new SeatDAO();
    }

    // 좌석 상태 변화
    public boolean updateSeatStatus(int seatId, String status) throws  SQLException {
        int result = dao.updateBySeatId(seatId,status);
        return result == 1;
    }

    // 좌석 전체 조회
    public List<SeatDTO> getSeats() throws SQLException {
        return dao.selectAll();
    }
}
