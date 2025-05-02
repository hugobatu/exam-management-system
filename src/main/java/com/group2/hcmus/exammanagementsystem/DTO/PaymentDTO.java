package com.group2.hcmus.exammanagementsystem.DTO;

import java.time.LocalDate;

public class PaymentDTO {
    public static class RegistrationDTO {
        private int maPhieuDangKy;
        private int maKhachHang;
        private LocalDate ngayDangKy;
        private String trangThaiThanhToan;
        private String loaiKhachHang;
        private double giamGia;
        private double thanhTien;

        public RegistrationDTO(int maPhieuDangKy, int maKhachHang, LocalDate ngayDangKy, String trangThaiThanhToan, String loaiKhachHang) {
            this.maPhieuDangKy = maPhieuDangKy;
            this.maKhachHang = maKhachHang;
            this.ngayDangKy = ngayDangKy;
            this.trangThaiThanhToan = trangThaiThanhToan;
            this.loaiKhachHang = loaiKhachHang;
        }

        public int getMaPhieuDangKy() {
            return maPhieuDangKy;
        }

        public void setMaPhieuDangKy(int maPhieuDangKy) {
            this.maPhieuDangKy = maPhieuDangKy;
        }

        public int getMaKhachHang() {
            return maKhachHang;
        }

        public void setMaKhachHang(int maKhachHang) {
            this.maKhachHang = maKhachHang;
        }

        public LocalDate getNgayDangKy() {
            return ngayDangKy;
        }

        public void setNgayDangKy(LocalDate ngayDangKy) {
            this.ngayDangKy = ngayDangKy;
        }

        public String getTrangThaiThanhToan() {
            return trangThaiThanhToan;
        }

        public void setTrangThaiThanhToan(String trangThaiThanhToan) {
            this.trangThaiThanhToan = trangThaiThanhToan;
        }

        public String getLoaiKhachHang() {
            return loaiKhachHang;
        }

        public void setLoaiKhachHang(String loaiKhachHang) {
            this.loaiKhachHang = loaiKhachHang;
        }

        public double getGiamGia() {
            return giamGia;
        }

        public void setGiamGia(double giamGia) {
            this.giamGia = giamGia;
        }

        public double getThanhTien() {
            return thanhTien;
        }

        public void setThanhTien(double thanhTien) {
            this.thanhTien = thanhTien;
        }
    }

    // DTO for Invoice
    public static class InvoiceDTO {
        private int maHoaDon;
        private int maPhieuDangKy;
        private int maNhanVienLap;
        private LocalDate ngayThanhToan;
        private String hinhThucThanhToan;
        private String trangThai;
        private double tienGiamGia;
        private double tongTien;

        public InvoiceDTO(int maPhieuDangKy, int maNhanVienLap, LocalDate ngayThanhToan, String hinhThucThanhToan, String trangThai, double tienGiamGia, double tongTien) {
            this.maPhieuDangKy = maPhieuDangKy;
            this.maNhanVienLap = maNhanVienLap;
            this.ngayThanhToan = ngayThanhToan;
            this.hinhThucThanhToan = hinhThucThanhToan;
            this.trangThai = trangThai;
            this.tienGiamGia = tienGiamGia;
            this.tongTien = tongTien;
        }

        public int getMaHoaDon() {
            return maHoaDon;
        }

        public void setMaHoaDon(int maHoaDon) {
            this.maHoaDon = maHoaDon;
        }

        public int getMaPhieuDangKy() {
            return maPhieuDangKy;
        }

        public void setMaPhieuDangKy(int maPhieuDangKy) {
            this.maPhieuDangKy = maPhieuDangKy;
        }

        public int getMaNhanVienLap() {
            return maNhanVienLap;
        }

        public void setMaNhanVienLap(int maNhanVienLap) {
            this.maNhanVienLap = maNhanVienLap;
        }

        public LocalDate getNgayThanhToan() {
            return ngayThanhToan;
        }

        public void setNgayThanhToan(LocalDate ngayThanhToan) {
            this.ngayThanhToan = ngayThanhToan;
        }

        public String getHinhThucThanhToan() {
            return hinhThucThanhToan;
        }

        public void setHinhThucThanhToan(String hinhThucThanhToan) {
            this.hinhThucThanhToan = hinhThucThanhToan;
        }

        public String getTrangThai() {
            return trangThai;
        }

        public void setTrangThai(String trangThai) {
            this.trangThai = trangThai;
        }

        public double getTienGiamGia() {
            return tienGiamGia;
        }

        public void setTienGiamGia(double tienGiamGia) {
            this.tienGiamGia = tienGiamGia;
        }

        public double getTongTien() {
            return tongTien;
        }

        public void setTongTien(double tongTien) {
            this.tongTien = tongTien;
        }
    }
}