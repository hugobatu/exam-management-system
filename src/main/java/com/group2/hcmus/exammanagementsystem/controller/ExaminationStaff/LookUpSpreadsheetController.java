package com.group2.hcmus.exammanagementsystem.controller.ExaminationStaff;

import com.group2.hcmus.exammanagementsystem.BUS.SpreadsheetBUS;
import com.group2.hcmus.exammanagementsystem.DTO.BangTinhDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.StringConverter;
import javafx.collections.FXCollections;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class LookUpSpreadsheetController implements Initializable {

    @FXML
    private TableView<BangTinhDTO> bangTinhTable;

    @FXML
    private TableColumn<BangTinhDTO, Integer> maChungChiCapColumn;

    @FXML
    private TableColumn<BangTinhDTO, Integer> maPhieuDuThiColumn;

    @FXML
    private TableColumn<BangTinhDTO, BigDecimal> diemColumn;

    @FXML
    private TableColumn<BangTinhDTO, LocalDate> ngayCapColumn;

    @FXML
    private TableColumn<BangTinhDTO, LocalDate> ngayHetHanColumn;

    @FXML
    private TableColumn<BangTinhDTO, String> donViCapColumn;

    @FXML
    private TableColumn<BangTinhDTO, String> trangThaiNhanColumn;

    @FXML
    private TextField searchField;

    private SpreadsheetBUS spreadsheetBUS;
    private ObservableList<BangTinhDTO> bangTinhList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spreadsheetBUS = new SpreadsheetBUS();

        // Set up columns
        maChungChiCapColumn.setCellValueFactory(new PropertyValueFactory<>("maChungChiCap"));
        maPhieuDuThiColumn.setCellValueFactory(new PropertyValueFactory<>("maPhieuDuThi"));
        diemColumn.setCellValueFactory(new PropertyValueFactory<>("diem"));
        ngayCapColumn.setCellValueFactory(new PropertyValueFactory<>("ngayCap"));
        ngayHetHanColumn.setCellValueFactory(new PropertyValueFactory<>("ngayHetHan"));
        donViCapColumn.setCellValueFactory(new PropertyValueFactory<>("donViCap"));
        trangThaiNhanColumn.setCellValueFactory(new PropertyValueFactory<>("trangThaiNhan"));

        bangTinhTable.setEditable(true);

        // Set ComboBoxTableCell for trangThaiNhanColumn
        trangThaiNhanColumn.setCellFactory(ComboBoxTableCell.forTableColumn(
                new StringConverter<String>() {
                    @Override
                    public String toString(String object) {
                        return object;
                    }

                    @Override
                    public String fromString(String string) {
                        return string;
                    }
                },
                "Đã nhận", "Chưa nhận"));

        // Handle edit commit
        trangThaiNhanColumn.setOnEditCommit(event -> {
            BangTinhDTO bangTinh = event.getRowValue();
            String newValue = event.getNewValue();
            bangTinh.setTrangThaiNhan(newValue);
            spreadsheetBUS.updateTrangThaiNhan(bangTinh.getMaChungChiCap(), newValue);
            bangTinhTable.refresh();
        });

        // Load data
        loadBangTinhData();

        // Set up search functionality
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                loadBangTinhData();
            } else {
                searchBangTinh(newValue);
            }
        });
    }

    private void loadBangTinhData() {
        List<BangTinhDTO> data = spreadsheetBUS.getAllBangTinh();
        bangTinhList = FXCollections.observableArrayList(data);
        bangTinhTable.setItems(bangTinhList);
    }

    private void searchBangTinh(String searchTerm) {
        List<BangTinhDTO> searchResults = spreadsheetBUS.searchBangTinh(searchTerm);
        bangTinhList = FXCollections.observableArrayList(searchResults);
        bangTinhTable.setItems(bangTinhList);
    }
}