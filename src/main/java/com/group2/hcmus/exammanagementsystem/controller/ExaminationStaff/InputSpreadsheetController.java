package com.group2.hcmus.exammanagementsystem.controller.ExaminationStaff;

import com.group2.hcmus.exammanagementsystem.BUS.SpreadsheetBUS;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class InputSpreadsheetController implements Initializable {
    
    @FXML
    private TextField txtMaChungChi;
    
    @FXML
    private TextField txtMaPhieuDuThi;
    
    @FXML
    private TextField txtDiem;
    
    @FXML
    private TextField txtDonViCap;
    
    @FXML
    private DatePicker dpNgayCap;
    
    @FXML
    private DatePicker dpNgayHetHan;
    
    @FXML
    private Button btnThem;
    
    private SpreadsheetBUS spreadsheetBUS;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spreadsheetBUS = new SpreadsheetBUS();
        
        // Add action handler for the "Thêm" button
        btnThem.setOnAction(event -> handleThemButtonClick());
    }
    
    private void handleThemButtonClick() {
        try {
            // Get values from form fields
            int maChungChiCap = Integer.parseInt(txtMaChungChi.getText().trim());
            int maPhieuDuThi = Integer.parseInt(txtMaPhieuDuThi.getText().trim());
            BigDecimal diem = new BigDecimal(txtDiem.getText().trim());
            String donViCap = txtDonViCap.getText().trim();
            
            // Insert the new BangTinh record
            String result = spreadsheetBUS.insert(
                maChungChiCap,
                maPhieuDuThi,
                diem,
                dpNgayCap.getValue(),
                dpNgayHetHan.getValue(),
                donViCap
            );
            
            // Show appropriate message
            if ("Success".equals(result)) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm bảng tính thành công!");
                clearForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", result);
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập đúng định dạng số cho mã chứng chỉ, mã phiếu dự thi và điểm");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Đã xảy ra lỗi: " + e.getMessage());
        }
    }
    
    private void clearForm() {
        txtMaChungChi.clear();
        txtMaPhieuDuThi.clear();
        txtDiem.clear();
        txtDonViCap.clear();
        dpNgayCap.setValue(null);
        dpNgayHetHan.setValue(null);
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}