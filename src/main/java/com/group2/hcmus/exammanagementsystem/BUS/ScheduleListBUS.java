package com.group2.hcmus.exammanagementsystem.BUS;

import com.group2.hcmus.exammanagementsystem.DAO.ScheduleListDAO;
import com.group2.hcmus.exammanagementsystem.DTO.ScheduleListDTO;

import java.util.List;

public class ScheduleListBUS {
    private ScheduleListDAO scheduleListDAO;

    // constructor
    public ScheduleListBUS() {
        scheduleListDAO = new ScheduleListDAO();
    }


    public List<ScheduleListDTO> getAllSchedules() {
        return scheduleListDAO.getAllSchedules();
    }

    public List<ScheduleListDTO> getFilteredSchedules(boolean isNgoaiNguSelected, boolean isTinHocSelected) {
        return scheduleListDAO.getFilteredSchedules(isNgoaiNguSelected, isTinHocSelected);
    }

}