package com.group2.hcmus.exammanagementsystem.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class NewExamScheduleDTO {
    private int maLichThi;
    private int maPhongThi;
    private int maChungChi;
    private LocalDate ngayThi;
    private LocalTime gioThi;
    private String diaDiemThi;
    private int soLuongConLai;

    public NewExamScheduleDTO(int maLichThi, int maPhongThi, int maChungChi,
                           LocalDate ngayThi, LocalTime gioThi,
                           String diaDiemThi, int soLuongConLai) {
        this.maLichThi = maLichThi;
        this.maPhongThi = maPhongThi;
        this.maChungChi = maChungChi;
        this.ngayThi = ngayThi;
        this.gioThi = gioThi;
        this.diaDiemThi = diaDiemThi;
        this.soLuongConLai = soLuongConLai;
    }

    // Getters
    public int getMaLichThi() { return maLichThi; }
    public int getMaPhongThi() { return maPhongThi; }
    public int getMaChungChi() { return maChungChi; }
    public LocalDate getNgayThi() { return ngayThi; }
    public LocalTime getGioThi() { return gioThi; }
    public String getDiaDiemThi() { return diaDiemThi; }
    public int getSoLuongConLai() { return soLuongConLai; }
}
