package com.group2.hcmus.exammanagementsystem.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class NewExamScheduleDTO {
    private int maLichThi;
    private int maPhongThi;
    private int maGiamThi;
    private LocalDate ngayThi;
    private LocalTime gioThi;
    private String diaDiemThi;
    private int soLuongToiDa;
    private int soLuongDangKy;

    public NewExamScheduleDTO(int maLichThi, int maPhongThi, int maGiamThi,
                           LocalDate ngayThi, LocalTime gioThi,
                           String diaDiemThi, int soLuongToiDa, int soLuongDangKy) {
        this.maLichThi = maLichThi;
        this.maPhongThi = maPhongThi;
        this.maGiamThi = maGiamThi;
        this.ngayThi = ngayThi;
        this.gioThi = gioThi;
        this.diaDiemThi = diaDiemThi;
        this.soLuongToiDa = soLuongToiDa;
        this.soLuongDangKy = soLuongDangKy;
    }

    // Getters
    public int getMaLichThi() { return maLichThi; }
    public int getMaPhongThi() { return maPhongThi; }
    public int getMaGiamThi() { return maGiamThi; }
    public LocalDate getNgayThi() { return ngayThi; }
    public LocalTime getGioThi() { return gioThi; }
    public String getDiaDiemThi() { return diaDiemThi; }
    public int getSoLuongToiDa() { return soLuongToiDa; }
    public int getSoChoTrong() { return soLuongToiDa - soLuongDangKy; }
}
