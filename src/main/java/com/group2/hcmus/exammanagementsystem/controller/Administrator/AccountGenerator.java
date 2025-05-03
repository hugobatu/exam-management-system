package com.group2.hcmus.exammanagementsystem.controller.Administrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.group2.hcmus.exammanagementsystem.DatabaseConnection;
import com.group2.hcmus.exammanagementsystem.utils.PasswordUtils;

public class AccountGenerator {

    public static void createAccountWithRole(String username, String password, String role) {
        PasswordUtils pw = new PasswordUtils();

        try {
            String salt = pw.generateSalt();
            String hashedPassword = pw.hashPassword(password, salt);
            String sqlQuery = "INSERT INTO exam_management_schema.ACCOUNT (username, hashed_password, salt, account_type) VALUES (?, ?, ?, ?)";

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sqlQuery);

            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, salt);
            pstmt.setString(4, role);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account created successfully for user: " + username);
            } else {
                System.out.println("Failed to create account.");
            }

            pstmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        String username = "a";
        String password = "a";
        String role = "Admin";

        String username_r = "r";
        String password_r = "r";
        String role_r = "Reception";

        String username_e = "e";
        String password_e = "e";
        String role_e = "Exam";

        String username_aa = "ac";
        String password_aa = "ac";
        String role_aa = "Accounting";

        AccountGenerator.createAccountWithRole(username, password, role);
        AccountGenerator.createAccountWithRole(username_aa, password_aa, role_aa);
        AccountGenerator.createAccountWithRole(username_r, password_r, role_r);
        AccountGenerator.createAccountWithRole(username_e, password_e, role_e);
    }
}
