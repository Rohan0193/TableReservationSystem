package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.model.Datastore;
import main.model.cancelBookingPopupModel;
import main.model.util;

import java.sql.SQLException;

public class cancelBookingPopupController {
    cancelBookingPopupModel cancelBookingPopupModel = new cancelBookingPopupModel();

    @FXML
    private TextField tableBookedTF;
    @FXML
    private TextField dateBookedTF;
    @FXML
    private TextField displayUsernameTF;
    @FXML
    private TextField displayStatusTF;
    @FXML
    private Button cancelBookingBtn;
    @FXML
    private Button exitBtn;

    @FXML
    private void initialize(){
        tableBookedTF.setText(Datastore.getInstance().getTableBooked());
        dateBookedTF.setText(Datastore.getInstance().getDateBooked());
        displayUsernameTF.setText(Datastore.getInstance().getUsername());
        displayStatusTF.setText(Datastore.getInstance().getBookingStatus());
    }

    public void handleCancelBookingBtn() throws SQLException {
        int bookingID = Datastore.getInstance().getBookingID();

        String title = "Cancel Booking";
        String issue = "Are you sure you want to cancel your current booking?";
        String instructions = "Select Yes to proceed or No to cancel";
        if (util.confirmationAlert(title,issue,instructions)) {
            if (cancelBookingPopupModel.checkCancellationCutoff()){
                cancelBookingPopupModel.deleteBooking(bookingID);
                Stage stage = (Stage) exitBtn.getScene().getWindow();
                stage.close();
                String title1 = "Cancel Booking";
                String issue1 = "Successfully cancelled booking";
                String instructions1 = "Select OK to continue";
                util.successAlert(title1,issue1,instructions1);
            }
            else {
                String title1 = "Cancel Booking";
                String issue1 = "Cannot cancel booking: Cancellation cut off date has been passed";
                String instructions1 = "Users may only cancel bookings up to 2 days before the set Booking Date\n" +
                        "Please Contact an Admin for more help";
                util.errorAlert(title1, issue1, instructions1);
            }
        }
    }

    public void handleExitBtn(){
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

}
