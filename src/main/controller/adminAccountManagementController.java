package main.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Datastore;
import main.model.adminAccountManagementModel;
import main.model.employeeObj;
import main.model.util;

import java.sql.SQLException;

public class adminAccountManagementController {
    main.model.adminAccountManagementModel adminAccountManagementModel = new adminAccountManagementModel();

    @FXML
    private TableView<employeeObj> table;
    @FXML
    private TableColumn<employeeObj, Integer> employeeID;
    @FXML
    private TableColumn<employeeObj, String> firstName;
    @FXML
    private TableColumn<employeeObj, String> lastName;
    @FXML
    private TableColumn<employeeObj, String> role;
    @FXML
    private TableColumn<employeeObj, String> username;
    @FXML
    private TableColumn<employeeObj, String> accountStatus;
    @FXML
    private TableColumn<employeeObj, String> lastTableBooked;
    @FXML
    private Button refreshBtn, updateDetailsBtn, deleteUserBtn, addUserBtn, backBtn;

    @FXML
    private void initialize(){
        ObservableList<employeeObj> items = adminAccountManagementModel.getEmployees();
        table.setItems(items);
        employeeID.setCellValueFactory(new PropertyValueFactory<employeeObj,Integer>("employeeID"));
        firstName.setCellValueFactory(new PropertyValueFactory<employeeObj,String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<employeeObj,String>("lastName"));
        role.setCellValueFactory(new PropertyValueFactory<employeeObj,String>("Role"));
        username.setCellValueFactory(new PropertyValueFactory<employeeObj,String>("username"));
        accountStatus.setCellValueFactory(new PropertyValueFactory<employeeObj,String>("accountStatus"));
        lastTableBooked.setCellValueFactory(new PropertyValueFactory<employeeObj,String>("lastTableBooked"));
    }

    public void handleDeleteUserBtn() throws SQLException {
        if (table.getSelectionModel().getSelectedItem() != null){
            int employeeID = table.getSelectionModel().getSelectedItem().getEmployeeID();
            adminAccountManagementModel.deleteUser(employeeID);
            initialize();
        }
        else {
            String title = "Delete User";
            String issue = "ERROR: No user has been selected";
            String instructions = "Please click and select a user from the table before continuing";
            util.errorAlert(title, issue, instructions);
        }
    }

    public void handleAddUserBtn(){
        String page = "main/ui/adminAddUserPopup.fxml";
        util.displayPopup(addUserBtn,page,500,600);
        initialize();
    }

    // handle back btn to go from Admin Acc Management back to Admin Menu
    public void handleBackBtn(){
        String page = "main/ui/adminMenu.fxml";
        util.changeScenes(backBtn, page);
    }

    public void handleUpdateDetailsBtn(){
        if(table.getSelectionModel().getSelectedItem() != null){
            int employeeID = table.getSelectionModel().getSelectedItem().getEmployeeID();
            Datastore.getInstance().setEmployeeIDUpdateUser(employeeID);
            String page = "main/ui/adminUpdateUserPopup.fxml";
            util.displayPopup(updateDetailsBtn, page,500,600);
            initialize();
        }
        else {
            String title = "Update Details";
            String issue = "ERROR: No user has been selected";
            String instructions = "Please click and select a user from the table before continuing";
            util.errorAlert(title, issue, instructions);
        }
    }

}


