package com.group2.hcmus.exammanagementsystem.controller.Receptionist.FreeRegistration;

import com.group2.hcmus.exammanagementsystem.DTO.FreeRegistrationDTO;
import com.group2.hcmus.exammanagementsystem.DTO.ScheduleListDTO;
import com.group2.hcmus.exammanagementsystem.BUS.FreeRegistrationBUS;
import com.group2.hcmus.exammanagementsystem.BUS.ScheduleListBUS;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.StackPane;

import com.group2.hcmus.exammanagementsystem.utils.SupportingUtilities;
import java.sql.Timestamp;
import java.util.List;

public class Step3Controller {
    @FXML private CheckBox ngoaingu;
    @FXML private CheckBox tinhoc;
    @FXML private TableView<ScheduleListDTO> scheduleTable;
    @FXML private TableColumn<ScheduleListDTO, String> diadiemthi;
    @FXML private TableColumn<ScheduleListDTO, Timestamp> ngaygiothi;
    @FXML private TableColumn<ScheduleListDTO, Integer> phongthi;
    @FXML private TableColumn<ScheduleListDTO, Integer> sl_dangky;
    @FXML private TableColumn<ScheduleListDTO, Integer> sl_toida;
    @FXML private TableColumn<ScheduleListDTO, Boolean> luachon;
    @FXML private Button btnBack;
    @FXML private Button btnConfirm;
    private StackPane mainContainer; // shared StackPane for all steps


    private ScheduleListDTO selectedSchedule = null;
    private static ScheduleListBUS scheduleListBUS = new ScheduleListBUS();
    private static FreeRegistrationDTO dto = new FreeRegistrationDTO();
    private static FreeRegistrationBUS bus = new FreeRegistrationBUS();

    public void setRegistration(FreeRegistrationDTO dto) {
        this.dto = dto;
    }

    public void setMainContainer(StackPane mainContainer) {
        this.mainContainer = mainContainer;
    }

    @FXML
    public void initialize() {
        // tick column
        scheduleTable.setEditable(true);
        luachon.setEditable(true);
        // check box filtering
        ngoaingu.setOnAction(this::onFiltered);
        tinhoc.setOnAction(this::onFiltered);
        // Set up table columns
        diadiemthi.setCellValueFactory(cellData -> cellData.getValue().diaDiemThiProperty());
        ngaygiothi.setCellValueFactory(cellData -> cellData.getValue().ngayGioProperty());
        phongthi.setCellValueFactory(cellData -> cellData.getValue().maPhongThiProperty().asObject());
        sl_dangky.setCellValueFactory(cellData -> cellData.getValue().slDangKyProperty().asObject());
        sl_toida.setCellValueFactory(cellData -> cellData.getValue().slDangKyProperty().asObject());

        // load data
        loadScheduleData();

        // set up checkbox column
        luachon.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        luachon.setCellFactory(tc -> {
            CheckBoxTableCell<ScheduleListDTO, Boolean> cell = new CheckBoxTableCell<>(index -> {
                BooleanProperty selected = scheduleTable.getItems().get(index).selectedProperty();
                selected.addListener((obs, wasSelected, isNowSelected) -> {
                    if (isNowSelected) {
                        // unselect the non-selected one
                        for (ScheduleListDTO item : scheduleTable.getItems()) {
                            if (item != scheduleTable.getItems().get(index)) {
                                item.setSelected(false);
                            }
                        }
                        selectedSchedule = scheduleTable.getItems().get(index);
                    } else {
                        if (selectedSchedule == scheduleTable.getItems().get(index)) {
                            selectedSchedule = null;
                        }
                    }
                });
                return selected;
            });
            return cell;
        });
    }

    private void loadScheduleData() {
        List<ScheduleListDTO> scheduleList = scheduleListBUS.getAllSchedules();
        scheduleTable.getItems().setAll(scheduleList);
    }

    private void onFiltered(ActionEvent event) {
        filterScheduleData();
    }

    private void filterScheduleData() {
        boolean isNgoaiNguSelected = ngoaingu.isSelected();
        boolean isTinHocSelected = tinhoc.isSelected();

        List<ScheduleListDTO> filteredSchedules = scheduleListBUS.getFilteredSchedules(isNgoaiNguSelected, isTinHocSelected);

        scheduleTable.getItems().setAll(filteredSchedules);
    }
    @FXML
    void onConfirm(ActionEvent event) {
        if (selectedSchedule == null) {

            return;
        }
        System.out.println("Schedule selected: " + selectedSchedule.getDiaDiemThi() + selectedSchedule.getNgayGio());

        dto.setSchedule(selectedSchedule);

        SupportingUtilities sp = new SupportingUtilities();
        boolean success = bus.registerCustomerAndForm(dto, selectedSchedule);
        if (success) {
            sp.showAlert("Registration successful!");
            // Navigate or reset form
        } else {
            sp.showAlert("An error occurred during registration.");
        }
    }

    @FXML
    void onPrevious(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/group2/hcmus/exammanagementsystem/Receptionist/FreeRegistration/Step2.fxml"));
            Parent step1Root = loader.load();

            Step2Controller step2Controller = loader.getController();
            step2Controller.setMainContainer(mainContainer);
            step2Controller.setRegistration(dto);
            mainContainer.getChildren().setAll(step1Root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
