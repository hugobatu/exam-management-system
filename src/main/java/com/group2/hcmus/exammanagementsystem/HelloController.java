package com.group2.hcmus.exammanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class HelloController {
    @FXML
    public Button logIn;
    public Text status;
//    @FXML

    @FXML
    protected void loginButtonClick() {
        status.setText("Login test");
    }
}