package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class employeeBookingModel {

    Connection connection;

    public employeeBookingModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    // uses the date entered in the employee booking controller to get all bookings at that particular date and return them as a list
    public ArrayList<bookingObj> getBookingsatDate(LocalDate date) throws SQLException {

        String dateStr = date.toString();

        connection = SQLConnection.connect();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ArrayList<bookingObj> result = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement("SELECT * from Bookings where bookingDate = ?");
            preparedStatement.setString(1, dateStr);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int bookingID = resultSet.getInt("bookingID");
                int employeeID = resultSet.getInt("employeeID");
                String bookingDate = resultSet.getString("bookingDate");
                String cancellationCutoff = resultSet.getString("cancellationCutoff");
                String tableBooked = resultSet.getString("tableBooked");
                String bookingStatus = resultSet.getString("bookingStatus");
                String checkinStatus = resultSet.getString("checkinStatus");

                bookingObj booking = new bookingObj(bookingID, employeeID, bookingDate, cancellationCutoff, tableBooked,
                        bookingStatus, checkinStatus);
                result.add(booking);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        }

        return result;
    }

    /* checks adminRestriction database for restriction at particular date and if true stores restriction
    details in Datastore*/
    public boolean checkForRestriction(String date) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from adminRestrictions where restrictionDate = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, date);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // assign and store values of current logged in user into the Datastore for use in other classes
                Datastore.getInstance().setRestrictionType(resultSet.getString("restrictionType"));
                Datastore.getInstance().setRestrictionDate(resultSet.getString("restrictionDate"));
                return true;
            }
            else{
                return false;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }finally {
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

    // generates a list of restriction objects from database
    public ArrayList<restrictionObj> getRestrictions() throws SQLException {
        connection = SQLConnection.connect();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ArrayList<restrictionObj> result = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement("SELECT * from adminRestrictions");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String restrictionDate = resultSet.getString("restrictionDate");
                String restrictionType = resultSet.getString("restrictionType");

                restrictionObj restriction = new restrictionObj(restrictionType,restrictionDate);
                result.add(restriction);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        }

        return result;
    }

    // deletes bookings for required tables to maintain social distancing if restriction implemented at particular date
    public void updateBookingsSocialDistancing(String date) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM Bookings WHERE bookingDate = ? " +
                    "and tableBooked in ('table2','table4','table6','table8','table10','table12','table14','table16')");
            preparedStatement.setString(1, date);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (preparedStatement!= null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        }
    }

    // deletes all bookings at a particular date if lockdown
    public void updateBookingsLockdown(String date) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM Bookings WHERE bookingDate = ?");
            preparedStatement.setString(1, date);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (preparedStatement!= null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        }
    }

    // deletes bookings before current date
    public void deletePastBookings() throws SQLException {
        String date = LocalDate.now().toString();
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM Bookings WHERE bookingDate < ?");
            preparedStatement.setString(1, date);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (preparedStatement!= null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        }
    }

}
