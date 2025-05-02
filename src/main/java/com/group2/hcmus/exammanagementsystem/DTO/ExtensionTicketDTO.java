package com.group2.hcmus.exammanagementsystem.DTO;

import java.time.LocalDate;

public class ExtensionTicketDTO {
    private int maGiaHan;
    private int maPhieuDuThi;
    private LocalDate ngayGiaHan;
    private String loaiGiaHan;
    private String lyDoGiaHan; // Changed from liDoGiaHan to match controller
    private String trangThaiGiaHan;
    private double phiGiaHan;
    private String ghiChu;

    public ExtensionTicketDTO() {
        this.trangThaiGiaHan = "Chấp nhận"; // Default value
    }

    public ExtensionTicketDTO(int maGiaHan, int maPhieuDuThi, LocalDate ngayGiaHan, String loaiGiaHan,
                              String lyDoGiaHan, double phiGiaHan, String ghiChu) {
        this.maGiaHan = maGiaHan;
        this.maPhieuDuThi = maPhieuDuThi;
        this.ngayGiaHan = ngayGiaHan;
        this.loaiGiaHan = loaiGiaHan;
        this.lyDoGiaHan = lyDoGiaHan;
        this.phiGiaHan = phiGiaHan;
        this.ghiChu = ghiChu;
        this.trangThaiGiaHan = "Chấp nhận"; // Default
    }

    // Getters and Setters
    public int getMaGiaHan() {
        return maGiaHan;
    }

    public void setMaGiaHan(int maGiaHan) {
        this.maGiaHan = maGiaHan;
    }

    public int getMaPhieuDuThi() {
        return maPhieuDuThi;
    }

    public void setMaPhieuDuThi(int maPhieuDuThi) {
        this.maPhieuDuThi = maPhieuDuThi;
    }

    public LocalDate getNgayGiaHan() {
        return ngayGiaHan;
    }

    public void setNgayGiaHan(LocalDate ngayGiaHan) {
        this.ngayGiaHan = ngayGiaHan;
    }

    public String getLoaiGiaHan() {
        return loaiGiaHan;
    }

    public void setLoaiGiaHan(String loaiGiaHan) {
        this.loaiGiaHan = loaiGiaHan;
    }

    public String getLyDoGiaHan() {
        return lyDoGiaHan;
    }

    public void setLyDoGiaHan(String lyDoGiaHan) {
        this.lyDoGiaHan = lyDoGiaHan;
    }

    public String getTrangThaiGiaHan() {
        return trangThaiGiaHan;
    }

    public void setTrangThaiGiaHan(String trangThaiGiaHan) {
        this.trangThaiGiaHan = trangThaiGiaHan;
    }

    public double getPhiGiaHan() {
        return phiGiaHan;
    }

    public void setPhiGiaHan(double phiGiaHan) {
        this.phiGiaHan = phiGiaHan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}