package com.group2.hcmus.exammanagementsystem.DAO;

import com.group2.hcmus.exammanagementsystem.DTO.FreeRegistrationDTO;

import java.sql.*;

import java.time.LocalDate;

public class FreeRegistrationDAO {

    public int insertKhachHang(Connection conn, FreeRegistrationDTO dto) throws SQLException {
        String sql = "INSERT INTO exam_management_schema.KhachHang " +
                "(loai_khach_hang, ho_ten, so_dien_thoai, email, dia_chi) " +
                "VALUES ('TUDO', ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, dto.getHoTenNDK());
            stmt.setString(2, dto.getSdtNDK());
            stmt.setString(3, dto.getEmailNDK());
            stmt.setString(4, dto.getDiaChiNDK());
            int affected = stmt.executeUpdate();

            if (affected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();

        }
        return -1; // Failed
    }

    public boolean insertPhieuDangKy(Connection conn, int maKhachHang) throws SQLException {
        String sql = "INSERT INTO exam_management_schema.PhieuDangKy " +
                "(ma_khach_hang, ngay_dang_ky, trang_thai_hoan_thanh) " +
                "VALUES (?, ?, 'chua thanh toan')";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maKhachHang);
            stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            return stmt.executeUpdate() > 0;
        }
    }

    // Optional: Add updateLichThi if needed
    /*
    public boolean updateLichThi(Connection conn, ...) throws SQLException {
        String sql = "...";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set parameters
            return stmt.executeUpdate() > 0;
        }
    }
    */
}
