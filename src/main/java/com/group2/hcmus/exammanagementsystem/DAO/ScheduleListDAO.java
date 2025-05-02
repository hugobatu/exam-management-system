package com.group2.hcmus.exammanagementsystem.DAO;

import com.group2.hcmus.exammanagementsystem.DTO.ScheduleListDTO;
import com.group2.hcmus.exammanagementsystem.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleListDAO {

    public List<ScheduleListDTO> getAllSchedules() {
        List<ScheduleListDTO> list = new ArrayList<>();

        String sql = "SELECT *" +
                "FROM exam_management_schema.LichThi";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ScheduleListDTO dto = new ScheduleListDTO();
                dto.setMaLichThi(rs.getInt("ma_lich_thi"));
                dto.setMaPhongThi(rs.getInt("ma_phong_thi"));
                dto.setMaGiamThi(rs.getInt("ma_giam_thi"));
                dto.setMaChungChi(rs.getInt("ma_chung_chi"));
                dto.setNgayGio(rs.getTimestamp("ngay_gio_thi"));
                dto.setDiaDiemThi(rs.getString("dia_diem_thi"));
                dto.setSlDangKy(rs.getInt("so_luong_dang_ky_con_lai"));

                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<ScheduleListDTO> getFilteredSchedules(boolean isNgoaiNguSelected, boolean isTinHocSelected) {
        List<ScheduleListDTO> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT lt.*, cc.loai_chung_chi " +
                        "FROM exam_management_schema.LichThi lt " +
                        "JOIN exam_management_schema.ChungChi cc ON lt.ma_chung_chi = cc.ma_chung_chi " +
                        "WHERE 1=1"
        );

        List<String> filters = new ArrayList<>();
        if (isNgoaiNguSelected) {
            filters.add("cc.loai_chung_chi = 'Ngoại ngữ'");
        }
        if (isTinHocSelected) {
            filters.add("cc.loai_chung_chi = 'Tin học'");
        }

        if (!filters.isEmpty()) {
            sql.append(" AND (").append(String.join(" OR ", filters)).append(")");
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString());
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ScheduleListDTO dto = new ScheduleListDTO();
                dto.setMaLichThi(rs.getInt("ma_lich_thi"));
                dto.setMaPhongThi(rs.getInt("ma_phong_thi"));
                dto.setMaGiamThi(rs.getInt("ma_giam_thi"));
                dto.setMaChungChi(rs.getInt("ma_chung_chi"));
                dto.setNgayGio(rs.getTimestamp("ngay_gio_thi"));
                dto.setDiaDiemThi(rs.getString("dia_diem_thi"));
                dto.setSlDangKy(rs.getInt("so_luong_dang_ky_con_lai"));

                // Optional: If you want to show the type in DTO too
                // dto.setLoaiChungChi(rs.getString("loai_chung_chi"));

                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
