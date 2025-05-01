package com.group2.hcmus.exammanagementsystem.controller.AccountingStaff.utils;

import com.group2.hcmus.exammanagementsystem.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Service {
    // Search exam cards by ID
    public List<ExamCard> searchByExamCardId(int maPhieuDuThi) {
        List<ExamCard> result = new ArrayList<>();
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
                while (rs.next()) {
                    result.add(createExamCardFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching exam cards by ID: " + e.getMessage());
        }

        return result;
    }

    // Search exam cards by exam registration number
    public List<ExamCard> searchByRegistrationNumber(int soBaoDanh) {
        List<ExamCard> result = new ArrayList<>();
        String query = """
            SELECT pdt.ma_phieu_du_thi, pdt.so_bao_danh, ts.ho_ten, ts.ngay_sinh, 
                   lt.ngay_thi, lt.gio_thi, lt.dia_diem_thi
            FROM exam_management_schema.PhieuDuThi pdt
            JOIN exam_management_schema.ChiTietDangKy ctdk ON pdt.ma_phieu_du_thi = ctdk.ma_phieu_du_thi
            JOIN exam_management_schema.ThiSinh ts ON ctdk.ma_thi_sinh = ts.ma_thi_sinh
            JOIN exam_management_schema.LichThi lt ON ctdk.ma_lich_thi = lt.ma_lich_thi
            WHERE pdt.so_bao_danh = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, soBaoDanh);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(createExamCardFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching exam cards by registration number: " + e.getMessage());
        }

        return result;
    }

    // Search exam cards by candidate name
    public List<ExamCard> searchByName(String name) {
        List<ExamCard> result = new ArrayList<>();
        String query = """
            SELECT pdt.ma_phieu_du_thi, pdt.so_bao_danh, ts.ho_ten, ts.ngay_sinh, 
                   lt.ngay_thi, lt.gio_thi, lt.dia_diem_thi
            FROM exam_management_schema.PhieuDuThi pdt
            JOIN exam_management_schema.ChiTietDangKy ctdk ON pdt.ma_phieu_du_thi = ctdk.ma_phieu_du_thi
            JOIN exam_management_schema.ThiSinh ts ON ctdk.ma_thi_sinh = ts.ma_thi_sinh
            JOIN exam_management_schema.LichThi lt ON ctdk.ma_lich_thi = lt.ma_lich_thi
            WHERE ts.ho_ten ILIKE ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(createExamCardFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching exam cards by name: " + e.getMessage());
        }

        return result;
    }

    // Get all exam cards
    public List<ExamCard> getAllExamCards() {
        List<ExamCard> result = new ArrayList<>();
        String query = """
            SELECT pdt.ma_phieu_du_thi, pdt.so_bao_danh, ts.ho_ten, ts.ngay_sinh, 
                   lt.ngay_thi, lt.gio_thi, lt.dia_diem_thi
            FROM exam_management_schema.PhieuDuThi pdt
            JOIN exam_management_schema.ChiTietDangKy ctdk ON pdt.ma_phieu_du_thi = ctdk.ma_phieu_du_thi
            JOIN exam_management_schema.ThiSinh ts ON ctdk.ma_thi_sinh = ts.ma_thi_sinh
            JOIN exam_management_schema.LichThi lt ON ctdk.ma_lich_thi = lt.ma_lich_thi
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                result.add(createExamCardFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all exam cards: " + e.getMessage());
        }

        return result;
    }

    // Helper method to create ExamCard from ResultSet
    private ExamCard createExamCardFromResultSet(ResultSet rs) throws SQLException {
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
