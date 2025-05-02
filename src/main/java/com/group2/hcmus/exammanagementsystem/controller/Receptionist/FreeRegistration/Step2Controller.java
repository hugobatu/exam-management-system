package com.group2.hcmus.exammanagementsystem.controller.Receptionist.FreeRegistration;

import com.group2.hcmus.exammanagementsystem.DTO.FreeRegistrationDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.time.LocalDate;

public class Step2Controller {

    private StackPane mainContainer;
    private FreeRegistrationDTO dto = new FreeRegistrationDTO();

    public void setMainContainer(StackPane mainContainer) {
        this.mainContainer = mainContainer;
    }

    public void setRegistration(FreeRegistrationDTO dto) {
        this.dto = dto;

        tfHoTen.setText(dto.getHotenTS());
        tfDiaChi.setText(dto.getDiaChiTS());
        tfEmail.setText(dto.getEmailTS());
        tfSDT.setText(dto.getSdtTS());
        dpNgaySinh.setValue(dto.getNgaySinh());
    }

    @FXML private TextField tfHoTen;
    @FXML private TextField tfDiaChi;
    @FXML private TextField tfEmail;
    @FXML private TextField tfSDT;
    @FXML private DatePicker dpNgaySinh;
    @FXML private Button btnNext;
    @FXML private Button btnBack;

    @FXML
    void onNext(ActionEvent event) {
        // Save current inputs into DTO
        dto.setHotenTS(tfHoTen.getText());
        dto.setDiaChiTS(tfDiaChi.getText());
        dto.setEmailTS(tfEmail.getText());
        dto.setSdtTS(tfSDT.getText());
        dto.setNgaySinh(dpNgaySinh.getValue());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/group2/hcmus/exammanagementsystem/Receptionist/FreeRegistration/Step3.fxml"));
            Parent step3Root = loader.load();

            Step3Controller step3Controller = loader.getController();
            step3Controller.setMainContainer(mainContainer);
            step3Controller.setRegistration(dto); // pass data to next step

            mainContainer.getChildren().setAll(step3Root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onPrevious(ActionEvent event) {
        dto.setHotenTS(tfHoTen.getText());
        dto.setDiaChiTS(tfDiaChi.getText());
        dto.setEmailTS(tfEmail.getText());
        dto.setSdtTS(tfSDT.getText());
        dto.setNgaySinh(dpNgaySinh.getValue());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/group2/hcmus/exammanagementsystem/Receptionist/FreeRegistration/Step1.fxml"));
            Parent step1Root = loader.load();

            Step1Controller step1Controller = loader.getController();
            step1Controller.setMainContainer(mainContainer);
            step1Controller.setRegistration(dto); // pass data back

            mainContainer.getChildren().setAll(step1Root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
