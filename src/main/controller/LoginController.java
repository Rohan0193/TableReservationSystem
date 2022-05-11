package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.model.Datastore;
import main.model.LoginModel;
import main.model.util;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public LoginModel loginModel = new LoginModel();
    @FXML
    private Label isConnected;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button resetPasswordBtn;
    @FXML
    private Button signupBtn;
    @FXML
    private Button loginBtn;
    @FXML
    private Button backBtn;


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (loginModel.isDbConnected()){
            isConnected.setText("Connected");
        }else{
            isConnected.setText("Not Connected");
        }

    }
    /* login Action method
       check if user input is the same as database.
     */
    public void Login(){
        try {
            if (loginModel.isLogin(txtUsername.getText(),txtPassword.getText())){
                //check if accountStatus is active or deactivated
                if (Datastore.getInstance().getAccountStatus().equals("Active")){
                    // check if the successful login is an admin or a employee and redirect to relevant page
                    if (loginModel.isAdmin()){
                        String page = "main/ui/adminMenu.fxml";
                        util.changeScenes(loginBtn, page);
                    }
                    else {
                        String page = "main/ui/employeeMenu.fxml";
                        util.changeScenes(loginBtn, page);
                    }
                }
                else {
                    String title = "Unsuccessful login";
                    String issue = "Account has been deactivated by admin";
                    String instructions = "Please contact an admin to reactivate your account or for more support/help";
                    util.errorAlert(title, issue, instructions);
                }
            }else{
                String title = "Unsuccessful login";
                String issue = "Username or password is incorrect";
                String instructions = "Please try again";
                util.errorAlert(title, issue, instructions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // method to change scene from the Login.fxml page to the resetPassword.fxml page
    public void handleResetPasswordBtn() {
        String page = "main/ui/resetPassword.fxml";
        util.changeScenes(resetPasswordBtn, page);
    }

    // method to change scene from the Login.fxml page to the resetPassword.fxml page
    public void handleSignUpBtn() {
        String page = "main/ui/register.fxml";
        util.changeScenes(signupBtn, page);
    }

}
