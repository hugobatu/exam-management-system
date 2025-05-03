package com.group2.hcmus.exammanagementsystem.BUS;

import com.group2.hcmus.exammanagementsystem.DTO.ExamCardDTO;
import com.group2.hcmus.exammanagementsystem.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamCardBUS {
    // Search exam cards by ID
    public List<ExamCardDTO> searchByExamCardId(int maPhieuDuThi) {
        List<ExamCardDTO> result = new ArrayList<>();
        String query = """
            SELECT pdt.ma_phieu_du_thi, pdt.so_bao_danh, ts.ho_ten, ts.ngay_sinh,
                DATE(lt.ngay_gio_thi) AS ngay_thi, CAST(lt.ngay_gio_thi AS TIME) AS gio_thi, lt.dia_diem_thi
            FROM exam_management_schema.PhieuDuThi pdt JOIN exam_management_schema.ChiTietDangKy ctdk ON pdt.ma_chi_tiet = ctdk.ma_chi_tiet
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
    public List<ExamCardDTO> searchByRegistrationNumber(int soBaoDanh) {
        List<ExamCardDTO> result = new ArrayList<>();
        String query = """
            SELECT pdt.ma_phieu_du_thi, pdt.so_bao_danh, ts.ho_ten, ts.ngay_sinh,
                DATE(lt.ngay_gio_thi) AS ngay_thi, CAST(lt.ngay_gio_thi AS TIME) AS gio_thi, lt.dia_diem_thi
            FROM exam_management_schema.PhieuDuThi pdt JOIN exam_management_schema.ChiTietDangKy ctdk ON pdt.ma_chi_tiet = ctdk.ma_chi_tiet
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
    public List<ExamCardDTO> searchByName(String name) {
        List<ExamCardDTO> result = new ArrayList<>();
        String query = """
            SELECT pdt.ma_phieu_du_thi, pdt.so_bao_danh, ts.ho_ten, ts.ngay_sinh,
                DATE(lt.ngay_gio_thi) AS ngay_thi, CAST(lt.ngay_gio_thi AS TIME) AS gio_thi, lt.dia_diem_thi
            FROM exam_management_schema.PhieuDuThi pdt JOIN exam_management_schema.ChiTietDangKy ctdk ON pdt.ma_chi_tiet = ctdk.ma_chi_tiet
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
    public List<ExamCardDTO> getAllExamCards() {
        List<ExamCardDTO> result = new ArrayList<>();
        String query = """
            SELECT pdt.ma_phieu_du_thi, pdt.so_bao_danh, ts.ho_ten, ts.ngay_sinh,
                DATE(lt.ngay_gio_thi) AS ngay_thi, CAST(lt.ngay_gio_thi AS TIME) AS gio_thi, lt.dia_diem_thi
            FROM exam_management_schema.PhieuDuThi pdt JOIN exam_management_schema.ChiTietDangKy ctdk ON pdt.ma_chi_tiet = ctdk.ma_chi_tiet
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
    private ExamCardDTO createExamCardFromResultSet(ResultSet rs) throws SQLException {
        return new ExamCardDTO(
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
