module com.group2.hcmus.exammanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.dlsc.formsfx;
    requires io.github.cdimascio.dotenv.java;
    requires com.jfoenix;
    opens com.group2.hcmus.exammanagementsystem to javafx.fxml;
    exports com.group2.hcmus.exammanagementsystem;

    opens com.group2.hcmus.exammanagementsystem.controller.LoginAndSignup to javafx.fxml;
    exports com.group2.hcmus.exammanagementsystem.controller.LoginAndSignup;

    opens com.group2.hcmus.exammanagementsystem.controller to javafx.fxml;
    exports com.group2.hcmus.exammanagementsystem.controller;

    // Receptionist
//    opens com.group2.hcmus.exammanagementsystem.controller.Receptionist to javafx.fxml;
//    exports  com.group2.hcmus.exammanagementsystem.controller.Receptionist;

    exports com.group2.hcmus.exammanagementsystem.controller.Receptionist.FreeRegistration;
    opens com.group2.hcmus.exammanagementsystem.controller.Receptionist.FreeRegistration to javafx.fxml;

//    opens com.group2.hcmus.exammanagementsystem.controller.Receptionist.UnitRegistration;
//    exports com.group2.hcmus.exammanagementsystem.controller.Receptionist.UnitRegistration to javafx.fxml;

}