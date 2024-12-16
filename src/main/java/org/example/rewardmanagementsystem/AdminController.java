package org.example.rewardmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private ChoiceBox<String> addAcademicPerformance;

    @FXML
    private Button addAdd;

    @FXML
    private Button addBtn;

    @FXML
    private TextField addChildID;

    @FXML
    private TextField addChildName;

    @FXML
    private TextField addClass;

    @FXML
    private Button addClear;

    @FXML
    private TableColumn<?, ?> addColAcademicPerformance;

    @FXML
    private TableColumn<?, ?> addColChildID;

    @FXML
    private TableColumn<?, ?> addColChildName;

    @FXML
    private TableColumn<?, ?> addColClass;

    @FXML
    private TableColumn<?, ?> addColDOB;

    @FXML
    private TableColumn<?, ?> addColHouseholdID;

    @FXML
    private TableColumn<?, ?> addColSchool;

    @FXML
    private TextField addDOB;

    @FXML
    private Button addDelete;

    @FXML
    private AnchorPane addForm;

    @FXML
    private TextField addHouseholdID;

    @FXML
    private TextField addSchool;

    @FXML
    private TableView<ChildrenData> addTableView;

    @FXML
    private Button addUpdate;

    @FXML
    private Button homeBtn;

    @FXML
    private AnchorPane homeForm;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button rewardBtn;

    @FXML
    private TextField rewardChildID;

    @FXML
    private Button rewardClearBtn;

    @FXML
    private TableColumn<?, ?> rewardColChildID;

    @FXML
    private TableColumn<?, ?> rewardColChildName;

    @FXML
    private TableColumn<?, ?> rewardColHouseholdID;

    @FXML
    private TableColumn<?, ?> rewardColQuantity;

    @FXML
    private TableColumn<?, ?> rewardColReward;

    @FXML
    private TableColumn<?, ?> rewardColValue;

    @FXML
    private AnchorPane rewardForm;

    @FXML
    private TextField rewardHouseholdID;

    @FXML
    private TextField rewardChildName;

    @FXML
    private TextField rewardQuantity;

    @FXML
    private TextField rewardReward;

    @FXML
    private TableView<ChildrenData> rewardTableView;

    @FXML
    private Button rewardUpdateBtn;

    @FXML
    private TextField rewardValue;

    @FXML
    private TextField search;

    @FXML
    private Label userName;

    @FXML
    private TextField addSearch;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAcademicPerformance.getItems().add("Excellent");
        addAcademicPerformance.getItems().add("Good");
        addChildrenShowListData();
        try {
            rewardChildrenShowListData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet resultSet;
    private Alert alert;

//Phuong thuc logout
    public void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");
        Optional<ButtonType> result = alert.showAndWait();

        try {
            if (result.get().equals(ButtonType.OK)) {
                //Quay ve sign in sign up dien sau
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Hien ten username
    public void displayUsername(){
        userName.setText(GetData.username);
    }

    //Lay du lieu co san tu table child
    public ObservableList<ChildrenData> addChildrenListData(){
        ObservableList<ChildrenData> listData = FXCollections.observableArrayList();
        String sql = "Select * from child";
        connect = database.connectDb();
        try{
            prepare = connect.prepareStatement(sql);
            resultSet = prepare.executeQuery();
            ChildrenData childrenD;

            while (resultSet.next()) {
                childrenD = new ChildrenData(resultSet.getString("ChildID"),
                        resultSet.getString("Name"),
                        resultSet.getDate("DOB"),
                        resultSet.getString("School"),
                        resultSet.getString("Class"),
                        resultSet.getString("AcademicPerformance"),
                        resultSet.getString("HouseholdID"));
                listData.add(childrenD);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return listData;
    }

    public void addChildrenSelect(){
        ChildrenData childrenDa = (ChildrenData) addTableView.getSelectionModel().getSelectedItem();
        int num = addTableView.getSelectionModel().getSelectedIndex();
        if((num - 1) < -1){
            return;
        }

        addColChildID.setText(String.valueOf(childrenDa.getChildID()));
        addColChildName.setText(String.valueOf(childrenDa.getChildName()));
        addColClass.setText(String.valueOf(childrenDa.getChildClass()));
        addColDOB.setText(String.valueOf(childrenDa.getChildDOB()));
        addColHouseholdID.setText(String.valueOf(childrenDa.getHouseholdID()));
        addColSchool.setText(String.valueOf(childrenDa.getChildSchool()));
        addColAcademicPerformance.setText(String.valueOf(childrenDa.getAcademicPerformance()));

    }

    //Them tre nho vao database
    public void addChildrenAdd(){
        String sql = "INSERT INTO child VALUES(?,?,?,?,?,?,?)";
        connect = database.connectDb();

        try{
            if(addChildID.getText().isEmpty()
            || addChildName.getText().isEmpty()
                || addClass.getText().isEmpty()
                || addDOB.getText().isEmpty()
                || addHouseholdID.getText().isEmpty()
                || addSchool.getText().isEmpty()
                ||addAcademicPerformance.getValue().equals(""))
            //|| GetData.path == null || GetData.path == "")
                {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the fields correctly.");
                alert.showAndWait();
            }
            else {
                String check = "Select ChildID from child Where ChildID= " + "'" + addChildID.getText() + "'";
                statement = connect.createStatement();
                resultSet = statement.executeQuery(check);
                if (resultSet.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("ChildID: " + addChildID.getText() + " already exists.");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1,  addChildID.getText());
                    prepare.setString(2, addChildName.getText() );
                    prepare.setString(3,  addDOB.getText() );
                    prepare.setString(4,  addSchool.getText() );
                    prepare.setString(5,  addClass.getText() );
                    prepare.setString(6, addAcademicPerformance.getValue().toString());
                    prepare.setString(7,  addHouseholdID.getText() );

                    /*String uri = GetData.path;
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(8, uri);
                    prepare.setString(9, String.valueOf(sqlDate.getTime()));
                     */
                    prepare.executeUpdate();


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added child.");
                    alert.showAndWait();
                    addChildrenShowListData();
                    addChildReset();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    //reset thong tin da nhap
    public void addChildReset(){
        addChildID.setText("");
        addChildName.setText("");
        addClass.setText("");
        addDOB.setText("");
        addHouseholdID.setText("");
        addSchool.setText("");
        addAcademicPerformance.setValue("");
        GetData.path = "";

    }

    //Update thong tin tre nho
    public void addChildUpdate(){
        String sql = "UPDATE child SET Name = " + "'" + addChildName.getText() + "',"
                + "Class= " + "'" + addClass.getText() + "',"
                + "DOB= " + "'" + addDOB.getText() + "',"
                + "HouseholdID = " + "'" + addHouseholdID.getText() + "',"
                + "School= " + "'" + addSchool.getText() + "',"
                + "AcademicPerformance= " + "'" + addAcademicPerformance.getValue() + "'"
                + "WHERE ChildID = " + "'" + addChildID.getText() + "';";

        connect = database.connectDb();

        try{
            if(addChildID.getText().isEmpty()
                    || addChildName.getText().isEmpty()
                    || addClass.getText().isEmpty()
                    || addDOB.getText().isEmpty()
                    || addHouseholdID.getText().isEmpty()
                    || addSchool.getText().isEmpty()
                    || addAcademicPerformance.getValue().equals("")
                     )
            //|| GetData.path == null || GetData.path == "")
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the fields correctly.");
                alert.showAndWait();
            }
            else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update this child?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    statement = connect.prepareStatement(sql);
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully update child.");
                    alert.showAndWait();

                    addChildrenShowListData();
                    addChildReset();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //Delete thong tin tre nho
    public void addChildDelete(){
        String sql = "DELETE FROM child WHERE ChildID = " + "'" + addChildID.getText() + "';";
        connect = database.connectDb();
        try{
            if(addChildID.getText().isEmpty()
                    || addChildName.getText().isEmpty()
                    || addClass.getText().isEmpty()
                    || addDOB.getText().isEmpty()
                    || addHouseholdID.getText().isEmpty()
                    || addSchool.getText().isEmpty()
                     || addAcademicPerformance.getValue().equals(""))
            //|| GetData.path == null || GetData.path == "")
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the fields correctly.");
                alert.showAndWait();
            }
            else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this child's information?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == (ButtonType.OK)) {
                    statement = connect.prepareStatement(sql);
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully deleted child.");

                    addChildrenShowListData();
                    addChildReset();
                }

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    //
    private ObservableList<ChildrenData> addChildList;
    public void addChildrenShowListData(){
        addChildList = addChildrenListData();
        addColChildID.setCellValueFactory(new PropertyValueFactory<>("childID"));
        addColChildName.setCellValueFactory(new PropertyValueFactory<>("childName"));
        addColDOB.setCellValueFactory(new PropertyValueFactory<>("childDOB"));
        addColHouseholdID.setCellValueFactory(new PropertyValueFactory<>("HouseholdID"));
        addColSchool.setCellValueFactory(new PropertyValueFactory<>("childSchool"));
        addColClass.setCellValueFactory(new PropertyValueFactory<>("childClass"));
        addColAcademicPerformance.setCellValueFactory(new PropertyValueFactory<>("AcademicPerformance"));

        addTableView.setItems(addChildList);
    }

    //Phuong thuc tim kiem thong tin tre em(dang loi~)
public void addChildrenSearch(){
    FilteredList<ChildrenData> filteredList = new FilteredList<>(addChildList, e-> true);
    addSearch.textProperty().addListener((Observable, oldValue, newValue) -> {
        filteredList.setPredicate(predicateData ->{
            if(newValue == null || newValue.isEmpty()){
            return false;}

            String searchKey = newValue;
            if(predicateData.getChildID().toLowerCase().contains(searchKey)) {
                return true;
            }
             else if(predicateData.getChildName().toLowerCase().contains(searchKey)) {
                 return true;
             }
             else if(predicateData.getChildClass().toLowerCase().contains(searchKey)) {
                 return true;
             }
             else if(predicateData.getHouseholdID().toLowerCase().contains(searchKey)) {
                 return true;
             }
             else if(predicateData.getChildSchool().toLowerCase().contains(searchKey)) {
                 return true;
             }
             else if(predicateData.getChildDOB().toString().toLowerCase().contains(searchKey)){
                 return true;
             }
             else if(predicateData.getAcademicPerformance().toLowerCase().contains(searchKey)) {
                 return true;
             }
             else return false;
        });
    });

    SortedList<ChildrenData> sortedList = new SortedList<>(filteredList);
    sortedList.comparatorProperty().bind(addTableView.comparatorProperty());
    addTableView.setItems(sortedList);

}
    //Phuong thuc chon form home, addchild, reward
    public void switchForm(ActionEvent event){
        if(event.getSource() == homeBtn){
            homeForm.setVisible(true);
            addForm.setVisible(false);
            rewardForm.setVisible(false);
        }
        else if(event.getSource() == addBtn){
            homeForm.setVisible(false);
            addForm.setVisible(true);
            rewardForm.setVisible(false);
            addChildrenSearch();
        }
        else if(event.getSource() == rewardBtn){
            homeForm.setVisible(false);
            addForm.setVisible(false);
            rewardForm.setVisible(true);
        }
    }


    //Tuong tac voi reward Allocation
    public ObservableList<ChildrenData> rewardChildrenList() {
        ObservableList<ChildrenData> childrenList = FXCollections.observableArrayList();
        String sql = "Select c.ChildID, c.Name, c.HouseholdID, rwf.RewardName, rwt.Quantity, rwt.TotalOfReward from child c\n" +
                "left join rewardtype rwt on c.AcademicPerformance = rwt.AcademicPerformance\n" +
                "left join rewardfund rwf on rwt.RewardID = rwf.RewardID";
        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            resultSet = prepare.executeQuery();
            ChildrenData childrenR;
            while (resultSet.next()) {
                childrenR = new ChildrenData(resultSet.getString("ChildID"),
                        resultSet.getString("Name"),
                        resultSet.getString("HouseholdID"),
                        resultSet.getString("RewardName"),
                        resultSet.getInt("Quantity"),
                        resultSet.getInt("TotalOfReward"));
                childrenList.add(childrenR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return childrenList;
    }

    private ObservableList<ChildrenData> rewardUpdateList = FXCollections.observableArrayList();

    public ObservableList<ChildrenData> rewardUpdateListData() throws SQLException {
        if (rewardChildID.getText().isEmpty()
                || rewardChildName.getText().isEmpty()
                || rewardHouseholdID.getText().isEmpty()
                || rewardReward.getText().isEmpty()
                || rewardQuantity.getText().isEmpty()
                || rewardValue.getText().isEmpty()) {
            return null;
        } else {
            rewardUpdateList.add(new ChildrenData(rewardChildID.getText(), rewardChildName.getText(), rewardHouseholdID.getText(), rewardReward.getText(), Integer.parseInt(rewardQuantity.getText()), Integer.parseInt(rewardValue.getText())));
            String sql = "Insert into bonusreward (ChildID, ChildName, HouseholdID, RewardName, Quantity, Value) values(?,?,?,?,?,?)";
            connect = database.connectDb();
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, rewardChildID.getText());
            prepare.setString(2, rewardChildName.getText());
            prepare.setString(3, rewardHouseholdID.getText());
            prepare.setString(4, rewardReward.getText());
            prepare.setInt(5, Integer.parseInt(rewardQuantity.getText()));
            prepare.setInt(6, Integer.parseInt(rewardValue.getText()));
            prepare.executeUpdate();

            return rewardUpdateList;
        }
    }


    private ObservableList<ChildrenData> rewardList, rewardList2 = FXCollections.observableArrayList();
    public void rewardChildrenShowListData() throws SQLException {
        ObservableList<ChildrenData> combineList = FXCollections.observableArrayList();
        rewardList = rewardChildrenList();
        combineList.addAll(rewardList);

        String sql = "Select * from bonusreward";
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String childID = rs.getString("ChildID");
            String childName = rs.getString("ChildName");
            String householdID = rs.getString("HouseholdID");
            String rewardName = rs.getString("RewardName");
            int quantity = rs.getInt("Quantity");
            int totalOfReward = rs.getInt("Value");

                rewardList2.add(new ChildrenData(childID, childName, householdID, rewardName, quantity, totalOfReward));
        }
        combineList.addAll(rewardList2);
        rewardColChildID.setCellValueFactory(new PropertyValueFactory<>("childID"));
        rewardColChildName.setCellValueFactory(new PropertyValueFactory<>("childName"));
        rewardColHouseholdID.setCellValueFactory(new PropertyValueFactory<>("householdID"));
        rewardColReward.setCellValueFactory(new PropertyValueFactory<>("reward"));
        rewardColQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        rewardColValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        rewardTableView.setItems(combineList);

        rewardTableView.sort();


    }
    public void rewardUpdate(ActionEvent event) throws SQLException {
        if(rewardChildID.getText().isEmpty()
                ||rewardChildName.getText().isEmpty()
                ||rewardHouseholdID.getText().isEmpty()
                ||rewardReward.getText().isEmpty()
                ||rewardQuantity.getText().isEmpty()
                ||rewardValue.getText().isEmpty())
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the fields correctly.");
            alert.showAndWait();
        }

        else{
            rewardUpdateListData();
            rewardChildrenShowListData();
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Complete!");
            alert.setHeaderText(null);
            alert.setContentText("Successfully updated child.");
            alert.showAndWait();
        }
    }
    //reset bang reward
    public void rewardReset(ActionEvent event) throws SQLException {
        rewardChildrenShowListData();
    }

    //clear bang reward
    public void rewardClear(ActionEvent event){
        rewardChildID.setText("");
        rewardChildName.setText("");
        rewardHouseholdID.setText("");
        rewardReward.setText("");
        rewardQuantity.setText("");
        rewardValue.setText("");
    }
}
