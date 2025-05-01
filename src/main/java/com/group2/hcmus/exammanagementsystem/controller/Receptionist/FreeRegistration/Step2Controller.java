package com.group2.hcmus.exammanagementsystem.controller.Receptionist.FreeRegistration;

import com.group2.hcmus.exammanagementsystem.DTO.FreeRegistrationDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class Step2Controller {
    private StackPane mainContainer;
    private FreeRegistrationDTO dto;

    public void setMainContainer(StackPane mainContainer) {
        this.mainContainer = mainContainer;
    }

    @FXML private TextField tfDiaChi;
    @FXML private TextField tfEmail;
    @FXML private TextField tfHoTen;
    @FXML private TextField tfSDT;
    @FXML private Button btnNext;
    @FXML private Button btnBack;



    @FXML
    void onNext(ActionEvent event) {
//        dto.set
        String hoTen = tfHoTen.getText();
        String sdt = tfSDT.getText();
        String email = tfEmail.getText();
        String diaChi = tfDiaChi.getText();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/group2/hcmus/exammanagementsystem/Receptionist/FreeRegistration/Step3.fxml"));
            Parent step3Root = loader.load();

            Step3Controller step3Controller = loader.getController();
            step3Controller.setMainContainer(mainContainer);

            mainContainer.getChildren().setAll(step3Root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onPrevious(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/group2/hcmus/exammanagementsystem/Receptionist/FreeRegistration/Step2.fxml"));
            Parent step1Root = loader.load();

            Step1Controller step1Controller = loader.getController();
            step1Controller.setMainContainer(mainContainer);

            mainContainer.getChildren().setAll(step1Root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
