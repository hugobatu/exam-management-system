package com.group2.hcmus.exammanagementsystem.BUS;

import com.group2.hcmus.exammanagementsystem.DAO.BangTinhDAO;
import com.group2.hcmus.exammanagementsystem.DTO.BangTinhDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SpreadsheetBUS {
    private final BangTinhDAO bangTinhDAO;
    
    public SpreadsheetBUS() {
        this.bangTinhDAO = new BangTinhDAO();
    }
    
    // Insert new BangTinh with validation
    public String insert(int maChungChiCap, int maPhieuDuThi, BigDecimal diem, 
                        LocalDate ngayCap, LocalDate ngayHetHan, String donViCap) {
        // Validate input
        if (diem == null || diem.compareTo(BigDecimal.ZERO) < 0 || diem.compareTo(new BigDecimal(10)) > 0) {
            return "Điểm phải nằm trong khoảng từ 0 đến 10";
        }
        
        if (ngayCap == null) {
            return "Ngày cấp không được để trống";
        }
        
        if (ngayHetHan == null) {
            return "Ngày hết hạn không được để trống";
        }
        
        if (ngayHetHan.isBefore(ngayCap)) {
            return "Ngày hết hạn phải sau ngày cấp";
        }
        
        if (donViCap == null || donViCap.trim().isEmpty()) {
            return "Đơn vị cấp không được để trống";
        }
        
        // Check if certificate ID already exists
        if (bangTinhDAO.certificateExists(maChungChiCap)) {
            return "Mã chứng chỉ cấp đã tồn tại";
        }
        
        // Check if the PhieuDuThi exists
        if (!bangTinhDAO.phieuDuThiExists(maPhieuDuThi)) {
            return "Mã phiếu dự thi không tồn tại";
        }
        
        // Create new BangTinh object
        BangTinhDTO bangTinh = new BangTinhDTO();
        bangTinh.setMaChungChiCap(maChungChiCap);
        bangTinh.setMaPhieuDuThi(maPhieuDuThi);
        bangTinh.setDiem(diem);
        bangTinh.setNgayCap(ngayCap);
        bangTinh.setNgayHetHan(ngayHetHan);
        bangTinh.setDonViCap(donViCap);
        bangTinh.setTrangThaiNhan("Chưa nhận"); // Default value
        
        // Insert into database
        boolean success = bangTinhDAO.insert(bangTinh);
        if (success) {
            return "Success";
        } else {
            return "Không thể thêm bảng tính. Vui lòng kiểm tra lại thông tin.";
        }
    }
    
    // Get all BangTinh records
    public List<BangTinhDTO> getAllBangTinh() {
        return bangTinhDAO.getAll();
    }
    
    // Search BangTinh records
    public List<BangTinhDTO> searchBangTinh(String searchTerm) {
        return bangTinhDAO.search(searchTerm);
    }
    public boolean updateTrangThaiNhan(int maChungChiCap, String trangThaiNhan) {
        return bangTinhDAO.updateTrangThaiNhan(maChungChiCap, trangThaiNhan);
    }
}