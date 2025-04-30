module com.group2.hcmus.exammanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires com.dlsc.formsfx;
    requires io.github.cdimascio.dotenv.java;

    opens com.group2.hcmus.exammanagementsystem to javafx.fxml;
    exports com.group2.hcmus.exammanagementsystem;

    opens com.group2.hcmus.exammanagementsystem.controller.LoginAndSignup to javafx.fxml;
    exports com.group2.hcmus.exammanagementsystem.controller.LoginAndSignup;

    opens com.group2.hcmus.exammanagementsystem.controller.KHACH_HANG.TU_DO to javafx.fxml;
    exports com.group2.hcmus.exammanagementsystem.controller.KHACH_HANG.TU_DO;
}