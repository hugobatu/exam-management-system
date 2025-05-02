package com.group2.hcmus.exammanagementsystem.DAO;

import com.group2.hcmus.exammanagementsystem.DatabaseConnection;
import com.group2.hcmus.exammanagementsystem.DTO.PaymentDTO;

import java.sql.*;
import java.time.LocalDate;

public class PaymentDAO {

    private Connection connection;

    public PaymentDAO() throws SQLException {
        connection = DatabaseConnection.getConnection();
        connection.setSchema("exam_management_schema");
    }

    // Registration-related methods
    public PaymentDTO.RegistrationDTO getRegistrationById(int maPhieuDangKy) throws SQLException {
        String query = "SELECT p.ma_khach_hang, p.ngay_dang_ky, p.trang_thai_thanh_toan, k.loai_khach_hang " +
                "FROM exam_management_schema.PhieuDangKy p " +
                "JOIN exam_management_schema.KhachHang k ON p.ma_khach_hang = k.ma_khach_hang " +
                "WHERE p.ma_phieu_dang_ky = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, maPhieuDangKy);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PaymentDTO.RegistrationDTO(
                        maPhieuDangKy,
                        rs.getInt("ma_khach_hang"),
                        rs.getDate("ngay_dang_ky").toLocalDate(),
                        rs.getString("trang_thai_thanh_toan"),
                        rs.getString("loai_khach_hang")
                );
            }
        }
        return null;
    }

    public void cancelRegistration(int maPhieuDK) throws SQLException {
        connection.setAutoCommit(false);
        try {
            // 1. Xóa dữ liệu trong BangTinh
            String deleteBangTinhQuery = "DELETE FROM exam_management_schema.BangTinh WHERE ma_phieu_du_thi IN (" +
                    "SELECT ma_phieu_du_thi FROM exam_management_schema.PhieuDuThi WHERE ma_chi_tiet IN (" +
                    "SELECT ma_chi_tiet FROM exam_management_schema.ChiTietDangKy WHERE ma_phieu_dang_ky = ?))";
            try (PreparedStatement stmt = connection.prepareStatement(deleteBangTinhQuery)) {
                stmt.setInt(1, maPhieuDK);
                stmt.executeUpdate();
            }

            // 2. Xóa GiaHan
            String deleteGiaHanQuery = "DELETE FROM exam_management_schema.GiaHan WHERE ma_phieu_du_thi IN (" +
                    "SELECT ma_phieu_du_thi FROM exam_management_schema.PhieuDuThi WHERE ma_chi_tiet IN (" +
                    "SELECT ma_chi_tiet FROM exam_management_schema.ChiTietDangKy WHERE ma_phieu_dang_ky = ?))";
            try (PreparedStatement stmt = connection.prepareStatement(deleteGiaHanQuery)) {
                stmt.setInt(1, maPhieuDK);
                stmt.executeUpdate();
            }

            // 3. Xóa PhieuDuThi
            String deletePhieuDuThiQuery = "DELETE FROM exam_management_schema.PhieuDuThi WHERE ma_chi_tiet IN (" +
                    "SELECT ma_chi_tiet FROM exam_management_schema.ChiTietDangKy WHERE ma_phieu_dang_ky = ?)";
            try (PreparedStatement stmt = connection.prepareStatement(deletePhieuDuThiQuery)) {
                stmt.setInt(1, maPhieuDK);
                stmt.executeUpdate();
            }

            // 4. Xóa ChiTietHoaDon
            String deleteChiTietHoaDonQuery = "DELETE FROM exam_management_schema.ChiTietHoaDon WHERE ma_hoa_don IN (" +
                    "SELECT ma_hoa_don FROM exam_management_schema.HoaDon WHERE ma_phieu_dang_ky = ?)";
            try (PreparedStatement stmt = connection.prepareStatement(deleteChiTietHoaDonQuery)) {
                stmt.setInt(1, maPhieuDK);
                stmt.executeUpdate();
            }

            // 5. Xóa HoaDon
            String deleteHoaDonQuery = "DELETE FROM exam_management_schema.HoaDon WHERE ma_phieu_dang_ky = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteHoaDonQuery)) {
                stmt.setInt(1, maPhieuDK);
                stmt.executeUpdate();
            }

            // 6. Xóa ChiTietDangKy
            String deleteChiTietDangKyQuery = "DELETE FROM exam_management_schema.ChiTietDangKy WHERE ma_phieu_dang_ky = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteChiTietDangKyQuery)) {
                stmt.setInt(1, maPhieuDK);
                stmt.executeUpdate();
            }

            // 7. Cập nhật trạng thái Phiếu đăng ký thành "Huy"
            String updateQuery = "UPDATE exam_management_schema.PhieuDangKy SET trang_thai_thanh_toan = 'Huy' WHERE ma_phieu_dang_ky = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
                stmt.setInt(1, maPhieuDK);
                stmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void updateRegistrationStatus(int maPhieuDangKy, String trangThaiThanhToan) throws SQLException {
        String query = "UPDATE exam_management_schema.PhieuDangKy SET trang_thai_thanh_toan = ? WHERE ma_phieu_dang_ky = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, trangThaiThanhToan.equals("Đã thanh toán") ? "Da thanh toan" : "Chua thanh toan");
            stmt.setInt(2, maPhieuDangKy);
            stmt.executeUpdate();
        }
    }

    // Invoice-related methods
    public boolean checkInvoiceExists(int maPhieuDangKy) throws SQLException {
        String query = "SELECT ma_hoa_don FROM exam_management_schema.HoaDon WHERE ma_phieu_dang_ky = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, maPhieuDangKy);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public String getInvoiceStatus(int maPhieuDangKy) throws SQLException {
        String query = "SELECT trang_thai FROM exam_management_schema.HoaDon WHERE ma_phieu_dang_ky = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, maPhieuDangKy);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("trang_thai");
            }
            return null;
        }
    }

    public int createInvoice(PaymentDTO.InvoiceDTO invoice) throws SQLException {
        String insertQuery = "INSERT INTO exam_management_schema.HoaDon " +
                "(ma_phieu_dang_ky, ma_nhan_vien_lap, ngay_thanh_toan, hinh_thuc_thanh_toan, trang_thai, tien_giam_gia, tong_tien) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, invoice.getMaPhieuDangKy());
            stmt.setInt(2, invoice.getMaNhanVienLap());
            stmt.setDate(3, invoice.getNgayThanhToan() != null ? Date.valueOf(invoice.getNgayThanhToan()) : null);
            stmt.setString(4, invoice.getHinhThucThanhToan());
            stmt.setString(5, invoice.getTrangThai());
            stmt.setDouble(6, invoice.getTienGiamGia());
            stmt.setDouble(7, invoice.getTongTien());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            throw new SQLException("Không lấy được mã hóa đơn mới.");
        }
    }

    public void createInvoiceDetails(int maHoaDon, int maPhieuDangKy) throws SQLException {
        String insertQuery = "INSERT INTO exam_management_schema.ChiTietHoaDon (ma_hoa_don, ma_chi_tiet) " +
                "SELECT ?, ma_chi_tiet FROM exam_management_schema.ChiTietDangKy WHERE ma_phieu_dang_ky = ?";
        try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
            stmt.setInt(1, maHoaDon);
            stmt.setInt(2, maPhieuDangKy);
            stmt.executeUpdate();
        }
    }

    public void updateInvoiceStatus(int maPhieuDangKy, String trangThai, LocalDate ngayThanhToan, String hinhThucThanhToan) throws SQLException {
        String query = "UPDATE exam_management_schema.HoaDon SET trang_thai = ?, ngay_thanh_toan = ?, hinh_thuc_thanh_toan = ? WHERE ma_phieu_dang_ky = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, trangThai);
            stmt.setDate(2, Date.valueOf(ngayThanhToan));
            stmt.setString(3, hinhThucThanhToan);
            stmt.setInt(4, maPhieuDangKy);
            stmt.executeUpdate();
        }
    }

    public boolean checkEmployeeExists(int maNhanVien) throws SQLException {
        String query = "SELECT acc_id FROM exam_management_schema.NhanVien WHERE ma_nhan_vien = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, maNhanVien);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public int countCandidates(int maPhieuDangKy) throws SQLException {
        String query = "SELECT COUNT(*) AS so_thi_sinh FROM exam_management_schema.ChiTietDangKy WHERE ma_phieu_dang_ky = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, maPhieuDangKy);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("so_thi_sinh");
            }
            return 0;
        }
    }

    public double calculateTotalFee(int maPhieuDangKy) throws SQLException {
        String query = "SELECT SUM(c.le_phi_thi) AS tong_le_phi " +
                "FROM exam_management_schema.ChiTietDangKy ctdk " +
                "JOIN exam_management_schema.LichThi lt ON ctdk.ma_lich_thi = lt.ma_lich_thi " +
                "JOIN exam_management_schema.ChungChi c ON lt.ma_chung_chi = c.ma_chung_chi " +
                "WHERE ctdk.ma_phieu_dang_ky = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, maPhieuDangKy);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("tong_le_phi");
            }
            return 0;
        }
    }
}