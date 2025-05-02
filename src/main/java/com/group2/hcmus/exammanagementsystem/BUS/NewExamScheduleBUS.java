package com.group2.hcmus.exammanagementsystem.BUS;

import com.group2.hcmus.exammanagementsystem.DAO.NewExamScheduleDAO;
import com.group2.hcmus.exammanagementsystem.DTO.NewExamScheduleDTO;

import java.time.LocalDate;
import java.util.List;

public class NewExamScheduleBUS {
    private NewExamScheduleDAO dao = new NewExamScheduleDAO();

    public List<NewExamScheduleDTO> getAvailableSchedules() {
        return dao.getAvailableSchedules();
    }

    public boolean assignScheduleToExamCard(int maPhieuDuThi, int maLichThi) {
        return dao.assignScheduleToExamCard(maPhieuDuThi, maLichThi);
    }

    public List<NewExamScheduleDTO> searchSchedules(LocalDate from, LocalDate to) {
        return dao.searchSchedulesByDateRange(from, to);
    }
}
