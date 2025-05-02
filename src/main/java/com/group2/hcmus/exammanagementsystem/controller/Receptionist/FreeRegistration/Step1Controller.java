package com.group2.hcmus.exammanagementsystem.controller.Receptionist.FreeRegistration;

import com.group2.hcmus.exammanagementsystem.DTO.FreeRegistrationDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Step1Controller {
    private StackPane mainContainer; // This will hold step1, step2, step3...
    public void setMainContainer(StackPane mainContainer) {
        this.mainContainer = mainContainer;
    }

    @FXML private Button settings;
    @FXML private TextField tfDiaChi;
    @FXML private TextField tfEmail;
    @FXML private TextField tfHoTen;
    @FXML private TextField tfSDT;
    @FXML private Button btnNext;

    private FreeRegistrationDTO dto = new FreeRegistrationDTO();
    public void setRegistration(FreeRegistrationDTO dto) {
        this.dto = dto;

        tfDiaChi.setText(dto.getDiaChiNDK());
        tfEmail.setText(dto.getEmailNDK());
        tfHoTen.setText(dto.getHoTenNDK());
        tfSDT.setText(dto.getSdtNDK());
    }

    @FXML
    void onNext(ActionEvent event) {
        dto.setHoTenNDK(tfHoTen.getText());
        dto.setSdtNDK(tfSDT.getText());
        dto.setEmailNDK(tfEmail.getText());
        dto.setDiaChiNDK(tfDiaChi.getText());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group2/hcmus/exammanagementsystem/Receptionist/FreeRegistration/Step2.fxml"));
            Parent step2Root = loader.load();

            Step2Controller step2Controller = loader.getController();
            step2Controller.setMainContainer(mainContainer);
            step2Controller.setRegistration(dto);

            mainContainer.getChildren().setAll(step2Root);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Step 2").showAndWait();
        }
    }


}
