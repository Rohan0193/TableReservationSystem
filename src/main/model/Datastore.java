package main.model;

/* this is a singleton class which stores relevant data for several key functions which need to be transported across
    across classes, or saved for easy accessibility */

import java.time.LocalDate;

public class Datastore {
    public static Datastore datastore;

    // Current logged in users Details
    private int employeeID;
    private String firstName;
    private String lastName;
    private String role;
    private String username;
    private String Password;
    private String accountStatus;

    //table selection details
    private String tableSelected;
    private LocalDate dateSelected;

    //stores info when admin/employee alters user details
    private int employeeIDUpdateUser;
    private String currentUsername;

    // stores info about Users current booking when viewing/deleting and checking in
    private String tableBooked;
    private String dateBooked;
    private int bookingID;
    private String cancellationCutoff;
    private String bookingStatus;
    private String checkinStatus;

    // stores info about an adminRestriction at a particular date
    private String restrictionType;
    private String restrictionDate;

    private Datastore(){ }

    public static Datastore getInstance(){
        if(datastore==null) {
            datastore = new Datastore();
        }
        return datastore;
    }

    // getters and setters for the variables declared above

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public String getRestrictionType() {
        return restrictionType;
    }

    public void setRestrictionType(String restrictionType) {
        this.restrictionType = restrictionType;
    }

    public String getRestrictionDate() {
        return restrictionDate;
    }

    public void setRestrictionDate(String restrictionDate) {
        this.restrictionDate = restrictionDate;
    }

    public String getCancellationCutoff() {
        return cancellationCutoff;
    }

    public void setCancellationCutoff(String cancellationCutoff) {
        this.cancellationCutoff = cancellationCutoff;
    }

    public String getCheckinStatus() {
        return checkinStatus;
    }

    public void setCheckinStatus(String checkinStatus) {
        this.checkinStatus = checkinStatus;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getTableBooked() {
        return tableBooked;
    }

    public void setTableBooked(String tableBooked) {
        this.tableBooked = tableBooked;
    }

    public String getDateBooked() {
        return dateBooked;
    }

    public void setDateBooked(String dateBooked) {
        this.dateBooked = dateBooked;
    }

    public int getEmployeeIDUpdateUser() {
        return employeeIDUpdateUser;
    }

    public void setEmployeeIDUpdateUser(int employeeIDUpdateUser) {
        this.employeeIDUpdateUser = employeeIDUpdateUser;
    }

    // getters and setters for employee details
    public int getEmployeeID(){
        return employeeID;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getRole(){
        return role;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword() {
        return Password;
    }

    public String getTableSelected(){
        return tableSelected;
    }

    public LocalDate getDateSelected(){
        return dateSelected;
    }

    public void setEmployeeID(int employeeID){
        this.employeeID = employeeID;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setRole(String role){
        this.role = role;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setTableSelected(String tableSelected){
        this.tableSelected = tableSelected;
    }

    public void setDateSelected(LocalDate dateSelected){
        this.dateSelected = dateSelected;
    }

}
