package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.model.Datastore;
import main.model.bookingPopupModel;
import main.model.util;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class bookingPopupController {

    bookingPopupModel bookingPopupModel = new bookingPopupModel();

    @FXML
    private TextField tableSelectedTF;
    @FXML
    private TextField displayUsernameTF;
    @FXML
    private TextField dateSelectedTF;
    @FXML
    private Button confirmBtn, exitBtn;

   @FXML
    private void initialize(){
        tableSelectedTF.setText(Datastore.getInstance().getTableSelected());
        displayUsernameTF.setText(Datastore.getInstance().getUsername());
        String formattedDate = Datastore.getInstance().getDateSelected().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dateSelectedTF.setText(formattedDate);
    }

    public void handleProceedBtn() throws SQLException {
        //checks if all fields are filled in
        if (!tableSelectedTF.getText().trim().isEmpty() || !displayUsernameTF.getText().trim().isEmpty() ||
                !dateSelectedTF.getText().trim().isEmpty()){
            String tableSelected = tableSelectedTF.getText();
            LocalDate bookingDate = Datastore.getInstance().getDateSelected();
            int employeeID = Datastore.getInstance().getEmployeeID();
            // call method to add info to database if the user does not already have an existing booking
            if (!bookingPopupModel.checkForExistingBooking(employeeID)){
                // check if user has previously booked this sitting
                if (!bookingPopupModel.checkPreviousBooking(tableSelected, employeeID)){
                    bookingPopupModel.makeBooking(employeeID,bookingDate, tableSelected);
                    String title = "Book Table";
                    String issue = "Booking successfully requested";
                    String instructions = "Your booking has been requested and now awaits admin confirmation";
                    handleExitBtn();
                    util.successAlert(title,issue,instructions);
                }
                else {
                    String title = "Book Table";
                    String issue = "Same table selected as previous booking";
                    String instructions = "Please select another table that wasn't your previous booking";
                    util.errorAlert(title,issue,instructions);
                }
            } else{
                String title = "Book Table";
                String issue = "There is already an existing booking";
                String instructions = "Please cancel existing booking before making a new one";
                util.errorAlert(title,issue,instructions);
            }
        }
        else {
            String title = "Book Table";
            String issue = "Empty Fields";
            String instructions = "Please fill out all fields before proceeding";
            util.errorAlert(title,issue,instructions);
        }
    }

    public void handleExitBtn(){
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

}
