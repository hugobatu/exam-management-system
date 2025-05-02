package com.group2.hcmus.exammanagementsystem.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BangTinhDTO {
    private int maChungChiCap;
    private int maPhieuDuThi;
    private BigDecimal diem;
    private LocalDate ngayCap;
    private LocalDate ngayHetHan;
    private String donViCap;
    private String trangThaiNhan;
    
    public BangTinhDTO() {
        this.trangThaiNhan = "Chưa nhận"; // Default value
    }
    
    public BangTinhDTO(int maChungChiCap, int maPhieuDuThi, BigDecimal diem, 
                      LocalDate ngayCap, LocalDate ngayHetHan, String donViCap, String trangThaiNhan) {
        this.maChungChiCap = maChungChiCap;
        this.maPhieuDuThi = maPhieuDuThi;
        this.diem = diem;
        this.ngayCap = ngayCap;
        this.ngayHetHan = ngayHetHan;
        this.donViCap = donViCap;
        this.trangThaiNhan = trangThaiNhan;
    }
    
    // Getters and setters
    public int getMaChungChiCap() {
        return maChungChiCap;
    }
    
    public void setMaChungChiCap(int maChungChiCap) {
        this.maChungChiCap = maChungChiCap;
    }
    
    public int getMaPhieuDuThi() {
        return maPhieuDuThi;
    }
    
    public void setMaPhieuDuThi(int maPhieuDuThi) {
        this.maPhieuDuThi = maPhieuDuThi;
    }
    
    public BigDecimal getDiem() {
        return diem;
    }
    
    public void setDiem(BigDecimal diem) {
        this.diem = diem;
    }
    
    public LocalDate getNgayCap() {
        return ngayCap;
    }
    
    public void setNgayCap(LocalDate ngayCap) {
        this.ngayCap = ngayCap;
    }
    
    public LocalDate getNgayHetHan() {
        return ngayHetHan;
    }
    
    public void setNgayHetHan(LocalDate ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }
    
    public String getDonViCap() {
        return donViCap;
    }
    
    public void setDonViCap(String donViCap) {
        this.donViCap = donViCap;
    }
    
    public String getTrangThaiNhan() {
        return trangThaiNhan;
    }
    
    public void setTrangThaiNhan(String trangThaiNhan) {
        this.trangThaiNhan = trangThaiNhan;
    }
}