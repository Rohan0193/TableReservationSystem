package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class adminViewTablesController {
    // uses same model as the employee booking because its the same just less functionality
    main.model.employeeBookingModel employeeBookingModel = new employeeBookingModel();

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

    public void handleBackBtn(){
        String page = "main/ui/adminMenu.fxml";
        util.changeScenes(backBtn, page);
    }
}
