package org.example.rewardmanagementsystem.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.example.rewardmanagementsystem.Model.*;

public class SignInController implements Initializable {

    @FXML
    private Label forgetLabel;

    @FXML
    private Button forgetBtn;
    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private TextField userNameTF;

    @FXML
    private Label welcome_back_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet rs;


    public void logIn(ActionEvent actionEvent) {
        if(userNameTF.getText().isEmpty() || passwordTF.getText().isEmpty()) {
            userNameTF.clear();
            passwordTF.clear();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();

        }
        else{
            int count = 0;
            String sql ="select count(*) from account where Account=? and Password=?";
            connect = database.connectDb();

            try{
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, userNameTF.getText());
                prepare.setString(2, passwordTF.getText());

                rs = prepare.executeQuery();

                if(rs.next()) {
                    count = rs.getInt(1);
                }

                if(count == 1) {
                    String sql2 = "select householdID from account where Account=?";
                    connect = database.connectDb();
                    try{
                        prepare = connect.prepareStatement(sql2);
                        prepare.setString(1, userNameTF.getText());
                        rs = prepare.executeQuery();
                        if(rs.next()) {
                            String householdID = rs.getString(1);
                            GetData.username = userNameTF.getText();
                            if(householdID.equals("HH00")) {
                                Parent root = FXMLLoader.load(getClass().getResource("/FxmlFile/AdminScene.fxml"));
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.show();
                                loginBtn.getScene().getWindow().hide();
                            }

                            //scene cua phu huynh
                            else{
                                Parent root = FXMLLoader.load(getClass().getResource("/FxmlFile/ParentScene.fxml"));
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.show();
                                loginBtn.getScene().getWindow().hide();
                            }
                        }
                    }
                    catch(SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Password");
                    alert.showAndWait();
                    userNameTF.clear();
                    passwordTF.clear();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void forget(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FxmlFile/ForgetPasswordScene.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
