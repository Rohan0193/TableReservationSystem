package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.model.Datastore;
import main.model.util;
import main.model.adminMenuModel;

import java.sql.SQLException;

public class adminMenuController {
    adminMenuModel adminMenuModel = new adminMenuModel();

    @FXML
    private Button bookingManagementBtn;
    @FXML
    private Button accountManagementBtn;
    @FXML
    private Button adminReportBtn;
    @FXML
    private Button restrictionsBtn;
    @FXML
    private Button viewTableAllocationBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Label displayUsernameLabel, displayRoleLabel;

    @FXML
    private void initialize(){
        String user = Datastore.getInstance().getFirstName() + " " + Datastore.getInstance().getLastName();
        String role = Datastore.getInstance().getRole();
        displayUsernameLabel.setText("Logged in as: " + user);
        displayRoleLabel.setText("Role: " + role);
    }

    public void handleBookingManagementBtn() {
        String page = "main/ui/adminBookingManagement.fxml";
        util.changeScenes(bookingManagementBtn, page);
    }

    public void handleAccountManagementBtn() {
        String page = "main/ui/adminAccountManagement.fxml";
        util.changeScenes(accountManagementBtn, page);
    }

    public void handleRestrictionsBtn() {
        String page = "main/ui/covidRestrictionsPopup.fxml";
        util.displayPopup(restrictionsBtn, page,600,450);
    }

    public void handleViewTablesBtn(){
        String page = "main/ui/adminViewTables.fxml";
        util.changeScenes(viewTableAllocationBtn, page);
    }

    public void handleLogoutBtn(){
        String title = "Confirm Logout";
        String issue = "Do you wish to logout?";
        String instructions = "Select YES to continue or NO to cancel";
        if (util.confirmationAlert(title,issue,instructions)){
            String page = "main/ui/login.fxml";
            util.changeScenes(logoutBtn,page);
        }
    }

    public void handleReportBtn() throws SQLException {
        String title = "Generate Admin Reports";
        String issue = "Do you wish to generate reports of user and booking data?";
        String instructions = "Please note that these reports contain sensitive information\nand are in .csv format";
        if (util.confirmationAlert(title,issue,instructions)){
            adminMenuModel.generateBookingReport();
            adminMenuModel.generateEmployeeReport();
            String title1 = "Generate Admin Reports";
            String issue1 = "Admin Reports successfully generated";
            String instructions1 = "Please check the file folder to access these files";
            util.successAlert(title1,issue1,instructions1);
        }
    }



}


