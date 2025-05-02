package com.group2.hcmus.exammanagementsystem.DTO;

import java.time.LocalDate;

public class FreeRegistrationDTO {
    private String hoTenNDK;
    private String sdtNDK;
    private String emailNDK;
    private String diaChiNDK;

    private String hotenTS;
    private LocalDate ngaySinh;
    private String sdtTS;
    private String emailTS;
    private String diaChiTS;

    private String loaiCC;

    // From TableView (exam schedule selection)
    private ScheduleListDTO schedule;

    public ScheduleListDTO getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleListDTO schedule) {
        this.schedule = schedule;
    }

    // === Getters and Setters ===

    public String getHoTenNDK() {
        return hoTenNDK;
    }

    public void setHoTenNDK(String hoTenNDK) {
        this.hoTenNDK = hoTenNDK;
    }

    public String getSdtNDK() {
        return sdtNDK;
    }

    public void setSdtNDK(String sdtNDK) {
        this.sdtNDK = sdtNDK;
    }

    public String getEmailNDK() {
        return emailNDK;
    }

    public void setEmailNDK(String emailNDK) {
        this.emailNDK = emailNDK;
    }

    public String getDiaChiNDK() {
        return diaChiNDK;
    }

    public void setDiaChiNDK(String diaChiNDK) {
        this.diaChiNDK = diaChiNDK;
    }

    public String getHotenTS() {
        return hotenTS;
    }

    public void setHotenTS(String hotenTS) {
        this.hotenTS = hotenTS;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSdtTS() {
        return sdtTS;
    }

    public void setSdtTS(String sdtTS) {
        this.sdtTS = sdtTS;
    }

    public String getEmailTS() {
        return emailTS;
    }

    public void setEmailTS(String emailTS) {
        this.emailTS = emailTS;
    }

    public String getDiaChiTS() {
        return diaChiTS;
    }

    public void setDiaChiTS(String diaChiTS) {
        this.diaChiTS = diaChiTS;
    }

    public String getLoaiCC() {
        return loaiCC;
    }

    public void setLoaiCC(String loaiCC) {
        this.loaiCC = loaiCC;
    }

}
