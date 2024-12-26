module org.example.rewardmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens org.example.rewardmanagementsystem.Controller to javafx.fxml;
    opens org.example.rewardmanagementsystem.Model to javafx.base;
    exports org.example.rewardmanagementsystem.View;
}