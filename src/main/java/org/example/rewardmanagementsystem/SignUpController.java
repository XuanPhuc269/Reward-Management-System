package org.example.rewardmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Label create_an_account_label;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField signUpHHID;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private PasswordField signUpReEnter;

    @FXML
    private TextField signUpUserName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet rs;

    public void signUp(ActionEvent actionEvent) throws SQLException {

        String userName = signUpUserName.getText();
        String sql2 = "Select * from account where Account = ?";
        connect = database.connectDb();
        prepare = connect.prepareStatement(sql2);
        prepare.setString(1, userName);
        rs = prepare.executeQuery();
        //oke
        if(signUpUserName.getText().isEmpty() || signUpPassword.getText().isEmpty() || signUpReEnter.getText().isEmpty() || signUpHHID.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
            signUpUserName.clear();
            signUpPassword.clear();
            signUpReEnter.clear();
            signUpHHID.clear();
        }
        else if(signUpHHID.equals("HH00")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Cannot create account with this HouseholdID");
        }

        //oke
        else if (rs.next()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Username already exists");
            alert.showAndWait();
            signUpUserName.clear();
            signUpHHID.clear();
            signUpPassword.clear();
            signUpReEnter.clear();
        }


        else if(signUpPassword.getText() != signUpReEnter.getText()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match");
            alert.showAndWait();
        }
            else {
            String sql = "insert into account values(?,?,?)";
            connect = database.connectDb();

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, signUpUserName.getText());
            prepare.setString(2, signUpPassword.getText());
            prepare.setString(3, signUpHHID.getText());
            int row = prepare.executeUpdate();
            if (row > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Account created successfully");
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to create account");
                alert.showAndWait();
                signUpHHID.clear();
                signUpPassword.clear();
                signUpReEnter.clear();
                signUpUserName.clear();
            }
        }
    }
}