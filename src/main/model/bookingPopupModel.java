package main.model;

import main.SQLConnection;

import java.sql.*;
import java.time.LocalDate;

public class bookingPopupModel {

    Connection connection;

    public bookingPopupModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    //method to insert data into booking database when booking is made
    public void makeBooking(int employeeID, LocalDate bookingDate, String table) {
        connection = SQLConnection.connect();
        Statement statement = null;
        // calculate the cutoff date for cancellation and convert LocalDate to Strings
        String cancellationCutoff = bookingDate.minusDays(2).toString();
        String bookingDateStr = bookingDate.toString();

        String insertFields = "INSERT INTO Bookings(employeeID, bookingDate, cancellationCutoff, tableBooked) VALUES ('";
        String insertValues = employeeID + "','" + bookingDateStr + "','" + cancellationCutoff + "','" + table + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(insertToRegister);
            updateLastTableBooked(table, employeeID);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                try {
                    if (connection != null){
                        connection.close();
                    }
                    if (statement != null){
                        statement.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    // when a user successfully makes a booking, sets the booked table as the last booked table for an employee
    public void updateLastTableBooked(String table, int employeeID) throws SQLException {
        PreparedStatement preparedStatement = null;
        connection = SQLConnection.connect();

        try {
            preparedStatement = connection.prepareStatement(
                    "update Employee set lastTableBooked = replace(lastTableBooked, lastTableBooked, ?) where employeeID = ?");
            preparedStatement.setString(1, table);
            preparedStatement.setInt(2, employeeID);
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

    // method to check if a user already has a booking before placing a booking, as a user is only allowed to have one booking at a time
    public boolean checkForExistingBooking(int employeeID) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Bookings where employeeID = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeID);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e) {
                return true;
        }
        finally {
            if (connection != null){
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

    /*compares current table a user is attempting to book with their previously booked table (from Database) to avoid
    same sit booking*/
    public boolean checkPreviousBooking(String tableSelected, int employeeID) throws SQLException {
        String lastTableBooked = getPreviousBooking(employeeID);
        if (tableSelected.equals(lastTableBooked)){
            return true;
        }
        else{
            return false;
        }
    }

    // gets lastTableBooked from a employee from database and returns the table name as a string
    public String getPreviousBooking(int employeeID) throws SQLException {
        connection = SQLConnection.connect();
        String result = "";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select lastTableBooked from employee where employeeID = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeID);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getString("lastTableBooked");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null){
                connection.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (resultSet != null){
                resultSet.close();
            }
        }
        return result;
    }

}
