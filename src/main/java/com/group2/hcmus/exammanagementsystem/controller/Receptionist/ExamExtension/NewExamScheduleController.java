package com.group2.hcmus.exammanagementsystem.controller.Receptionist.ExamExtension;

import com.group2.hcmus.exammanagementsystem.BUS.NewExamScheduleBUS;
import com.group2.hcmus.exammanagementsystem.DTO.ExamCardDTO;
import com.group2.hcmus.exammanagementsystem.DTO.NewExamScheduleDTO;
import com.group2.hcmus.exammanagementsystem.DTO.ExtensionTicketDTO;
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

public class NewExamScheduleController implements Initializable {

    @FXML private TextField maPhieuTextField;
    @FXML private TextField soBaoDanhTextField;
    @FXML private TextField hoTenTextField;
    @FXML private TextField lichThiHienTaiTextField;

    @FXML private DatePicker fromDatePicker;
    @FXML private DatePicker toDatePicker;

    @FXML private TableView<NewExamScheduleDTO> scheduleTable;
    @FXML private TableColumn<NewExamScheduleDTO, Integer> maLichThiColumn;
    @FXML private TableColumn<NewExamScheduleDTO, Integer> maChungChiColumn;
    @FXML private TableColumn<NewExamScheduleDTO, LocalDate> ngayThiColumn;
    @FXML private TableColumn<NewExamScheduleDTO, LocalTime> gioThiColumn;
    @FXML private TableColumn<NewExamScheduleDTO, String> diaChiColumn;
    @FXML private TableColumn<NewExamScheduleDTO, Integer> soLuongCLColumn;

    @FXML private TextField maLichThiMoiTextField;
    @FXML private TextField ngayThiMoiTextField;
    @FXML private TextField gioThiMoiTextField;
    @FXML private TextField diaChiMoiTextField;

    @FXML private Button backButton;
    @FXML private Button cancelButton;
    @FXML private Button nextButton;

    private ExamCardDTO examCard;
    private ExtensionTicketDTO extensionTicket;
    private ObservableList<NewExamScheduleDTO> availableSchedules;
    private final NewExamScheduleBUS scheduleBUS = new NewExamScheduleBUS();

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Map table columns
        maLichThiColumn.setCellValueFactory(new PropertyValueFactory<>("maLichThi"));
        maChungChiColumn.setCellValueFactory(new PropertyValueFactory<>("maChungChi"));
        ngayThiColumn.setCellValueFactory(new PropertyValueFactory<>("ngayThi"));
        gioThiColumn.setCellValueFactory(new PropertyValueFactory<>("gioThi"));
        diaChiColumn.setCellValueFactory(new PropertyValueFactory<>("diaDiemThi"));
        soLuongCLColumn.setCellValueFactory(new PropertyValueFactory<>("soLuongConLai"));

        scheduleTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        scheduleTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateSelectedScheduleDetails(newSel);
            }
        });

        // Load all available schedules
        loadAvailableSchedules();
    }

    public void setExamCard(ExamCardDTO examCard) {
        this.examCard = examCard;

        if (examCard != null) {
            maPhieuTextField.setText(String.valueOf(examCard.getMaPhieuDuThi()));
            soBaoDanhTextField.setText(String.valueOf(examCard.getSoBaoDanh()));
            hoTenTextField.setText(examCard.getHoTen());

            String lichThi = examCard.getNgayThi().format(dateFormatter) + " " +
                    examCard.getGioThi().format(timeFormatter);
            lichThiHienTaiTextField.setText(lichThi);
        }
    }

    public void setExtensionTicket(ExtensionTicketDTO extensionTicket) {
        this.extensionTicket = extensionTicket;
    }

    private void loadAvailableSchedules() {
        List<NewExamScheduleDTO> list = scheduleBUS.getAvailableSchedules();
        availableSchedules = FXCollections.observableArrayList(list);
        scheduleTable.setItems(availableSchedules);
    }

    private void populateSelectedScheduleDetails(NewExamScheduleDTO schedule) {
        maLichThiMoiTextField.setText(String.valueOf(schedule.getMaLichThi()));
        ngayThiMoiTextField.setText(schedule.getNgayThi().format(dateFormatter));
        gioThiMoiTextField.setText(schedule.getGioThi().format(timeFormatter));
        diaChiMoiTextField.setText(schedule.getDiaDiemThi());
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        LocalDate from = fromDatePicker.getValue();
        LocalDate to = toDatePicker.getValue();

        List<NewExamScheduleDTO> filtered = scheduleBUS.searchSchedules(from, to);
        scheduleTable.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/group2/hcmus/exammanagementsystem/AccountingStaff/ExtensionTicket.fxml"));
            Parent root = loader.load();

            ExtensionTicketController controller = loader.getController();
            controller.setExamCard(examCard);

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gia hạn phiếu dự thi");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể quay lại: " + e.getMessage());
        }
    }

    @FXML
    private void handleNext(ActionEvent event) {
        NewExamScheduleDTO selected = scheduleTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Chọn lịch thi", "Vui lòng chọn một lịch thi mới.");
            return;
        }

        boolean success = scheduleBUS.assignScheduleToExamCard(examCard.getMaPhieuDuThi(), selected.getMaLichThi());

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Phiếu dự thi đã được cập nhật.");
            ((Stage) nextButton.getScene().getWindow()).close();
        } else {
            showAlert(Alert.AlertType.ERROR, "Thất bại", "Cập nhật thất bại. Vui lòng thử lại.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
