package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class employeeMenuModel {

    Connection connection;

    public employeeMenuModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    // used in employee menu when cancel booking is selected to see if user has a booking
    public boolean checkIfUserHasBooking(int employeeID) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Bookings where employeeID = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeID);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Datastore.getInstance().setDateBooked(resultSet.getString("bookingDate"));
                Datastore.getInstance().setTableBooked(resultSet.getString("tableBooked"));
                Datastore.getInstance().setBookingID(resultSet.getInt("bookingID"));
                Datastore.getInstance().setCancellationCutoff(resultSet.getString("cancellationCutoff"));
                Datastore.getInstance().setBookingStatus(resultSet.getString("bookingStatus"));
                Datastore.getInstance().setCheckinStatus(resultSet.getString("checkinStatus"));
                return true;
            }
            else{
                return false;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(connection != null){
                connection.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (resultSet != null){
                resultSet.close();
            }
        }
    }

    // updates user data when user decides to change details
    public void updateUserData(int employeeID, String firstname, String lastname, String username, String secretQuestion,
                               String ansSecretQuestion) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        String query = "UPDATE Employee SET firstName = replace(firstName, firstName, ?), lastName = replace(lastName, lastName, ?)," +
                "username = replace(username, username, ?), secretQuestion = replace(secretQuestion, secretQuestion, ?)," +
                "ansSecretQuestion = replace(ansSecretQuestion, ansSecretQuestion, ?) WHERE employeeID = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, secretQuestion);
            preparedStatement.setString(5, ansSecretQuestion);
            preparedStatement.setInt(6, employeeID);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (connection != null){
                connection.close();
            }
            if (preparedStatement!=null){
                preparedStatement.close();
            }
        }
    }

    // changes the users booking checkinStatus to checked in
    public void checkIn(int bookingID) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        String query = "UPDATE Bookings SET checkinStatus = replace(checkinStatus, checkinStatus, 'Checked In') where " +
                "bookingID = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookingID);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (connection != null){
                connection.close();
            }
            if (preparedStatement!=null){
                preparedStatement.close();
            }
        }
    }

    public Boolean isDbConnected(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }
}
