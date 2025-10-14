package service;

import dao.EntryLogDAO;
import dao.MemberPassDAO;
import dao.RatePlanDAO;
import dao.SeatDAO;
import dto.EntryLogDTO;
import dto.ExtraFeeDTO;
import dto.MemberPassDTO;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EntryService {

    private EntryLogDAO entryLogDAO;
    private MemberPassDAO passDAO;
    private SeatDAO seatDAO;

    public EntryService() {
        this.entryLogDAO = new EntryLogDAO();
        this.passDAO = new MemberPassDAO();
        this.seatDAO = new SeatDAO();
    }

    // 입실 - 최초 입실
    public boolean enterEntry(int passId, int seatId) throws SQLException {
        EntryLogDTO e = new EntryLogDTO();

        //입실 시간 설정
        LocalDateTime startTime = LocalDateTime.now();
        // 퇴실 예정 시간 설정(자동 퇴실)
        MemberPassDTO pass = passDAO.findByPassId(passId);
        LocalDateTime exEndTime = null;
        if (pass.getRemainingTime() != null) {
            exEndTime = startTime.plusMinutes(pass.getRemainingTime());
        } else { // 기간권인 경우
            exEndTime = pass.getExpireDate().plusDays(1).atStartOfDay();
        }

        e.setSeatId(seatId);
        e.setPassId(passId);
        e.setEntryStatus("IN");
        e.setStartTime(startTime);
        e.setExpectedEndTime(exEndTime);

        return entryLogDAO.insertEntryLog(e) == 1;
    }

    // 복귀 입실 - 외출 후 복귀. 이미 선택된 좌석과 log가 있음.(시간 차감은 계속)
    public boolean returnEntry(int memberId) throws SQLException {

        int logId = entryLogDAO.findActiveLogId(memberId);
        return entryLogDAO.updateEntryStatus(logId, "IN") == 1;
    }

    // 퇴실
    public ExtraFeeDTO exitEntry(int memberId) throws SQLException {

        EntryLogDTO entry = entryLogDAO.findActiveEntry(memberId);
        if (entry == null) return null;

        // 1. 퇴실시간 찍기
        LocalDateTime endTime = LocalDateTime.now();
        System.out.println(endTime);
        // 2. 퇴실 시간에서 시작 시간을 뺀다 -> 잔여시간(분) 도출
        Duration duration = Duration.between(entry.getStartTime(), endTime);
        int minutes = (int)(duration.toMinutes());
        int extraFee = 0;
        // 2-1. 퇴실 예정 시간보다 퇴실 시간이 큰 경우, 초과시간(분)을 도출.
        if(endTime.isAfter(entry.getExpectedEndTime())) {
            duration = Duration.between(entry.getExpectedEndTime(), endTime);
            double hours = (double) duration.toMinutes() / 60.0;

            if (hours > 0) {
                int hour = (int) Math.ceil(hours);
                System.out.println(hour);
                // 3. 초과시간(분) * 시간당 2000원
                extraFee = hour * 2000;
                System.out.println(extraFee);
            }
        }

        // 4. entryLog update & 좌석 상태 available & 이용권 selected false & 이용권 남은시간 차감
        entryLogDAO.updateExitEntryLog(entry.getLogId(), endTime, extraFee);
        seatDAO.updateBySeatId(entry.getSeatId(), "AVAILABLE");
        passDAO.updateSelectedAndRemainingTime(entry.getPassId(), 0, minutes);

        if(passDAO.findByPassId(entry.getPassId()).getRemainingTime() <= 0){
            passDAO.updateIsActive(entry.getPassId(), false);
        }

        return new ExtraFeeDTO(extraFee, entry.getStartTime(), endTime);
    }

    // 입실 중인 상태 조회
    public boolean isEntered(int passId) throws SQLException {
        String entryStatus = entryLogDAO.findEntryStatusByPassId(passId);

        if (!(entryStatus == null) && (entryStatus.equals("IN") || entryStatus.equals("BREAK")))
            return true;
        else
            return false;
    }

    // 입실 중인 것 찾기 - memberID로.
    public boolean isEnteredByMemberId(int memberId) throws SQLException {
        int logId = entryLogDAO.findActiveLogId(memberId);
        return logId > 0;
    }
}
