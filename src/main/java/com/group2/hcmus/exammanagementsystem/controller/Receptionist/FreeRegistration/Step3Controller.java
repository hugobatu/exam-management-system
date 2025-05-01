package com.group2.hcmus.exammanagementsystem.controller.Receptionist.FreeRegistration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

public class Step3Controller {

    @FXML private ComboBox<?> lichthi;
    @FXML private CheckBox ngoaingu;
    @FXML private TableColumn<?, ?> diadiemthi;
    @FXML private TableColumn<?, ?> giothi;
    @FXML private TableColumn<?, ?> ngaythi;
    @FXML private TableColumn<?, ?> phongthi;
    @FXML private TableColumn<?, ?> sl_dangky;
    @FXML private TableColumn<?, ?> sl_toida;


    @FXML private Button btnBack;
    @FXML private Button btnConfirm;

    private StackPane mainContainer; // Shared StackPane for all steps

    public void setMainContainer(StackPane mainContainer) {
        this.mainContainer = mainContainer;
    }
    @FXML
    void onConfirm(ActionEvent event) {
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
                    "/com/group2/hcmus/exammanagementsystem/view/Receptionist/FreeRegistration/Step2.fxml"));
            Parent step1Root = loader.load();

            Step2Controller step2Controller = loader.getController();
            step2Controller.setMainContainer(mainContainer);

            mainContainer.getChildren().setAll(step1Root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
