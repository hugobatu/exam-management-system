package com.group2.hcmus.exammanagementsystem.repository;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("✅ Connected to PostgreSQL!");
        } catch (Exception e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}
