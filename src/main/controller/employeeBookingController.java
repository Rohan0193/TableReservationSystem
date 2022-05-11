package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class employeeBookingController {
    employeeBookingModel employeeBookingModel = new employeeBookingModel();

    @FXML
    private Label displayUsernameLabel;
    @FXML
    private Label displayRoleLabel;
    @FXML
    private Rectangle table1, table2, table3, table4, table5, table6, table7, table8, table9, table10, table11, table12,
                    table13, table14, table15, table16;
    @FXML
    private Button changePasswordBtn;
    @FXML
    public Button logoutBtn, backBtn;
    @FXML
    public DatePicker datePicker;

    @FXML
    private void initialize() throws SQLException {
        displayUsernameLabel.setText("Logged in as: " + Datastore.getInstance().getFirstName() + " " + Datastore.getInstance().getLastName());
        displayRoleLabel.setText("Role: " + Datastore.getInstance().getRole());
        datePicker.setValue(LocalDate.now());
        updateDatabase();
        handleDatePicker();
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

    // goes from current scene (employeeBooking window) back to the login page
    public void handleBackBtn() throws Exception {
        String page = "main/ui/employeeMenu.fxml";
        util.changeScenes(backBtn, page);
    }

    @FXML   //method to display popup when table is clicked
    public void handleTable(MouseEvent event) throws SQLException {
        if (!datePicker.getValue().toString().trim().isEmpty()) {
            Rectangle tableClicked = (Rectangle) event.getSource();
            if (tableClicked.getFill().equals(Color.DODGERBLUE)){
                Datastore.getInstance().setTableSelected(tableClicked.getId());
                Datastore.getInstance().setDateSelected(datePicker.getValue());
                String title = "Book Table";
                String issue = "There is an existing booking request for this table.\nDo you still wish to proceed?";
                String instructions = "Select NEXT to continue and place request or\nCANCEL to find another table";
                if (util.warningAlert(title,issue,instructions)){
                    String page = "main/ui/bookingPopup.fxml";
                    util.displayPopup(backBtn,page,350,250);
                    handleDatePicker();
                }
            }
            else if (tableClicked.getFill().equals(Color.GREEN)){
                Datastore.getInstance().setTableSelected(tableClicked.getId());
                Datastore.getInstance().setDateSelected(datePicker.getValue());
                String page = "main/ui/bookingPopup.fxml";
                util.displayPopup(backBtn,page,350,250);
                handleDatePicker();
            }
            else {
                String title = "Make a Booking";
                String issue = "ERROR: Table Booked (Red) or Locked down (orange)";
                String instructions = "Please select another table";
                util.errorAlert(title, issue, instructions);
            }
        }
    }

    public void handleDatePicker() throws SQLException {
        LocalDate selectedDate = datePicker.getValue();
        ArrayList<bookingObj> bookingList = employeeBookingModel.getBookingsatDate(selectedDate);
        ArrayList<Rectangle> tableList = createListOfTables();

        setTablesAsAvailable();
        if (bookingList.isEmpty()) {
            if (employeeBookingModel.checkForRestriction(selectedDate.toString())){
                if (Datastore.getInstance().getRestrictionType().equals("Complete Lockdown")){
                    setTablesAsLockdown();
                }
                else {
                    setTablesAsSocialDistanced();
                }
            }
            else {
                setTablesAsAvailable();
            }
        }
        else {
            if (employeeBookingModel.checkForRestriction(selectedDate.toString())){
                setTablesAsSocialDistanced();
                assignTables(tableList,bookingList);
            }
            else {
                assignTables(tableList,bookingList);
            }
        }
    }

    // compares each table to each booking and assigns colour
    public void assignTables(ArrayList<Rectangle> tableList, ArrayList<bookingObj> bookingList){
        for (int i=0; i < tableList.size(); i++){
            if (!tableList.get(i).getFill().equals(Color.ORANGE)){
                tableList.get(i).setFill(Color.LIMEGREEN);
                for (int x = 0; x < bookingList.size(); x++){
                    String table = tableList.get(i).getId();
                    if (bookingList.get(x).getTableBooked().equals(table)){     // checks if table in tablelist is in the booking list
                        if (bookingList.get(x).getBookingStatus().equals("Pending")){
                            tableList.get(i).setFill(Color.DEEPSKYBLUE);
                        }
                        else {
                            tableList.get(i).setFill(Color.RED);
                        }
                    }
                }
            }
        }
    }

    // sets all table colours as green (available)
    public void setTablesAsAvailable() {
        ArrayList<Rectangle> tableList = createListOfTables();
        for (int i=0; i<tableList.size(); i++){
            tableList.get(i).setFill(Color.LIMEGREEN);
        }
    }

    public void setTablesAsLockdown() {
        ArrayList<Rectangle> tableList = createListOfTables();
        for (int i=0; i<tableList.size(); i++){
            tableList.get(i).setFill(Color.ORANGE);
        }
    }

    public void setTablesAsSocialDistanced(){
        ArrayList<Rectangle> tableList = createListOfTables();
        int x = 1;
        for (int i=0; i+x<tableList.size(); i++){
            tableList.get(i+x).setFill(Color.ORANGE);
            x++;
        }
    }

    public ArrayList<Rectangle> createListOfTables(){
        ArrayList<Rectangle> tableList = new ArrayList<>();
        tableList.add(table1);
        tableList.add(table2);
        tableList.add(table3);
        tableList.add(table4);
        tableList.add(table5);
        tableList.add(table6);
        tableList.add(table7);
        tableList.add(table8);
        tableList.add(table9);
        tableList.add(table10);
        tableList.add(table11);
        tableList.add(table12);
        tableList.add(table13);
        tableList.add(table14);
        tableList.add(table15);
        tableList.add(table16);
        return tableList;
    }

    @FXML
    public void hoverAnimationEnter(MouseEvent event){
        Rectangle tableHovered = (Rectangle) event.getSource();
        if (tableHovered.getFill() == Color.LIMEGREEN){
            tableHovered.setFill(Color.GREEN);
        }
        else if (tableHovered.getFill().equals(Color.DEEPSKYBLUE)){
            tableHovered.setFill(Color.DODGERBLUE);
        }
    }

    @FXML
    public void hoverAnimationExit(MouseEvent event){
        Rectangle tableHovered = (Rectangle) event.getSource();
        if (tableHovered.getFill().equals(Color.GREEN)){
            tableHovered.setFill(Color.LIMEGREEN);
        }
        else if (tableHovered.getFill().equals(Color.DODGERBLUE)){
            tableHovered.setFill(Color.DEEPSKYBLUE);
        }
    }

    public void updateDatabase() throws SQLException {
        employeeBookingModel.deletePastBookings();
        ArrayList<restrictionObj> restrictionsList = employeeBookingModel.getRestrictions();
        for (int i=0;i<restrictionsList.size(); i++){
            if (restrictionsList.get(i).getRestrictionType().equals("Social Distancing")){
                employeeBookingModel.updateBookingsSocialDistancing(restrictionsList.get(i).getRestrictionDate());
            }
            else{
                employeeBookingModel.updateBookingsLockdown(restrictionsList.get(i).getRestrictionDate());
            }
        }
    }


}
