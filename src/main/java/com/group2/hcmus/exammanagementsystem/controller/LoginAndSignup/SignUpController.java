package com.group2.hcmus.exammanagementsystem.controller.LoginAndSignup;

import com.group2.hcmus.exammanagementsystem.DatabaseConnection;
import com.group2.hcmus.exammanagementsystem.utils.PasswordUtils;
import com.group2.hcmus.exammanagementsystem.utils.SupportingUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.Types.INTEGER;

public class SignUpController {
    @FXML private Button btnBack;
    @FXML private TextField tfEmail;
    @FXML private TextField tfFullname;
    @FXML private TextField tfUsername;
    @FXML private TextField tfPassword;
    @FXML private TextField tfPhoneNumber;
    private SupportingUtilities sp = new SupportingUtilities();
    @FXML public void signUp() {
        // lấy thông tin từ các trường TextField
        String email = tfEmail.getText();
        String fullname = tfFullname.getText();
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        String phone = tfPhoneNumber.getText();

        // generate salt
        String salt = PasswordUtils.generateSalt();

        // hash password with salt
        String hashedPassword = PasswordUtils.hashPassword(password, salt);

        // dung stored procedure usp_sign_up_account
        String resultMessage = callSignUpProcedure(email, phone, username, hashedPassword, fullname, salt);

        if (resultMessage.equals("1")) {
            // Đăng ký thành công
            sp.showAlert("Account created successfully!");
            sp.loadScene("/com/group2/hcmus/exammanagementsystem/Login.fxml", "Login", btnBack);
        } else if (resultMessage.equals("0")) {
            // Email đã tồn tại
            sp.showAlert("Email already exists.");
        } else if (resultMessage.equals("-1")) {
            // Username đã tồn tại
            sp.showAlert("Username already exists.");
        } else if (resultMessage.equals("-2")) {
            // Số điện thoại đã tồn tại
            sp.showAlert("Phone number already exists.");
        }
    }

    private String callSignUpProcedure(String email, String phone, String username, String hashedPassword, String fullname, String salt) {
        String result = "-99"; // Default result in case of failure

        String sql = "{ ? = call exam_management_schema.usp_sign_up_account(?, ?, ?, ?, ?, ?) }"; // Gọi function
        try {
            Connection conn = DatabaseConnection.getConnection();
            CallableStatement stmt = conn.prepareCall(sql);

            // set đầu ra của stored procedure
            stmt.registerOutParameter(1, INTEGER);

            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, username);
            stmt.setString(5, hashedPassword);
            stmt.setString(6, fullname);
            stmt.setString(7, salt);

            stmt.execute();

            // Lấy kết quả trả về từ stored procedure
            result = String.valueOf(stmt.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
            sp.showAlert("Lỗi: " + e.getMessage());
        }

        return result;
    }

    @FXML private void goBackLogin(ActionEvent event) {
        sp.loadScene("/com/group2/hcmus/exammanagementsystem/Login.fxml", "Login", btnBack);
    }
}
