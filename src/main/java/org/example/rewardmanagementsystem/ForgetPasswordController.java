package org.example.rewardmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgetPasswordController {
    @FXML
    private TextField forgetHouseholdID;

    @FXML
    private TextField forgetUserName;

    @FXML
    private Button verifyButton;

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
        if (rs.next()) {
            String sql2 = "Update account Set password = '1' where account = ?";
            connect = database.connectDb();
            prepare = connect.prepareStatement(sql2);
            prepare.setString(1, forgetUserName.getText());
            int check = prepare.executeUpdate();
            if (check > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Password verified");
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update password");
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to verify your password");
            alert.showAndWait();
        }
    }
}
