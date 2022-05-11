package main.model;

// this class is representative of an employee object found in the Employee table in the database

public class employeeObj {
    int employeeID;
    String firstName;
    String lastName;
    String role;
    String username;
    String password;
    String secretQuestion;
    String ansSecretQuestion;
    String accountStatus;
    String lastTableBooked;

    public employeeObj(int employeeID, String firstName, String lastName, String role, String username, String password,
                      String secretQuestion, String ansSecretQuestion, String accountStatus, String lastTableBooked){
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.username = username;
        this.password = password;
        this.secretQuestion = secretQuestion;
        this.ansSecretQuestion = ansSecretQuestion;
        this.accountStatus = accountStatus;
        this.lastTableBooked = lastTableBooked;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getAnsSecretQuestion() {
        return ansSecretQuestion;
    }

    public void setAnsSecretQuestion(String ansSecretQuestion) {
        this.ansSecretQuestion = ansSecretQuestion;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getLastTableBooked() {
        return lastTableBooked;
    }

    public void setLastTableBooked(String lastTableBooked) {
        this.lastTableBooked = lastTableBooked;
    }
}
