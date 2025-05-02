package com.group2.hcmus.exammanagementsystem.bus;

import com.group2.hcmus.exammanagementsystem.DAO.FreeRegistrationDAO;
import com.group2.hcmus.exammanagementsystem.DTO.FreeRegistrationDTO;
import com.group2.hcmus.exammanagementsystem.DTO.ScheduleListDTO;


public class FreeRegistrationBUS {
    private final FreeRegistrationDAO freeRegistrationDAO = new FreeRegistrationDAO();

    public boolean registerCustomerAndForm(FreeRegistrationDTO dto, ScheduleListDTO scheduleListDTO) {
        int customerId = freeRegistrationDAO.insertKhachHang(dto);
        if (customerId == -1) {
            System.out.println("Failed to insert customer.");
            return false;
        }
        boolean formInserted = freeRegistrationDAO.insertPhieuDangKy(customerId);
        if (!formInserted) {
            System.out.println("Failed to insert PhieuDangKy.");
            return false;
        }

        boolean insertedThiSinh = freeRegistrationDAO.insertThiSinh(customerId, dto);
        if (!insertedThiSinh) {
            System.out.println("Failed to insert ThiSinh.");
            return false;
        }

        boolean updated = freeRegistrationDAO.updateLichThi(scheduleListDTO.getMaLichThi(), 1);
        if (updated == false) {
            return false;
        }
        return true;
    }

}


