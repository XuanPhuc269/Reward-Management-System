module com.example.rma {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.Main.rma to javafx.fxml;
    exports com.Main.rma;
}