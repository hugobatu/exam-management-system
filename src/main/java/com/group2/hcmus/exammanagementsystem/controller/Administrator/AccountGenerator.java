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
        String role = "NVTN";

        AccountGenerator.createAccountWithRole(username, password, role);
    }
}
