package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class cancelBookingPopupModel {

    Connection connection;

    public cancelBookingPopupModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    // deletes a booking using bookingID from the database
    public void deleteBooking(int bookingID) throws SQLException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM Bookings WHERE bookingID = ?");
            preparedStatement.setInt(1, bookingID);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (preparedStatement != null){
                preparedStatement.close();
            }
        }
    }

    // checks if the time of attempting to cancel is after the cancellation date
    public boolean checkCancellationCutoff(){
        LocalDate cancellationCutoff = LocalDate.parse(Datastore.getInstance().getCancellationCutoff());
        LocalDate currentDate = LocalDate.now();

        if (currentDate.isAfter(cancellationCutoff)){
            return false;
        }
        else {
            return true;
        }
    }




}
