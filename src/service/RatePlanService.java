package service;

import dao.MemberPassDAO;
import dao.RatePlanDAO;
import dto.RatePlanDTO;

import java.sql.SQLException;
import java.util.List;

public class RatePlanService {

    private RatePlanDAO ratePlanDAO;

    public RatePlanService() {
        this.ratePlanDAO = new RatePlanDAO();
    }
    // 이용권이 없을 경우 => 전체 요금제 조회
    public List<RatePlanDTO> getRatePassList() throws SQLException {
        return ratePlanDAO.selectAll();
    }
}
