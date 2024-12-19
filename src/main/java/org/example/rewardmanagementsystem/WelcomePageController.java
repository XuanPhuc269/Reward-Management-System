package org.example.rewardmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomePageController {
    @FXML
    private Button signUpBtn, signInBtn;

    public void signIn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FxmlFile/LoginScene.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        signInBtn.getScene().getWindow().hide();
    }

    public void signUp(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/FxmlFile/SignUpScene.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
