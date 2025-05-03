package com.group2.hcmus.exammanagementsystem.controller;

import com.group2.hcmus.exammanagementsystem.DatabaseConnection;
import com.group2.hcmus.exammanagementsystem.controller.Receptionist.ExamExtension.LookUpExamCardController;
import com.group2.hcmus.exammanagementsystem.controller.Receptionist.FreeRegistration.Step1Controller;
import com.group2.hcmus.exammanagementsystem.utils.SupportingUtilities;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.group2.hcmus.exammanagementsystem.controller.LoginAndSignup.LoginController;
import javafx.stage.Stage;

public class DashboardController {
    @FXML private AnchorPane navBarContainer;
    @FXML private StackPane mainInfoContainer;
    private final List<JFXButton> sidebarButtons = new ArrayList<>();
    private String role;
    SupportingUtilities sp = new SupportingUtilities();


    public void setRole(String role) {
        this.role = role;
    }

    @FXML
    public void initialize() {
        LoginController controller = new LoginController();
        setRole(controller.getRole());
        if (this.role == null) {
            sp.showAlert("Role is not set. User is not logged in.");
        }
        else {
            setupSidebar(role);
        }
    }

    public void setupSidebar(String role) {
        navBarContainer.getChildren().clear();
        sidebarButtons.clear();

        switch (role) {
            case "Reception":
                sidebarButtons.add(createButton("Đăng ký tự do", "#FreeRegistration"));
                sidebarButtons.add(createButton("Đăng ký đơn vị", "#ThanhToan"));
                sidebarButtons.add(createButton("Lịch thi", "#TimKiemLichThi"));
                sidebarButtons.add(createButton("Lập phiếu gia hạn", "#LapPhieuGiaHan"));
                break;
            case "Accounting":
                sidebarButtons.add(createButton("Thanh toán", "#ThanhToan"));
                sidebarButtons.add(createButton("Tạo phiếu dự thi", "#TaoPhieuDuThi"));
                sidebarButtons.add(createButton("Thanh toán gia hạn thi", "#ThanhToanGiaHan"));

                break;
            case "Exam":
                sidebarButtons.add(createButton("Tra cứu bảng tính", "#LookUpSpreadsheet"));
                sidebarButtons.add(createButton("Nhập liệu bảng tính", "#InputSpreadsheet"));
                break;
            case "Admin":
                sidebarButtons.add(createButton("Quản trị nhân viên", "#Staff"));
                sidebarButtons.add(createButton("Quản trị khách hàng", "#Customer"));
                sidebarButtons.add(createButton("Quản trị hóa đơn", "#Invoice"));
                sidebarButtons.add(createButton("Quản trị phòng thi", "#Room"));
                sidebarButtons.add(createButton("Quản trị chứng chỉ", "#Certificate"));
                break;
        }

        for (int i = 0; i < sidebarButtons.size(); i++) {
            JFXButton button = sidebarButtons.get(i);
            button.setLayoutY(i * 40);
            navBarContainer.getChildren().add(button);
        }

        // logout button
        JFXButton logoutButton = createButton("Đăng xuất", "#Logout");
        logoutButton.setLayoutY(sidebarButtons.size() * 40);

        logoutButton.setOnAction(e -> {
            try {
                handleNavigation("#Logout", logoutButton);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        sidebarButtons.add(logoutButton);
        navBarContainer.getChildren().add(logoutButton);
    }

    private JFXButton createButton(String text, String action) {
        JFXButton button = new JFXButton(text);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setPrefSize(190, 30);
        button.setLayoutX(5);
        button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: WHITE;");
        button.setFont(new Font("System Bold", 12));

        button.setOnAction(event -> {
            try {
                handleNavigation(action, button);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return button;
    }
    private void resetButtonStyles() {
        for (JFXButton button : sidebarButtons) {
            button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: WHITE;");
        }
    }
    private void handleNavigation(String action, JFXButton clickedButton) throws IOException {
        resetButtonStyles();
        clickedButton.setStyle("-fx-background-color: #ff9700; -fx-background-radius: 10px; -fx-text-fill: WHITE;");

        Parent content = null;

        switch (action) {
            case "#FreeRegistration":
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group2/hcmus/exammanagementsystem/Receptionist/FreeRegistration/Step1.fxml"));
                Parent step1 = loader.load();

                Step1Controller controller = loader.getController();
                controller.setMainContainer(mainInfoContainer); // passing main info inside the stackpane
                mainInfoContainer.getChildren().setAll(step1);
                break;
            case "#ThanhToan":
                content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/group2/hcmus/exammanagementsystem/AccountingStaff/Payment.fxml")));
                break;
            case "#TaoPhieuDuThi":
                content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/group2/hcmus/exammanagementsystem/AccountingStaff/ExamTicket.fxml")));
                break;
            case "#ThanhToanGiaHan":
                content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/group2/hcmus/exammanagementsystem/AccountingStaff/ExtendPayment.fxml")));
                break;
            case "#LapPhieuGiaHan":
                FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/com/group2/hcmus/exammanagementsystem/AccountingStaff/LookUpExamCard.fxml"));
                Parent s1 = loader1.load();
                LookUpExamCardController controller1 = loader1.getController();
                controller1.setMainContainer(mainInfoContainer);
                mainInfoContainer.getChildren().setAll(s1);
                break;
            case "#Staff":
                content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/group2/hcmus/exammanagementsystem/Administrator/StaffManagement.fxml")));
                break;
            case "#Customer":
                content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/group2/hcmus/exammanagementsystem/Administrator/CustomerManagement.fxml")));
                break;
            case "#Invoice":
                content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/group2/hcmus/exammanagementsystem/Administrator/InvoiceManagement.fxml")));
                break;
            case "#Room":
                content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/group2/hcmus/exammanagementsystem/Administrator/RoomManagement.fxml")));
                break;
            case "#Certificate":
                content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/group2/hcmus/exammanagementsystem/Administrator/CertificateManagement.fxml")));
                break;
            case "#LookUpSpreadsheet":
                content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/group2/hcmus/exammanagementsystem/ExaminationStaff/LookUpSpreadsheet.fxml")));
                break;
            case "#InputSpreadsheet":   
                content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/group2/hcmus/exammanagementsystem/ExaminationStaff/InputSpreadsheet.fxml")));
                break;
            case "#TimKiemLichThi":
                content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/group2/hcmus/exammanagementsystem/Receptionist/ScheduleList.fxml")));
                break;
            case "#Logout":
                DatabaseConnection.closeConnection(); // if you have such a method

                FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/com/group2/hcmus/exammanagementsystem/Login.fxml"));
                Scene loginScene = new Scene(loginLoader.load());

                Stage currentStage = (Stage) mainInfoContainer.getScene().getWindow();
                currentStage.setScene(loginScene);
                currentStage.centerOnScreen();
                break;
            default:
                break;
        }

        if (content != null) {
            mainInfoContainer.getChildren().setAll(content);
        }
    }
}
