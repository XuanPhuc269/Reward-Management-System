package org.example.rewardmanagementsystem.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import org.example.rewardmanagementsystem.Model.*;
public class ParentController implements Initializable {

    @FXML
    private Label parentChildAC;

    @FXML
    private Label parentChildClass;

    @FXML
    private Label parentChildDOB;

    @FXML
    private Label parentChildName;

    @FXML
    private Label parentChildSchool;

    @FXML
    private Label parentTotalPresent;

    @FXML
    private Label parentTotalValue;

    @FXML
    private AnchorPane parentForm;
    @FXML
    private Label userName;

    @FXML
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet resultSet;
    public ArrayList<ChildrenData> list = new ArrayList<>();



    //Hien thi thong tin tre
    /*public int getParent(String value) throws SQLException {
        String sql = "Select count(*) from child where HouseholdID= ?";
        connect = database.connectDb();
        prepare = connect.prepareStatement(sql);
        prepare.setString(1, value);
        resultSet = prepare.executeQuery();
        return resultSet.getInt(1);
    }

     */
    public void displayUsername(){
        userName.setText(GetData.username);
    }

    public String hholdID;
    public void getParentHouseholdID(String value) throws SQLException{
        String sql = "Select HouseholdID from account where Account = ?";
        connect = database.connectDb();
        prepare = connect.prepareStatement(sql);
        prepare.setString(1, value);
        resultSet = prepare.executeQuery();
        if(resultSet.next()){
            hholdID = resultSet.getString(1);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        displayUsername();
        try {
            getParentHouseholdID(userName.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            sumPresent();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            totalValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            information();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void information() throws SQLException {
        String sql = "SELECT * FROM child WHERE HouseholdID = ?";
        connect = database.connectDb();
        prepare = connect.prepareStatement(sql);
        prepare.setString(1, hholdID);  // Thay thế ? với HouseholdID
        resultSet = prepare.executeQuery();

        while(resultSet.next()){
            list.add(new ChildrenData(resultSet.getString("Name"), resultSet.getString("DOB"), resultSet.getString("School"), resultSet.getString("Class"), resultSet.getString("AcademicPerformance")));
        }


    }
    //gan actionevent
    public int k = 0;
    public void showList(ActionEvent event) throws SQLException {
        if(k < list.size() && k >= 0) {
            parentChildName.setText(list.get(k).getChildName());
            parentChildClass.setText(list.get(k).getChildClass());
            parentChildSchool.setText(list.get(k).getChildSchool());
            parentChildDOB.setText(list.get(k).getChildDOB());
            parentChildAC.setText(list.get(k).getAcademicPerformance());
            k++;
        }
        else{
            k = 0;
            parentChildName.setText(list.get(k).getChildName());
            parentChildClass.setText(list.get(k).getChildClass());
            parentChildSchool.setText(list.get(k).getChildSchool());
            parentChildDOB.setText(list.get(k).getChildDOB());
            parentChildAC.setText(list.get(k).getAcademicPerformance());
            k++;
        }


    }
    public void showList2(ActionEvent event) throws SQLException {
        if (k >= 0 && k < list.size()) {
            parentChildName.setText(list.get(k).getChildName());
            parentChildClass.setText(list.get(k).getChildClass());
            parentChildSchool.setText(list.get(k).getChildSchool());
            parentChildDOB.setText(list.get(k).getChildDOB());
            parentChildAC.setText(list.get(k).getAcademicPerformance());
            k--;
        }
        else{
            k = list.size() - 1;
            parentChildName.setText(list.get(k).getChildName());
            parentChildClass.setText(list.get(k).getChildClass());
            parentChildSchool.setText(list.get(k).getChildSchool());
            parentChildDOB.setText(list.get(k).getChildDOB());
            parentChildAC.setText(list.get(k).getAcademicPerformance());
            k--;
        }

    }


    public void sumPresent() throws SQLException {
        String sql = "Select count(*) from child where HouseholdID = ? and (AcademicPerformance = ? or AcademicPerformance = ?)";
        connect = database.connectDb();
        prepare = connect.prepareStatement(sql);
        prepare.setString(1, hholdID);
        prepare.setString(2, "Excellent");
        prepare.setString(3, "Good");
        resultSet = prepare.executeQuery();
        if(resultSet.next()){
            parentTotalPresent.setText(resultSet.getString(1));
        }
    }


    //Log Out
    public void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");
        Optional<ButtonType> result = alert.showAndWait();

        try {
            if (result.get().equals(ButtonType.OK)) {
                //Quay ve sign in sign up dien sau
                Parent root = FXMLLoader.load(getClass().getResource("/FxmlFile/WelcomePageScene.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                Stage currentStage = (Stage) parentForm.getScene().getWindow();
                currentStage.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Setting
    public void setting(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Feature under development");
        alert.showAndWait();
    }

    // Total value
    public void totalValue() throws SQLException {
        int[] arrayOfEachPresent = new int[3];
        for(int i = 0; i < 3; i++){
            arrayOfEachPresent[i] = 0;
        }

        //Tinh so tien hoc sinh nhan duoc
        String sql =  "Select count(*) from child where HouseholdID = ? and AcademicPerformance = ?";
        connect = database.connectDb();
        prepare = connect.prepareStatement(sql);
        prepare.setString(1, hholdID);
        prepare.setString(2, "Excellent");
        resultSet = prepare.executeQuery();
        if(resultSet.next()){
            arrayOfEachPresent[0] += resultSet.getInt(1) * 1150000;
        }
        prepare = connect.prepareStatement(sql);
        prepare.setString(1, hholdID);
        prepare.setString(2, "Good");
        resultSet = prepare.executeQuery();
        if(resultSet.next()){
            arrayOfEachPresent[1] += resultSet.getInt(1) * 575000;
        }

        int sum = 0;
        for(int i = 0; i < 3; i++){
            sum += arrayOfEachPresent[i];
        }

        //Tinh so tien hoc sinh duoc thuong them

        String sql2 = "Select (Quantity * Value) from bonusreward where HouseholdID = ?";
        connect = database.connectDb();
        prepare = connect.prepareStatement(sql2);
        prepare.setString(1, hholdID);
        resultSet = prepare.executeQuery();
        while(resultSet.next()){
            sum += resultSet.getInt(1);
        }
        NumberFormat formatter = NumberFormat.getInstance(new Locale("en", "US"));
        String formattedSum = formatter.format(sum);

        // Hiển thị số đã định dạng
        parentTotalValue.setText(formattedSum);
    }
}
