package com.group2.hcmus.exammanagementsystem.BUS;

import com.group2.hcmus.exammanagementsystem.DAO.FreeRegistrationDAO;
import com.group2.hcmus.exammanagementsystem.DatabaseConnection;
import com.group2.hcmus.exammanagementsystem.DTO.FreeRegistrationDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class FreeRegistrationBUS {
    private final FreeRegistrationDAO registrationDAO = new FreeRegistrationDAO();

    public boolean registerCustomer(FreeRegistrationDTO dto) throws SQLException {
        if (dto == null) throw new IllegalArgumentException("Dữ liệu đăng ký không được để trống");

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Begin transaction

            // Insert into KhachHang
            int maKhachHang = registrationDAO.insertKhachHang(conn, dto);
            if (maKhachHang == -1) throw new SQLException("Không thể thêm khách hàng");

            // Insert into PhieuDangKy
            boolean successPhieu = registrationDAO.insertPhieuDangKy(conn, maKhachHang);
            if (!successPhieu) throw new SQLException("Không thể thêm phiếu đăng ký");

            // TODO: Add logic to update LichThi if needed
            // boolean updated = registrationDAO.updateLichThi(conn, someParams);
            // if (!updated) throw new SQLException("Cập nhật lịch thi thất bại");

            conn.commit(); // All good, commit
            return true;

        } catch (Exception e) {
            if (conn != null) conn.rollback(); // Rollback on any error
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) conn.setAutoCommit(true); // Restore default
        }
    }
}
