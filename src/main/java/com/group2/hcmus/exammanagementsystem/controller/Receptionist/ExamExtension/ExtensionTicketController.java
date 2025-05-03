package com.group2.hcmus.exammanagementsystem.controller.Receptionist.ExamExtension;

import com.group2.hcmus.exammanagementsystem.DTO.ExamCardDTO;
import com.group2.hcmus.exammanagementsystem.DTO.ExtensionTicketDTO;
import com.group2.hcmus.exammanagementsystem.BUS.ExtensionTicketBUS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class ExtensionTicketController implements Initializable {
    @FXML
    private Button backButton;

    @FXML
    private Button browseButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextArea ghiChuTextArea;

    @FXML
    private TextField hoTenTextField;

    @FXML
    private TextField lichThiTextField;

    @FXML
    private ComboBox<String> loaiGiaHanComboBox;

    @FXML
    private ComboBox<String> lyDoGiaHanComboBox;

    @FXML
    private TextField maPhieuTextField;

    @FXML
    private Button nextButton;

    @FXML
    private DatePicker ngayGiaHanPicker;

    @FXML
    private TextField ngayGioThiTextField;

    @FXML
    private TextField ngaySinhTextField;

    @FXML
    private TextField phiGiaHanTextField;

    @FXML
    private TextField soBaoDanhTextField;

    @FXML
    private Label soLanGiaHanLabel;

    private ExamCardDTO examCard;
    private ExtensionTicketBUS extensionService;
    private File selectedFile;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    // method for loading into new stack pane
    private StackPane mainContainer; // This will hold step1, step2, step3...
    public void setMainContainer(StackPane mainContainer) {
        this.mainContainer = mainContainer;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the extension service
        extensionService = new ExtensionTicketBUS();

        // Set default values
        ngayGiaHanPicker.setValue(LocalDate.now());

        // Initialize combo boxes with data
        initializeComboBoxes();

        // Set up change listeners for combo boxes
        setupComboBoxListeners();
    }

    private void initializeComboBoxes() {
        // Add extension types
        loaiGiaHanComboBox.getItems().addAll();

        // Add extension reasons
        lyDoGiaHanComboBox.getItems().addAll();
    }

    private void setupComboBoxListeners() {
        // Calculate fee when extension type or reason changes
        loaiGiaHanComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            calculateFee();
        });

        lyDoGiaHanComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            calculateFee();
        });
    }

    private void calculateFee() {
        String extensionType = loaiGiaHanComboBox.getValue();
        String reason = lyDoGiaHanComboBox.getValue();

        if (extensionType != null && reason != null) {
            // Use the service to calculate the fee
            double fee = extensionService.calculateExtensionFee(extensionType, reason);

            // Format the fee and update the text field
            DecimalFormat formatter = new DecimalFormat("#,###");
            phiGiaHanTextField.setText(formatter.format(fee) + " VND");
        }
    }

    public void setExamCard(ExamCardDTO examCard) {
        this.examCard = examCard;

        // Populate fields with exam card data
        maPhieuTextField.setText(String.valueOf(examCard.getMaPhieuDuThi()));
        soBaoDanhTextField.setText(String.valueOf(examCard.getSoBaoDanh()));
        hoTenTextField.setText(examCard.getHoTen());
        ngaySinhTextField.setText(examCard.getNgaySinh().format(dateFormatter));

        // Set exam schedule info
        lichThiTextField.setText(examCard.getNgayThi().format(dateFormatter));
        ngayGioThiTextField.setText(examCard.getGioThi().format(timeFormatter)
        );

        // Get number of previous extensions
        int extensions = extensionService.getExtensionCount(examCard.getMaPhieuDuThi());
        soLanGiaHanLabel.setText(String.valueOf(extensions));

        // Disable next button if already has 2 extensions
        if (extensions >= 2) {
            nextButton.setDisable(true);
            showAlert(Alert.AlertType.WARNING, "Thông báo",
                    "Phiếu dự thi này đã sử dụng tối đa số lần gia hạn (2 lần).");
        }
    }

    @FXML
    void handleBack(ActionEvent event) {
        try {
            // Load LookUpExamCard.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group2/hcmus/exammanagementsystem/AccountingStaff/LookUpExamCard.fxml"));
            Parent root = loader.load();

            // Get controller and set mainContainer back if needed
            LookUpExamCardController controller = loader.getController();
            controller.setMainContainer(mainContainer);

            // Switch screen
            mainContainer.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể quay lại màn hình tra cứu: " + e.getMessage());
        }
    }


    @FXML
    void handleCancel(ActionEvent event) {
        // Ask for confirmation before canceling
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Xác nhận");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Bạn có chắc muốn hủy đăng ký gia hạn không?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Close the window
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void handleNext(ActionEvent event) {
        // Validate required fields
        if (!validateFields()) {
            return;
        }

        // Create extension ticket DTO
        ExtensionTicketDTO ticket = new ExtensionTicketDTO();
        ticket.setMaPhieuDuThi(examCard.getMaPhieuDuThi());
        ticket.setNgayGiaHan(ngayGiaHanPicker.getValue());
        ticket.setLoaiGiaHan(loaiGiaHanComboBox.getValue());
        ticket.setLyDoGiaHan(lyDoGiaHanComboBox.getValue());
        ticket.setGhiChu(ghiChuTextArea.getText());

        // Parse fee (remove formatting)
        String feeText = phiGiaHanTextField.getText().replaceAll("[^\\d]", "");
        ticket.setPhiGiaHan(Double.parseDouble(feeText));

        // Save the extension ticket
        boolean success = extensionService.saveExtensionTicket(ticket);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Thành công",
                    "Đã lưu thông tin gia hạn. Bạn có thể tiếp tục để chọn lịch thi mới.");

            // Load next screen using mainContainer (StackPane)
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group2/hcmus/exammanagementsystem/AccountingStaff/NewExamSchedule.fxml"));
                Parent root = loader.load();

                // Get controller and pass data
                NewExamScheduleController step3 = loader.getController();
                step3.setMainContainer(mainContainer);
                step3.setExamCard(examCard);
                step3.setExtensionTicket(ticket);

                // Replace current content
                mainContainer.getChildren().setAll(root);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể mở màn hình chọn lịch thi: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể lưu thông tin gia hạn. Vui lòng thử lại.");
        }
    }


    private boolean validateFields() {
        StringBuilder errorMessage = new StringBuilder();

        if (loaiGiaHanComboBox.getValue() == null) {
            errorMessage.append("- Vui lòng chọn loại gia hạn\n");
        }

        if (lyDoGiaHanComboBox.getValue() == null) {
            errorMessage.append("- Vui lòng chọn lý do gia hạn\n");
        }

        if (ngayGiaHanPicker.getValue() == null) {
            errorMessage.append("- Vui lòng chọn ngày gia hạn\n");
        } else if (ngayGiaHanPicker.getValue().isBefore(LocalDate.now())) {
            errorMessage.append("- Ngày gia hạn không thể là ngày trong quá khứ\n");
        }

        if (errorMessage.length() > 0) {
            showAlert(Alert.AlertType.WARNING, "Thông tin không hợp lệ", errorMessage.toString());
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}