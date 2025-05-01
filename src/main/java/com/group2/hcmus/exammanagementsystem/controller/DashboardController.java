package com.group2.hcmus.exammanagementsystem.controller;

import com.group2.hcmus.exammanagementsystem.controller.Receptionist.FreeRegistration.Step1Controller;
import com.group2.hcmus.exammanagementsystem.utils.SupportingUtilities;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.group2.hcmus.exammanagementsystem.controller.LoginAndSignup.LoginController;

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
            case "NVTN":
                sidebarButtons.add(createButton("Đăng ký tự do", "#FreeRegistration"));
                sidebarButtons.add(createButton("Đăng ký đon vị", "#ThanhToan"));
                sidebarButtons.add(createButton("Lịch thi", "#ThanhToan"));
//                sidebarButtons.add(createButton("Thanh toán", "#ThanhToan"));

                break;
            case "Accounting":
                sidebarButtons.add(createButton("Lập phiếu gia hạn", "#LapPhieuGiaHan"));
                break;
            case "Inputting":
                sidebarButtons.add(createButton("Nhập kết quả", "#NhapKetQua"));
                break;
            case "Khao thi":
            case "Admin":
                sidebarButtons.add(createButton("Thanh toán", "#ThanhToan"));
                break;
        }

        for (int i = 0; i < sidebarButtons.size(); i++) {
            JFXButton button = sidebarButtons.get(i);
            button.setLayoutY(i * 40);
            navBarContainer.getChildren().add(button);
        }
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
                content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/group2/hcmus/exammanagementsystem/Receptionist/FreeRegistration/Step2.fxml")));
                break;
            case "#LapPhieuGiaHan":
                content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/group2/hcmus/exammanagementsystem/AccountingStaff/LookUpExamCard.fxml")));
                break;
            case "#NhapKetQua":
                content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/hcmus/exammanagement/CapChungChi/nhap-ket-qua.fxml")));
                break;
            default:
                break;
        }

        if (content != null) {
            mainInfoContainer.getChildren().setAll(content);
        }
    }
}
