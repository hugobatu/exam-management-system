package com.group2.hcmus.exammanagementsystem.DAO;

import com.group2.hcmus.exammanagementsystem.DTO.BangTinhDTO;
import com.group2.hcmus.exammanagementsystem.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BangTinhDAO {
    
    // Insert a new BangTinh record
    public boolean insert(BangTinhDTO bangTinh) {
        String query = "INSERT INTO exam_management_schema.BangTinh (ma_chung_chi_cap, ma_phieu_du_thi, diem, ngay_cap, ngay_het_han, don_vi_cap, trang_thai_nhan) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, bangTinh.getMaChungChiCap());
            stmt.setInt(2, bangTinh.getMaPhieuDuThi());
            stmt.setBigDecimal(3, bangTinh.getDiem());
            stmt.setDate(4, Date.valueOf(bangTinh.getNgayCap()));
            stmt.setDate(5, Date.valueOf(bangTinh.getNgayHetHan()));
            stmt.setString(6, bangTinh.getDonViCap());
            stmt.setString(7, bangTinh.getTrangThaiNhan());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Get all BangTinh records
    public List<BangTinhDTO> getAll() {
        List<BangTinhDTO> bangTinhList = new ArrayList<>();
        String query = "SELECT * FROM exam_management_schema.BangTinh";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                BangTinhDTO bangTinh = new BangTinhDTO(
                    rs.getInt("ma_chung_chi_cap"),
                    rs.getInt("ma_phieu_du_thi"),
                    rs.getBigDecimal("diem"),
                    rs.getDate("ngay_cap").toLocalDate(),
                    rs.getDate("ngay_het_han").toLocalDate(),
                    rs.getString("don_vi_cap"),
                    rs.getString("trang_thai_nhan")
                );
                bangTinhList.add(bangTinh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return bangTinhList;
    }
    
    // Search BangTinh records by various criteria
    public List<BangTinhDTO> search(String searchTerm) {
        List<BangTinhDTO> result = new ArrayList<>();
        String query = "SELECT * FROM exam_management_schema.BangTinh WHERE " +
                      "CAST(ma_chung_chi_cap AS TEXT) LIKE ? OR " +
                      "CAST(ma_phieu_du_thi AS TEXT) LIKE ? OR " +
                      "CAST(diem AS TEXT) LIKE ? OR " +
                      "don_vi_cap LIKE ? OR " +
                      "trang_thai_nhan LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            String searchPattern = "%" + searchTerm + "%";
            for (int i = 1; i <= 5; i++) {
                stmt.setString(i, searchPattern);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BangTinhDTO bangTinh = new BangTinhDTO(
                        rs.getInt("ma_chung_chi_cap"),
                        rs.getInt("ma_phieu_du_thi"),
                        rs.getBigDecimal("diem"),
                        rs.getDate("ngay_cap").toLocalDate(),
                        rs.getDate("ngay_het_han").toLocalDate(),
                        rs.getString("don_vi_cap"),
                        rs.getString("trang_thai_nhan")
                    );
                    result.add(bangTinh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    // Check if a PhieuDuThi ID exists in the PhieuDuThi table
    public boolean phieuDuThiExists(int maPhieuDuThi) {
        String query = "SELECT 1 FROM exam_management_schema.PhieuDuThi WHERE ma_phieu_du_thi = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, maPhieuDuThi);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Check if a BangTinh record with the given certificate ID already exists
    public boolean certificateExists(int maChungChiCap) {
        String query = "SELECT 1 FROM BangTinh WHERE ma_chung_chi_cap = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, maChungChiCap);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}