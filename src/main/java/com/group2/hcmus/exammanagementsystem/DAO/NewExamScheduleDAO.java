package com.group2.hcmus.exammanagementsystem.DAO;

import com.group2.hcmus.exammanagementsystem.DTO.NewExamScheduleDTO;
import com.group2.hcmus.exammanagementsystem.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class NewExamScheduleDAO {
    public List<NewExamScheduleDTO> getAvailableSchedules() {
        List<NewExamScheduleDTO> schedules = new ArrayList<>();

        String query = """
            SELECT lt.ma_lich_thi, lt.ma_phong_thi, lt.ma_chung_chi, DATE(lt.ngay_gio_thi) AS ngay_thi, CAST(lt.ngay_gio_thi AS TIME) AS gio_thi, lt.dia_diem_thi, lt.so_luong_dang_ky_con_lai
            FROM exam_management_schema.LichThi lt
            WHERE so_luong_dang_ky_con_lai > 0
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                schedules.add(new NewExamScheduleDTO(
                        rs.getInt("ma_lich_thi"),
                        rs.getInt("ma_phong_thi"),
                        rs.getInt("ma_chung_chi"),
                        rs.getDate("ngay_thi").toLocalDate(),
                        rs.getTime("gio_thi").toLocalTime(),
                        rs.getString("dia_diem_thi"),
                        rs.getInt("so_luong_dang_ky_con_lai")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schedules;
    }

    public boolean assignScheduleToExamCard(int maPhieuDuThi, int maLichThi) {
        String update = """
            UPDATE exam_management_schema.ChiTietDangKy
            SET ma_lich_thi = ?
            WHERE ma_phieu_dang_ky = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(update)) {
            stmt.setInt(1, maLichThi);
            stmt.setInt(2, maPhieuDuThi);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<NewExamScheduleDTO> searchSchedulesByDateRange(LocalDate from, LocalDate to) {
        List<NewExamScheduleDTO> schedules = new ArrayList<>();

        String query = "SELECT lt.ma_lich_thi, lt.ma_phong_thi, lt.ma_chung_chi, DATE(lt.ngay_gio_thi) AS ngay_thi, CAST(lt.ngay_gio_thi AS TIME) AS gio_thi, lt.dia_diem_thi, lt.so_luong_dang_ky_con_lai " +
                "FROM exam_management_schema.LichThi lt " +
                "WHERE DATE(lt.ngay_gio_thi) BETWEEN ? AND ? " +
                "AND so_luong_dang_ky_con_lai > 0";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(from));
            stmt.setDate(2, Date.valueOf(to));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                schedules.add(new NewExamScheduleDTO(
                        rs.getInt("ma_lich_thi"),
                        rs.getInt("ma_phong_thi"),
                        rs.getInt("ma_chung_chi"),
                        rs.getDate("ngay_thi").toLocalDate(),
                        rs.getTime("gio_thi").toLocalTime(),
                        rs.getString("dia_diem_thi"),
                        rs.getInt("so_luong_dang_ky_con_lai")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schedules;
    }

}
