package com.group2.hcmus.exammanagementsystem.controller.Receptionist.UnitRegistration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class UnitStep2Controller {
    private StackPane mainContainer; // This will hold step1, step2, step3...
    public void setMainContainer(StackPane mainContainer) {
        this.mainContainer = mainContainer;
    }
    @FXML
    void onNext(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group2/hcmus/exammanagementsystem/Receptionist/UnitRegistration/Step3.fxml"));
            Parent step2Root = loader.load();

            UnitStep3Controller step3Controller = loader.getController();
            step3Controller.setMainContainer(mainContainer);

            mainContainer.getChildren().setAll(step2Root);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Step 3").showAndWait();
        }
    }
}
