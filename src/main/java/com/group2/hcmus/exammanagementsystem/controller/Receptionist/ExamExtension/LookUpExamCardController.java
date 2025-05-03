package com.group2.hcmus.exammanagementsystem.controller.Receptionist.ExamExtension;

import com.group2.hcmus.exammanagementsystem.DTO.ExamCardDTO;
import com.group2.hcmus.exammanagementsystem.BUS.ExamCardBUS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class LookUpExamCardController implements Initializable{
    @FXML
    private Button cancelButton;

    @FXML
    private Button clearButton;

    @FXML
    private TableColumn<ExamCardDTO, String> diaChiColumn;

    @FXML
    private TableColumn<ExamCardDTO, LocalTime> gioThiColumn;

    @FXML
    private TableColumn<ExamCardDTO, String> hoTenColumn;

    @FXML
    private TableColumn<ExamCardDTO, Integer> maPhieuColumn;

    @FXML
    private Button nextButton;

    @FXML
    private TableColumn<ExamCardDTO, LocalDate> ngaySinhColumn;

    @FXML
    private TableColumn<ExamCardDTO, LocalDate> ngayThiColumn;

    @FXML
    private TableView<ExamCardDTO> resultTable;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> searchTypeComboBox;

    @FXML
    private Button selectButton;

    @FXML
    private TableColumn<ExamCardDTO, Integer> soBaoDanhColumn;

    private ExamCardBUS examCardService;
    private ObservableList<ExamCardDTO> examCardList;
    private ExamCardDTO selectedExamCard;

    /* Initializes the controller class */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the service
        examCardService = new ExamCardBUS();

        // Initialize the observable list
        examCardList = FXCollections.observableArrayList();

        // Set up the ComboBox
        searchTypeComboBox.getItems().setAll("Mã phiếu dự thi", "Số báo danh", "Họ tên thí sinh");
        searchTypeComboBox.getSelectionModel().selectFirst();

        // Set up table columns
        maPhieuColumn.setCellValueFactory(new PropertyValueFactory<>("maPhieuDuThi"));
        soBaoDanhColumn.setCellValueFactory(new PropertyValueFactory<>("soBaoDanh"));
        hoTenColumn.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        ngaySinhColumn.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        ngayThiColumn.setCellValueFactory(new PropertyValueFactory<>("ngayThi"));
        gioThiColumn.setCellValueFactory(new PropertyValueFactory<>("gioThi"));
        diaChiColumn.setCellValueFactory(new PropertyValueFactory<>("diaDiemThi"));

        // Format date columns
        ngaySinhColumn.setCellFactory(column -> new TableCell<ExamCardDTO, LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(formatter.format(date));
                }
            }
        });

        ngayThiColumn.setCellFactory(column -> new TableCell<ExamCardDTO, LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(formatter.format(date));
                }
            }
        });

        // Format time column
        gioThiColumn.setCellFactory(column -> new TableCell<ExamCardDTO, LocalTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

            @Override
            protected void updateItem(LocalTime time, boolean empty) {
                super.updateItem(time, empty);
                if (empty || time == null) {
                    setText(null);
                } else {
                    setText(formatter.format(time));
                }
            }
        });

        // Bind the table view to the observable list
        resultTable.setItems(examCardList);

        // Set up table row selection listener
        resultTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedExamCard = newSelection;
                selectButton.setDisable(false);
                nextButton.setDisable(false);
            } else {
                selectedExamCard = null;
                selectButton.setDisable(true);
                nextButton.setDisable(true);
            }
        });

        // Initially disable the select and next buttons
        selectButton.setDisable(true);
        nextButton.setDisable(true);

        // Load all exam cards initially
        loadAllExamCards();
    }

    /* Load all exam cards into the table view */
    private void loadAllExamCards() {
        List<ExamCardDTO> cards = examCardService.getAllExamCards();
        examCardList.clear();
        examCardList.addAll(cards);
    }

    /* Handle search button click */
    @FXML
    void handleSearch(ActionEvent event) {
        String searchType = searchTypeComboBox.getValue();
        String searchValue = searchField.getText().trim();

        if (searchValue.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Thông báo", "Vui lòng nhập thông tin tìm kiếm.");
            return;
        }

        List<ExamCardDTO> searchResult;

        switch (searchType) {
            case "Mã phiếu dự thi":
                try {
                    int maPhieu = Integer.parseInt(searchValue);
                    searchResult = examCardService.searchByExamCardId(maPhieu);
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Mã phiếu dự thi phải là số nguyên.");
                    return;
                }
                break;

            case "Số báo danh":
                try {
                    int soBaoDanh = Integer.parseInt(searchValue);
                    searchResult = examCardService.searchByRegistrationNumber(soBaoDanh);
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Số báo danh phải là số nguyên.");
                    return;
                }
                break;

            case "Họ tên thí sinh":
                searchResult = examCardService.searchByName(searchValue);
                break;

            default:
                showAlert(Alert.AlertType.WARNING, "Thông báo", "Vui lòng chọn loại tìm kiếm.");
                return;
        }

        examCardList.clear();
        examCardList.addAll(searchResult);

        if (searchResult.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Không tìm thấy kết quả phù hợp.");
        }
    }

    /* Handle clear button click */
    @FXML
    void handleClear(ActionEvent event) {
        searchField.clear();
        searchTypeComboBox.getSelectionModel().selectFirst();
        loadAllExamCards();
    }

    /* Handle select button click */
    @FXML
    void handleSelect(ActionEvent event) {
        if (selectedExamCard != null) {
            // TODO: Implement logic to handle the selected exam card
            showAlert(Alert.AlertType.INFORMATION, "Thông báo",
                    "Đã chọn phiếu dự thi:\nMã phiếu: " + selectedExamCard.getMaPhieuDuThi() +
                            "\nSố báo danh: " + selectedExamCard.getSoBaoDanh() +
                            "\nThí sinh: " + selectedExamCard.getHoTen());
        }
    }

    /* Handle next button click */
    @FXML
    void handleNext(ActionEvent event) {
        if (selectedExamCard == null) {
            showAlert(Alert.AlertType.WARNING, "Thông báo", "Vui lòng chọn một phiếu dự thi.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group2/hcmus/exammanagementsystem/AccountingStaff/ExtensionTicket.fxml"));
            Parent root = loader.load();

            // Pass the selected exam card to the ExtensionTicketController
            ExtensionTicketController controller = loader.getController();
            controller.setExamCard(selectedExamCard);

            Scene scene = new Scene(root);
            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Gia hạn phiếu dự thi");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể mở màn hình gia hạn phiếu dự thi: " + e.getMessage());
        }
    }


    /* Handle cancel button click */
    @FXML
    void handleCancel(ActionEvent event) {
        // Close the current window
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /* Show an alert dialog */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /* Get the selected exam card */
    public ExamCardDTO getSelectedExamCard() {
        return selectedExamCard;
    }
}
