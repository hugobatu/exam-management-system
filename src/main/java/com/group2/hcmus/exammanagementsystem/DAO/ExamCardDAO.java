package com.group2.hcmus.exammanagementsystem.DAO;

import com.group2.hcmus.exammanagementsystem.controller.AccountingStaff.utils.ExamCard;
import com.group2.hcmus.exammanagementsystem.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExamCardDAO {

    /* Get exam card by ID */
    public ExamCard getExamCardById(int maPhieuDuThi) {
        String query = """
            SELECT pdt.ma_phieu_du_thi, pdt.so_bao_danh, ts.ho_ten, ts.ngay_sinh, 
                   lt.ngay_thi, lt.gio_thi, lt.dia_diem_thi
            FROM exam_management_schema.PhieuDuThi pdt
            JOIN exam_management_schema.ChiTietDangKy ctdk ON pdt.ma_phieu_du_thi = ctdk.ma_phieu_du_thi
            JOIN exam_management_schema.ThiSinh ts ON ctdk.ma_thi_sinh = ts.ma_thi_sinh
            JOIN exam_management_schema.LichThi lt ON ctdk.ma_lich_thi = lt.ma_lich_thi
            WHERE pdt.ma_phieu_du_thi = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, maPhieuDuThi);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ExamCard(
                            rs.getInt("ma_phieu_du_thi"),
                            rs.getInt("so_bao_danh"),
                            rs.getString("ho_ten"),
                            rs.getDate("ngay_sinh").toLocalDate(),
                            rs.getDate("ngay_thi").toLocalDate(),
                            rs.getTime("gio_thi").toLocalTime(),
                            rs.getString("dia_diem_thi")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving exam card: " + e.getMessage());
        }

        return null;
    }

    /* Create a new exam card */
    public boolean createExamCard(int soBaoDanh, LocalDate ngayPhatHanh, int maPhongThi) {
        String query = "INSERT INTO exam_management_schema.PhieuDuThi (so_bao_danh, ngay_phat_hanh, ma_phong_thi) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, soBaoDanh);
            stmt.setDate(2, Date.valueOf(ngayPhatHanh));
            stmt.setInt(3, maPhongThi);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error creating exam card: " + e.getMessage());
            return false;
        }
    }

    /* Update an existing exam card */
    public boolean updateExamCard(int maPhieuDuThi, int soBaoDanh, int maPhongThi) {
        String query = "UPDATE exam_management_schema.PhieuDuThi SET so_bao_danh = ?, ma_phong_thi = ? WHERE ma_phieu_du_thi = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, soBaoDanh);
            stmt.setInt(2, maPhongThi);
            stmt.setInt(3, maPhieuDuThi);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating exam card: " + e.getMessage());
            return false;
        }
    }

    /* Delete an exam card */
    public boolean deleteExamCard(int maPhieuDuThi) {
        // First check if this exam card is referenced in ChiTietDangKy
        String checkQuery = "SELECT COUNT(*) FROM exam_management_schema.ChiTietDangKy WHERE ma_phieu_du_thi = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setInt(1, maPhieuDuThi);

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // This exam card is referenced, can't delete
                    return false;
                }
            }

            // If not referenced, delete the exam card
            String deleteQuery = "DELETE FROM exam_management_schema.PhieuDuThi WHERE ma_phieu_du_thi = ?";

            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                deleteStmt.setInt(1, maPhieuDuThi);
                int rowsAffected = deleteStmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting exam card: " + e.getMessage());
            return false;
        }
    }

    /* Get next available SoBaoDanh */
    public int getNextSoBaoDanh() {
        String query = "SELECT COALESCE(MAX(so_bao_danh), 1000) + 1 FROM exam_management_schema.PhieuDuThi";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 1001; // Default starting number if no records exist
        } catch (SQLException e) {
            System.err.println("Error getting next SoBaoDanh: " + e.getMessage());
            return 1001; // Default in case of error
        }
    }
}
