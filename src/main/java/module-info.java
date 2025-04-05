module com.group2.hcmus.exammanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;

    opens com.group2.hcmus.exammanagementsystem to javafx.fxml;
    exports com.group2.hcmus.exammanagementsystem;
}