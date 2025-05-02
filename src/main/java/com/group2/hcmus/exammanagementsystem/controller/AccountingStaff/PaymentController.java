package com.group2.hcmus.exammanagementsystem.controller.AccountingStaff;

import com.group2.hcmus.exammanagementsystem.BUS.PaymentBUS;
import com.group2.hcmus.exammanagementsystem.DTO.PaymentDTO;
import com.group2.hcmus.exammanagementsystem.utils.SupportingUtilities;
import javafx.collections.FXCollections; import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PaymentController {

    @FXML private TextField txt_MaPhieuDangKy;
    @FXML private TextField txt_MaKhachHang;
    @FXML private DatePicker dp_NgayDangKy;
    @FXML private ComboBox<String> cbb_LoaiKhachHang;
    @FXML private CheckBox chk_DaThanhToan;
    @FXML private CheckBox chk_ChuaThanhToan;
    @FXML private TextField txt_GiamGia;
    @FXML private TextField txt_ThanhTien;
    @FXML private DatePicker dp_NgayThanhToan;
    @FXML private ComboBox<String> cbb_HinhThucThanhToan;
    @FXML private TextField txt_NhanVienLap;
    @FXML private Button btn_LapHoaDon;
    @FXML private Button btn_XacNhan;
    @FXML private Button btn_TimKiem;
    @FXML private Button btn_Reset;

    private PaymentBUS paymentBUS;
    private SupportingUtilities supportingUtilities = new SupportingUtilities();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    public void initialize() {
        System.out.println("PaymentController initializing...");

        cbb_LoaiKhachHang.setItems(FXCollections.observableArrayList("Tự do", "Đơn vị"));
        cbb_HinhThucThanhToan.setItems(FXCollections.observableArrayList("Tiền mặt", "Chuyển khoản"));

        dp_NgayDangKy.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) { return date != null ? DATE_FORMATTER.format(date) : ""; }
            @Override
            public LocalDate fromString(String string) { return string != null && !string.isEmpty() ? LocalDate.parse(string, DATE_FORMATTER) : null; }
        });

        dp_NgayThanhToan.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) { return date != null ? DATE_FORMATTER.format(date) : ""; }
            @Override
            public LocalDate fromString(String string) { return string != null && !string.isEmpty() ? LocalDate.parse(string, DATE_FORMATTER) : null; }
        });

        try {
            paymentBUS = new PaymentBUS();
        } catch (SQLException e) {
            System.err.println("Service initialization error: " + e.getMessage());
            supportingUtilities.showAlert("Lỗi khởi tạo: " + e.getMessage());
        }

        txt_MaPhieuDangKy.setOnAction(event -> loadRegistrationInfo());
        btn_TimKiem.setOnAction(event -> loadRegistrationInfo());

        chk_DaThanhToan.setOnAction(event -> { if (chk_DaThanhToan.isSelected()) chk_ChuaThanhToan.setSelected(false); });
        chk_ChuaThanhToan.setOnAction(event -> { if (chk_ChuaThanhToan.isSelected()) chk_DaThanhToan.setSelected(false); });

        btn_Reset.setOnAction(this::handleReset);
        btn_LapHoaDon.setOnAction(this::handleLapHoaDon);
        btn_XacNhan.setOnAction(this::handleXacNhanThanhToan);
    }

    private void loadRegistrationInfo() {
        String maPhieu = txt_MaPhieuDangKy.getText().trim();
        if (maPhieu.isEmpty()) {
            supportingUtilities.showAlert("Thiếu thông tin: Vui lòng nhập mã phiếu đăng ký.");
            clearFields();
            return;
        }

        try {
            int maPhieuDangKy = Integer.parseInt(maPhieu);
            PaymentDTO.RegistrationDTO registration = paymentBUS.getRegistrationById(maPhieuDangKy);
            if (registration == null) {
                supportingUtilities.showAlert("Không tìm thấy: Mã phiếu đăng ký không tồn tại.");
                clearFields();
                return;
            }

            if ("Huy".equals(registration.getTrangThaiThanhToan())) {
                supportingUtilities.showAlert("Lỗi: Phiếu đăng ký này đã bị hủy.");
                clearFields();
                return;
            }

            txt_MaKhachHang.setText(String.valueOf(registration.getMaKhachHang()));
            dp_NgayDangKy.setValue(registration.getNgayDangKy());
            cbb_LoaiKhachHang.setValue(registration.getLoaiKhachHang());
            chk_DaThanhToan.setSelected("Da thanh toan".equals(registration.getTrangThaiThanhToan()));
            chk_ChuaThanhToan.setSelected("Chua thanh toan".equals(registration.getTrangThaiThanhToan()));
            txt_GiamGia.setText(String.format("%.3f", registration.getGiamGia()));
            txt_ThanhTien.setText(String.format("%.3f", registration.getThanhTien()));
        } catch (SQLException e) {
            System.err.println("Load error: " + e.getMessage());
            supportingUtilities.showAlert("Lỗi truy vấn: " + e.getMessage());
        } catch (NumberFormatException e) {
            supportingUtilities.showAlert("Lỗi định dạng: Mã phiếu phải là số.");
            clearFields();
        }
    }

    @FXML
    private void handleLapHoaDon(ActionEvent event) {
        if (!validateInput()) return;

        LocalDate ngayDangKy = dp_NgayDangKy.getValue();
        LocalDate ngayThanhToan = dp_NgayThanhToan.getValue();

        if (ngayDangKy == null || ngayThanhToan == null) {
            supportingUtilities.showAlert("Lỗi: Vui lòng nhập đầy đủ ngày đăng ký và ngày thanh toán.");
            return;
        }

        try {
            int maPhieuDK = Integer.parseInt(txt_MaPhieuDangKy.getText().trim());
            if (!paymentBUS.checkPaymentDeadline(ngayDangKy, ngayThanhToan)) {
                paymentBUS.cancelRegistration(maPhieuDK);
                supportingUtilities.showAlert("Phiếu đăng ký đã quá hạn thanh toán, đã hủy phiếu đăng ký.");
                clearFields();
                return;
            }

            String maNhanVienStr = txt_NhanVienLap.getText().trim();
            int maNhanVien = Integer.parseInt(maNhanVienStr);
            if (!paymentBUS.checkEmployeeExists(maNhanVien)) {
                supportingUtilities.showAlert("Lỗi: Mã nhân viên " + maNhanVien + " không tồn tại.");
                return;
            }

            if (paymentBUS.checkInvoiceExists(maPhieuDK)) {
                supportingUtilities.showAlert("Lỗi: Hóa đơn cho phiếu đăng ký này đã tồn tại.");
                return;
            }

            String hinhThucThanhToan = cbb_HinhThucThanhToan.getValue();
            String trangThai = chk_DaThanhToan.isSelected() ? "Đã thanh toán" : "Chưa thanh toán";
            double tienGiamGia = Double.parseDouble(txt_GiamGia.getText().trim());
            double tongTien = Double.parseDouble(txt_ThanhTien.getText().trim());

            PaymentDTO.InvoiceDTO invoice = new PaymentDTO.InvoiceDTO(maPhieuDK, maNhanVien, ngayThanhToan, hinhThucThanhToan, trangThai, tienGiamGia, tongTien);
            int maHoaDon = paymentBUS.createInvoice(invoice);
            supportingUtilities.showAlert("Thành công: Lập hóa đơn thành công. Mã hóa đơn: " + maHoaDon);
            clearFields();
        } catch (SQLException e) {
            System.err.println("SQL Exception in handleLapHoaDon: " + e.getMessage());
            supportingUtilities.showAlert("Lỗi lập hóa đơn: " + e.getMessage());
        } catch (NumberFormatException e) {
            supportingUtilities.showAlert("Lỗi định dạng: Giá trị không hợp lệ.");
        }
    }

    @FXML
    private void handleXacNhanThanhToan(ActionEvent event) {
        if (!validateInput()) return;

        LocalDate ngayDangKy = dp_NgayDangKy.getValue();
        LocalDate ngayThanhToan = dp_NgayThanhToan.getValue();

        if (ngayDangKy == null || ngayThanhToan == null) {
            supportingUtilities.showAlert("Lỗi: Vui lòng nhập đầy đủ ngày đăng ký và ngày thanh toán.");
            return;
        }

        try {
            int maPhieuDK = Integer.parseInt(txt_MaPhieuDangKy.getText().trim());
            if (!paymentBUS.checkPaymentDeadline(ngayDangKy, ngayThanhToan)) {
                paymentBUS.cancelRegistration(maPhieuDK);
                supportingUtilities.showAlert("Phiếu đăng ký đã quá hạn thanh toán, đã hủy phiếu đăng ký.");
                clearFields();
                return;
            }

            String trangThaiMoi = chk_DaThanhToan.isSelected() ? "Đã thanh toán" : "Chưa thanh toán";
            String hinhThucThanhToan = cbb_HinhThucThanhToan.getValue();

            if (!paymentBUS.checkInvoiceExists(maPhieuDK)) {
                supportingUtilities.showAlert("Lỗi: Không tìm thấy hóa đơn cho phiếu đăng ký này.");
                return;
            }

            if (!"Đã thanh toán".equals(trangThaiMoi)) {
                supportingUtilities.showAlert("Lỗi: Trạng thái thanh toán phải là 'Đã thanh toán' để xác nhận.");
                return;
            }

            paymentBUS.confirmPayment(maPhieuDK, trangThaiMoi, ngayThanhToan, hinhThucThanhToan);
            supportingUtilities.showAlert("Thành công: Xác nhận thanh toán thành công.");
            clearFields();
        } catch (SQLException e) {
            System.err.println("SQL Exception in handleXacNhanThanhToan: " + e.getMessage());
            supportingUtilities.showAlert("Lỗi xác nhận: " + e.getMessage());
        } catch (NumberFormatException e) {
            supportingUtilities.showAlert("Lỗi định dạng: Giá trị không hợp lệ.");
        }
    }

    private boolean validateInput() {
        if (txt_MaPhieuDangKy.getText().trim().isEmpty()) {
            supportingUtilities.showAlert("Thiếu thông tin: Vui lòng nhập mã phiếu đăng ký.");
            return false;
        }
        if (dp_NgayThanhToan.getValue() == null) {
            supportingUtilities.showAlert("Thiếu thông tin: Vui lòng chọn ngày thanh toán.");
            return false;
        }
        if (cbb_HinhThucThanhToan.getValue() == null) {
            supportingUtilities.showAlert("Thiếu thông tin: Vui lòng chọn hình thức thanh toán.");
            return false;
        }
        if (txt_NhanVienLap.getText().trim().isEmpty()) {
            supportingUtilities.showAlert("Thiếu thông tin: Vui lòng nhập mã nhân viên lập.");
            return false;
        }
        if (!chk_DaThanhToan.isSelected() && !chk_ChuaThanhToan.isSelected()) {
            supportingUtilities.showAlert("Thiếu thông tin: Vui lòng chọn trạng thái thanh toán.");
            return false;
        }
        return true;
    }

    private void clearFields() {
        txt_MaPhieuDangKy.clear();
        txt_MaKhachHang.clear();
        dp_NgayDangKy.setValue(null);
        cbb_LoaiKhachHang.setValue(null);
        chk_DaThanhToan.setSelected(false);
        chk_ChuaThanhToan.setSelected(false);
        txt_GiamGia.clear();
        txt_ThanhTien.clear();
        dp_NgayThanhToan.setValue(null);
        cbb_HinhThucThanhToan.setValue(null);
        txt_NhanVienLap.clear();
    }

    @FXML
    private void handleReset(ActionEvent event) {
        clearFields();
        supportingUtilities.showAlert("Thành công: Form đã được reset.");
    }
}