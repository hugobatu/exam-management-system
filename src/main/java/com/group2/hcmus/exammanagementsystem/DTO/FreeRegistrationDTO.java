package com.group2.hcmus.exammanagementsystem.DTO;

public class FreeRegistrationDTO {
    private String hoTenNDK;
    private String sdtNDK;
    private String emailNDK;
    private String diaChiNDK;

    private String hotenTS;
    private String ngaySinh;
    private String sdtTS;
    private String emailTS;
    private String diaChiTS;

    private String loaiCC;

    // From TableView (exam schedule selection)
    private String ngayThi;
    private String gioThi;
    private String diaDiemThi;
    private String phongThi;
    private int slDangKy;
    private int slToiDa;
    private boolean luaChon; // whether this row is selected

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

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
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

    public String getNgayThi() {
        return ngayThi;
    }

    public void setNgayThi(String ngayThi) {
        this.ngayThi = ngayThi;
    }

    public String getGioThi() {
        return gioThi;
    }

    public void setGioThi(String gioThi) {
        this.gioThi = gioThi;
    }

    public String getDiaDiemThi() {
        return diaDiemThi;
    }

    public void setDiaDiemThi(String diaDiemThi) {
        this.diaDiemThi = diaDiemThi;
    }

    public String getPhongThi() {
        return phongThi;
    }

    public void setPhongThi(String phongThi) {
        this.phongThi = phongThi;
    }

    public int getSlDangKy() {
        return slDangKy;
    }

    public void setSlDangKy(int slDangKy) {
        this.slDangKy = slDangKy;
    }

    public int getSlToiDa() {
        return slToiDa;
    }

    public void setSlToiDa(int slToiDa) {
        this.slToiDa = slToiDa;
    }

    public boolean isLuaChon() {
        return luaChon;
    }

    public void setLuaChon(boolean luaChon) {
        this.luaChon = luaChon;
    }
}
