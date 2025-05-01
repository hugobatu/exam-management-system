package com.group2.hcmus.exammanagementsystem.controller.LoginAndSignup;

import com.group2.hcmus.exammanagementsystem.DatabaseConnection;
import com.group2.hcmus.exammanagementsystem.utils.PasswordUtils;
import com.group2.hcmus.exammanagementsystem.utils.SupportingUtilities;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private static String role;
    private static String username;

    @FXML private TextField tfUsername;
    @FXML private PasswordField tfPassword;
    @FXML private Button btnLogin;

    @FXML
    public void initialize() {
        btnLogin.setOnMousePressed(event -> {
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100), btnLogin);
            scaleDown.setToX(0.95);
            scaleDown.setToY(0.95);
            scaleDown.play();
        });

        btnLogin.setOnMouseReleased(event -> {
            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), btnLogin);
            scaleUp.setToX(1.0);
            scaleUp.setToY(1.0);
            scaleUp.play();
        });
    }

    @FXML
    private void login(ActionEvent event) {
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        login(username, password);
    }

    public void login(String username, String password) {
        String sql = "SELECT hashed_password, salt FROM exam_management_schema.Account WHERE username = ?";
        String sql_role = "SELECT account_type FROM exam_management_schema.Account where username = ?";
        SupportingUtilities sp = new SupportingUtilities();

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("hashed_password");
                String storedSalt = rs.getString("salt");

                // verify password
                if (PasswordUtils.verifyPassword(password, storedHash, storedSalt)) {

                    // retrieve role
                    PreparedStatement stmtRole = conn.prepareStatement(sql_role);
                    stmtRole.setString(1, username);
                    ResultSet rsRole = stmtRole.executeQuery();

                    if (rsRole.next()) {
                        this.role = rsRole.getString("account_type");
                        this.username = username;

                        sp.showAlert("Logged in as: " + this.username + " with role: " + this.role);
                        sp.loadScene("/com/group2/hcmus/exammanagementsystem/Dashboard.fxml", "Application", btnLogin);
                    } else {
                        sp.showAlert("Role not found.");
                    }
                } else {
                    sp.showAlert("Password does not match");
                }
            } else {
                sp.showAlert("No account found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sp.showAlert("Error: " + e.getMessage());
        }
    }
    public static String getRole() {return role;};
    public static String getUsername() {return username;};
}
