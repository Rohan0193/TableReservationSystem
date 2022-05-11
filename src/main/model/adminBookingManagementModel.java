package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class adminBookingManagementModel {

    Connection connection;

    public adminBookingManagementModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    // returns an observable list of all bookings listed as pending so an admin can accept or reject them
    public ObservableList<bookingObj> getPendingBookings() throws SQLException {
        connection = SQLConnection.connect();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ObservableList<bookingObj> result = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement("SELECT * from Bookings");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int bookingID = resultSet.getInt("bookingID");
                int employeeID = resultSet.getInt("employeeID");
                String bookingDate = resultSet.getString("bookingDate");
                String cancellationCutoff = resultSet.getString("cancellationCutoff");
                String tableBooked = resultSet.getString("tableBooked");
                String bookingStatus = resultSet.getString("bookingStatus");
                String checkinStatus = resultSet.getString("checkinStatus");

                bookingObj booking = new bookingObj(bookingID, employeeID, bookingDate, cancellationCutoff, tableBooked, bookingStatus, checkinStatus);
                result.add(booking);
            }

        }catch(SQLException e){
            e.printStackTrace();
        } finally {
                if (connection != null){
                    connection.close();
                }
                if (resultSet != null){
                    resultSet.close();
                }
                if (preparedStatement != null){
                    preparedStatement.close();
                }
        }
        return result;
    }

    // changes bookingStatus from Pending to accepted in the database
    public void acceptBooking(int bookingID) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "update Bookings set bookingStatus = replace(bookingStatus, bookingStatus, 'Accepted') where bookingID = ?");
            preparedStatement.setInt(1, bookingID);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (connection != null){
                connection.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }

        }
    }

    public void rejectBooking(int bookingID) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM Bookings WHERE bookingID = ?");
            preparedStatement.setInt(1, bookingID);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (connection != null){
                connection.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
        }
    }

    // when an booking is accepted the other pending bookings of that table and date are removed to avoid doubleups
    public void removeDuplicatePendingBookings(String table, String date) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM Bookings WHERE tableBooked = ? AND bookingDate = ? AND bookingStatus = 'Pending'");
            preparedStatement.setString(1, table);
            preparedStatement.setString(2, date);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (connection != null){
                connection.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
        }
    }
}
