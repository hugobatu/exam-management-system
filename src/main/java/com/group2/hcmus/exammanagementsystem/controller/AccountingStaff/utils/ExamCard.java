package com.group2.hcmus.exammanagementsystem.controller.AccountingStaff.utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExamCard {
    private int maPhieuDuThi;
    private int soBaoDanh;
    private String hoTen;
    private LocalDate ngaySinh;
    private LocalDate ngayThi;
    private LocalTime gioThi;
    private String diaDiemThi;

    public ExamCard(int maPhieuDuThi, int soBaoDanh, String hoTen, LocalDate ngaySinh,
                    LocalDate ngayThi, LocalTime gioThi, String diaDiemThi) {
        this.maPhieuDuThi = maPhieuDuThi;
        this.soBaoDanh = soBaoDanh;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.ngayThi = ngayThi;
        this.gioThi = gioThi;
        this.diaDiemThi = diaDiemThi;
    }

    // Getters and setters
    public int getMaPhieuDuThi() {
        return maPhieuDuThi;
    }

    public void setMaPhieuDuThi(int maPhieuDuThi) {
        this.maPhieuDuThi = maPhieuDuThi;
    }

    public int getSoBaoDanh() {
        return soBaoDanh;
    }

    public void setSoBaoDanh(int soBaoDanh) {
        this.soBaoDanh = soBaoDanh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public LocalDate getNgayThi() {
        return ngayThi;
    }

    public void setNgayThi(LocalDate ngayThi) {
        this.ngayThi = ngayThi;
    }

    public LocalTime getGioThi() {
        return gioThi;
    }

    public void setGioThi(LocalTime gioThi) {
        this.gioThi = gioThi;
    }

    public String getDiaDiemThi() {
        return diaDiemThi;
    }

    public void setDiaDiemThi(String diaDiemThi) {
        this.diaDiemThi = diaDiemThi;
    }
}
