package main.model;

// This class represents a booking obj found in the booking Database

public class bookingObj {
    int bookingID;
    int employeeID;
    String bookingDate;
    String cancellationCutoff;
    String tableBooked;
    String bookingStatus;
    String checkinStatus;

    public bookingObj(int bookingID, int employeeID, String bookingDate, String cancellationCutoff,
                      String tableBooked, String bookingStatus, String checkinStatus){
        this.bookingID = bookingID;
        this.employeeID = employeeID;
        this.bookingDate = bookingDate;
        this.cancellationCutoff = cancellationCutoff;
        this.tableBooked = tableBooked;
        this.bookingStatus = bookingStatus;
        this.checkinStatus = checkinStatus;
    }

    public int getBookingID() {
        return bookingID;
    }

    public int getEmployeeID(){
        return employeeID;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getCancellationCutoff() {
        return cancellationCutoff;
    }

    public String getTableBooked() {
        return tableBooked;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public String getCheckinStatus() {
        return checkinStatus;
    }
}
