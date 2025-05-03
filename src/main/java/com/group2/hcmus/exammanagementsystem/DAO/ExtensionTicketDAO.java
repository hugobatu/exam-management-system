package com.group2.hcmus.exammanagementsystem.DAO;

import com.group2.hcmus.exammanagementsystem.DTO.ExtensionTicketDTO;
import com.group2.hcmus.exammanagementsystem.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;

public class ExtensionTicketDAO {
    private Connection connection;

    public ExtensionTicketDAO() {
        try {
            // Replace with your actual connection initialization
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ExtensionTicketDAO(Connection connection) {
        this.connection = connection;
    }

    /* Create a new extension ticket */
    public boolean saveExtensionTicket(ExtensionTicketDTO extensionTicket) {
        String sql = "INSERT INTO exam_management_schema.GiaHan " +
                "(ma_phieu_du_thi, ngay_gia_han, loai_gia_han, li_do_gia_han, trang_thai_gia_han, " +
                "phi_gia_han, ghi_chu) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, extensionTicket.getMaPhieuDuThi());
            ps.setDate(2, Date.valueOf(extensionTicket.getNgayGiaHan()));
            ps.setString(3, extensionTicket.getLoaiGiaHan());
            ps.setString(4, extensionTicket.getLyDoGiaHan());
            ps.setString(5, extensionTicket.getTrangThaiGiaHan());
            ps.setDouble(6, extensionTicket.getPhiGiaHan());
            ps.setString(7, extensionTicket.getGhiChu());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    extensionTicket.setMaGiaHan(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /* Check how many extensions an exam card has */
    public int getExtensionCount(int maPhieuDuThi) {
        String sql = "SELECT COUNT(*) FROM exam_management_schema.GiaHan WHERE ma_phieu_du_thi = ? AND trang_thai_gia_han = 'Chấp nhận'";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, maPhieuDuThi);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /* Calculate extension fee based on type and reason */
    public double calculateExtensionFee(String extensionType, String reason) {
        // Default fee
        double fee = 100000; // 100,000 VND

        // Apply different fees based on type and reason
        if (extensionType != null && extensionType.equals("Trường hợp đặc biệt")) {
            fee = 50000; // 50,000 VND for special cases
        }

        if (reason != null) {
            switch (reason) {
                case "Bệnh tật":
                    fee *= 0.8; // 20% discount for sickness
                    break;
                case "Tai nạn":
                    fee *= 0.7; // 30% discount for accidents
                    break;
                case "Tang sự":
                    fee *= 0.5; // 50% discount for bereavement
                    break;
                default:
                    break;
            }
        }

        return fee;
    }
}