package org.example.rewardmanagementsystem.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.rewardmanagementsystem.Model.*;
public class ForgetPasswordController {
    @FXML
    private TextField forgetHouseholdID;

    @FXML
    private TextField forgetUserName;

    @FXML
    private Button verifyButton;

    @FXML
    private Button Confirm;
    @FXML
    private TextField forgetNewPassword;

    @FXML
    private TextField forgetReEnter;


    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet rs;

    public void verify(ActionEvent event) throws SQLException {
        String sql = "Select count(*) from account where HouseholdId = ? and Account = ?";
        connect = database.connectDb();
        prepare = connect.prepareStatement(sql);
        prepare.setString(1, forgetHouseholdID.getText());
        prepare.setString(2, forgetUserName.getText());

        rs = prepare.executeQuery();
        if (rs.next()){
            int count = rs.getInt(1);
            if (count > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("You have successfully verified your password.");
                alert.showAndWait();


                forgetHouseholdID.setVisible(false);
                forgetUserName.setVisible(false);
                verifyButton.setVisible(false);

                forgetNewPassword.setVisible(true);
                forgetReEnter.setVisible(true);
                Confirm.setVisible(true);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Incorrect Household ID or Username, try again.");
                alert.showAndWait();
            }
        }
    }

    public void confirm(ActionEvent event) throws SQLException {
        if (forgetHouseholdID.getText().isEmpty() || forgetReEnter.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
        }
        else if (!forgetNewPassword.getText().equals(forgetReEnter.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match.");
            alert.showAndWait();
        } else {
            String sql = "Update account set Password = ? where HouseholdId = ? and Account = ?";
            connect = database.connectDb();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, forgetNewPassword.getText());
            prepare.setString(2, forgetHouseholdID.getText());
            prepare.setString(3, forgetUserName.getText());
            prepare.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("You have successfully verified your password.");
            alert.showAndWait();

            Confirm.getScene().getWindow().hide();
        }
    }
}
