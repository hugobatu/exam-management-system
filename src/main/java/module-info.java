module com.group2.hcmus.exammanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.group2.hcmus.exammanagementsystem to javafx.fxml;
    exports com.group2.hcmus.exammanagementsystem;
}