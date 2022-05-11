package main.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.adminBookingManagementModel;
import main.model.bookingObj;
import main.model.util;

import java.sql.SQLException;

public class adminBookingManagementController {
    main.model.adminBookingManagementModel adminBookingManagementModel = new adminBookingManagementModel();

    @FXML
    private TableView<bookingObj> table;
    @FXML
    private TableColumn<bookingObj, Integer> bookingID;
    @FXML
    private TableColumn<bookingObj, Integer> employeeID;
    @FXML
    private TableColumn<bookingObj, String> bookingDate;
    @FXML
    private TableColumn<bookingObj, String> cancellationCutoff;
    @FXML
    private TableColumn<bookingObj, String> tableBooked;
    @FXML
    private TableColumn<bookingObj, String> bookingStatus;
    @FXML
    private TableColumn<bookingObj, String> checkinStatus;
    @FXML
    private Button refreshBtn, acceptBtn, rejectBtn, backBtn;

    @FXML
    private void initialize() throws SQLException {
        ObservableList<bookingObj> items = adminBookingManagementModel.getPendingBookings();
        table.setItems(items);
        bookingID.setCellValueFactory(new PropertyValueFactory<bookingObj,Integer>("bookingID"));
        employeeID.setCellValueFactory(new PropertyValueFactory<bookingObj,Integer>("employeeID"));
        bookingDate.setCellValueFactory(new PropertyValueFactory<bookingObj,String>("bookingDate"));
        cancellationCutoff.setCellValueFactory(new PropertyValueFactory<bookingObj,String>("cancellationCutoff"));
        tableBooked.setCellValueFactory(new PropertyValueFactory<bookingObj,String>("tableBooked"));
        bookingStatus.setCellValueFactory(new PropertyValueFactory<bookingObj,String>("bookingStatus"));
        checkinStatus.setCellValueFactory(new PropertyValueFactory<bookingObj,String>("checkinStatus"));
    }

    public void handleAcceptBtn() throws SQLException {
        if (table.getSelectionModel().getSelectedItem() != null){
            int bookingID = table.getSelectionModel().getSelectedItem().getBookingID();
            String tableBooked = table.getSelectionModel().getSelectedItem().getTableBooked();
            String dateBooked = table.getSelectionModel().getSelectedItem().getBookingDate();
            adminBookingManagementModel.acceptBooking(bookingID);
            adminBookingManagementModel.removeDuplicatePendingBookings(tableBooked,dateBooked);
            String title = "Accept Booking";
            String issue = "Booking successfully accepted and confirmed";
            String instructions = "Please select OK to continue";
            util.successAlert(title,issue,instructions);
            initialize();
        }
        else{
            String title = "Accept Booking";
            String issue = "ERROR: No booking has been selected";
            String instructions = "Please click and select a booking from the table before continuing";
            util.errorAlert(title, issue, instructions);
        }
    }

    public void handleRejectBtn() throws SQLException {
        if (table.getSelectionModel().getSelectedItem() != null){
            int bookingID = table.getSelectionModel().getSelectedItem().getBookingID();
            adminBookingManagementModel.rejectBooking(bookingID);
            String title = "Reject Booking";
            String issue = "Booking successfully rejected and removed";
            String instructions = "Please select OK to continue";
            util.successAlert(title,issue,instructions);
            initialize();
        }
        else {
            String title = "Reject Booking";
            String issue = "ERROR: No booking has been selected";
            String instructions = "Please click and select a booking from the table before continuing";
            util.errorAlert(title, issue, instructions);
        }
    }

    public void handleBackBtn(){
        String page = "main/ui/adminMenu.fxml";
        util.changeScenes(backBtn, page);
    }

}
