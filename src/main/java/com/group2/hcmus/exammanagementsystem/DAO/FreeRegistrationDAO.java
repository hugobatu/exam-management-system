package com.group2.hcmus.exammanagementsystem.DAO;

import com.group2.hcmus.exammanagementsystem.DTO.FreeRegistrationDTO;
import com.group2.hcmus.exammanagementsystem.DatabaseConnection;
import com.group2.hcmus.exammanagementsystem.utils.SupportingUtilities;
import javafx.scene.chart.PieChart;

import java.sql.*;

import java.time.LocalDate;

public class FreeRegistrationDAO {
    public int insertKhachHang(FreeRegistrationDTO dto) {
        String sql = "INSERT INTO exam_management_schema.KhachHang " +
                "(loai_khach_hang, ho_ten, so_dien_thoai, email, dia_chi) " +
                "VALUES ('Tự do', ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean insertPhieuDangKy(int maKhachHang) {
        String sql = "INSERT INTO exam_management_schema.PhieuDangKy " +
                "(ma_khach_hang, ngay_dang_ky, trang_thai_thanh_toan) " +
                "VALUES (?, ?, 'Chua thanh toan')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maKhachHang);
            stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean insertThiSinh(int maKhachHang, FreeRegistrationDTO dto) {
        String sql = "INSERT INTO exam_management_schema.ThiSinh " +
                "(ma_khach_hang, ho_ten, ngay_sinh, so_dien_thoai, email, dia_chi) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maKhachHang);
            stmt.setString(2, dto.getHotenTS());
            stmt.setDate(3, java.sql.Date.valueOf(dto.getNgaySinh()));
            stmt.setString(4, dto.getSdtTS());
            stmt.setString(5, dto.getEmailTS());
            stmt.setString(6, dto.getDiaChiTS());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateLichThi(int maLichThi, int soLuong) {
        String sql = "UPDATE exam_management_schema.LichThi " +
                "SET so_luong_dang_ky_con_lai = so_luong_dang_ky_con_lai - ? " +
                "WHERE ma_lich_thi = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, soLuong);
            stmt.setInt(2, maLichThi);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}