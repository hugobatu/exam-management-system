package com.group2.hcmus.exammanagementsystem.BUS;

import com.group2.hcmus.exammanagementsystem.DAO.PaymentDAO;
import com.group2.hcmus.exammanagementsystem.DTO.PaymentDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PaymentBUS {
    private PaymentDAO paymentDAO;

    public PaymentBUS() throws SQLException {
        this.paymentDAO = new PaymentDAO();
    }

    public PaymentDTO.RegistrationDTO getRegistrationById(int maPhieuDangKy) throws SQLException {
        PaymentDTO.RegistrationDTO registration = paymentDAO.getRegistrationById(maPhieuDangKy);
        if (registration != null) {
            calculateDiscountAndTotal(registration);
        }
        return registration;
    }

    private void calculateDiscountAndTotal(PaymentDTO.RegistrationDTO registration) throws SQLException {
        int soThiSinh = paymentDAO.countCandidates(registration.getMaPhieuDangKy());
        double tongLePhi = paymentDAO.calculateTotalFee(registration.getMaPhieuDangKy());

        double giamGia = 0;
        if ("Đơn vị".equals(registration.getLoaiKhachHang())) {
            if (soThiSinh >= 20) giamGia = tongLePhi * 0.15;
            else if (soThiSinh > 0) giamGia = tongLePhi * 0.10;
        }

        double thanhTien = tongLePhi - giamGia;
        registration.setGiamGia(giamGia);
        registration.setThanhTien(thanhTien);
    }

    public boolean checkPaymentDeadline(LocalDate ngayDangKy, LocalDate ngayThanhToan) {
        long daysBetween = ChronoUnit.DAYS.between(ngayDangKy, ngayThanhToan);
        return daysBetween <= 3;
    }

    public void cancelRegistration(int maPhieuDangKy) throws SQLException {
        paymentDAO.cancelRegistration(maPhieuDangKy);
    }

    public boolean checkEmployeeExists(int maNhanVien) throws SQLException {
        return paymentDAO.checkEmployeeExists(maNhanVien);
    }

    public boolean checkInvoiceExists(int maPhieuDangKy) throws SQLException {
        return paymentDAO.checkInvoiceExists(maPhieuDangKy);
    }

    public String getInvoiceStatus(int maPhieuDangKy) throws SQLException {
        return paymentDAO.getInvoiceStatus(maPhieuDangKy);
    }

    public int createInvoice(PaymentDTO.InvoiceDTO invoice) throws SQLException {
        int maHoaDon = paymentDAO.createInvoice(invoice);
        paymentDAO.createInvoiceDetails(maHoaDon, invoice.getMaPhieuDangKy());
        paymentDAO.updateRegistrationStatus(invoice.getMaPhieuDangKy(), invoice.getTrangThai());
        return maHoaDon;
    }

    public void confirmPayment(int maPhieuDangKy, String trangThai, LocalDate ngayThanhToan, String hinhThucThanhToan) throws SQLException {
        paymentDAO.updateInvoiceStatus(maPhieuDangKy, trangThai, ngayThanhToan, hinhThucThanhToan);
        paymentDAO.updateRegistrationStatus(maPhieuDangKy, trangThai);
    }
}