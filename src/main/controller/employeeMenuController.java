package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.model.Datastore;
import main.model.employeeMenuModel;
import main.model.util;

import java.sql.SQLException;
import java.time.LocalDate;

public class employeeMenuController {

    employeeMenuModel employeeMenuModel = new employeeMenuModel();

    @FXML
    private Button makeBookingBtn;
    @FXML
    private Button cancelBookingBtn;
    @FXML
    private Button checkInBtn;
    @FXML
    private Button changeDetailsBtn;
    @FXML
    private Button changePasswordBtn;
    @FXML
    private Label displayUsernameLabel, displayRoleLabel, isConnected;
    @FXML
    private Button backBtn, logoutBtn;

    @FXML
    private void initialize(){
        displayUsernameLabel.setText("Logged in as: " + Datastore.getInstance().getFirstName() + " " + Datastore.getInstance().getLastName());
        displayRoleLabel.setText("Role: " + Datastore.getInstance().getRole());
        if (employeeMenuModel.isDbConnected()){
            isConnected.setText("Connected");
        }else{
            isConnected.setText("Not Connected");
        }
    }

    public void handleMakeBookingBtn(){
        String page = "main/ui/employeeBooking.fxml";
        util.changeScenes(makeBookingBtn, page);
    }

    public void handleCancelBookingBtn() throws SQLException {
        if (employeeMenuModel.checkIfUserHasBooking(Datastore.getInstance().getEmployeeID())){
            String page = "main/ui/cancelBookingPopup.fxml";
            util.displayPopup(cancelBookingBtn, page,400,300);
        }
        else {
            String title = "Cancel/View Booking";
            String issue = "No existing booking found";
            String instructions = "Please place a booking in the Booking Menu";
            util.errorAlert(title, issue, instructions);
        }
    }

    public void handleCheckInBtn() throws SQLException {
        if (employeeMenuModel.checkIfUserHasBooking(Datastore.getInstance().getEmployeeID())){
            String currentDate = LocalDate.now().toString();
            if (currentDate.equals(Datastore.getInstance().getDateBooked())){
                if(Datastore.getInstance().getBookingStatus().equals("Accepted")){
                    if(Datastore.getInstance().getCheckinStatus().equals("Not Checked In")){
                        employeeMenuModel.checkIn(Datastore.getInstance().getBookingID());
                        String title = "Booking Check In";
                        String issue = "Successfully Checked In for booking";
                        String instructions = "Enjoy your day! Note: reservation will expire at midnight today";
                        util.successAlert(title,issue,instructions);
                    }
                    else{
                        String title = "Booking Check In";
                        String issue = "Already Checked In";
                        String instructions = "You have already checked in for this booking";
                        util.errorAlert(title,issue,instructions);
                    }
                }
                else{
                    String title = "Booking Check In";
                    String issue = "Booking not accepted: Admin has not accepted your booking yet";
                    String instructions = "Please allow admins adequate time to assess your booking request\nIf you are " +
                            "still experiencing difficulties please contact an admin directly";
                    util.errorAlert(title,issue,instructions);
                }
            }
            else{
                String title = "Booking Check In";
                String issue = "Current Date does not match Booking Date";
                String instructions = "Please only attempt to check in on the day of your booking";
                util.errorAlert(title,issue,instructions);
            }
        }
        else{
            String title = "Booking Check In";
            String issue = "No existing booking found";
            String instructions = "Please place a booking in the Booking Menu";
            util.errorAlert(title,issue,instructions);
        }

    }

    public void handleChangeDetailsBtn(){
        String page = "main/ui/employeeUpdateDetails.fxml";
        util.displayPopup(changeDetailsBtn, page,450,450);
    }

    public void handleChangePasswordBtn(){
        String page = "main/ui/changePasswordPopup.fxml";
        util.displayPopup(changePasswordBtn, page,400,300);
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


}
