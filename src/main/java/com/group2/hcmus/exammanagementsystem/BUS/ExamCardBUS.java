package com.group2.hcmus.exammanagementsystem.BUS;

import com.group2.hcmus.exammanagementsystem.DAO.ExamCardDAO;
import com.group2.hcmus.exammanagementsystem.DTO.ExamCardDTO;

import java.util.List;

public class ExamCardBUS {
    private final ExamCardDAO examCardDAO;

    public ExamCardBUS() {
        this.examCardDAO = new ExamCardDAO();
    }

    // Search exam cards by ID with business validation
    public List<ExamCardDTO> searchByExamCardId(int maPhieuDuThi) {
        if (maPhieuDuThi <= 0) {
            throw new IllegalArgumentException("Mã phiếu dự thi không hợp lệ");
        }
        return examCardDAO.searchByExamCardId(maPhieuDuThi);
    }

    // Search exam cards by registration number with business validation
    public List<ExamCardDTO> searchByRegistrationNumber(int soBaoDanh) {
        if (soBaoDanh <= 0) {
            throw new IllegalArgumentException("Số báo danh không hợp lệ");
        }
        return examCardDAO.searchByRegistrationNumber(soBaoDanh);
    }

    // Search exam cards by candidate name with business validation
    public List<ExamCardDTO> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên thí sinh không được để trống");
        }
        return examCardDAO.searchByName(name);
    }

    // Get all exam cards
    public List<ExamCardDTO> getAllExamCards() {
        return examCardDAO.getAllExamCards();
    }

    // Additional business logic methods can be added here
    // For example, validating if a candidate is eligible for an exam,
    // checking if the exam card has expired, etc.

    /**
     * Check if an exam card is valid for today's date
     * This is an example of business logic that would go in the BUS layer
     */
    public boolean isExamCardValid(ExamCardDTO examCard) {
        if (examCard == null) {
            return false;
        }

        // Example logic: Check if exam date is in the future
        java.time.LocalDate today = java.time.LocalDate.now();
        return !examCard.getNgayThi().isBefore(today);
    }
}