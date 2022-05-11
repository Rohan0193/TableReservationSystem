package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.model.Datastore;
import main.model.changePasswordPopupModel;
import main.model.util;

import java.sql.SQLException;

public class changePasswordPopupController {
    changePasswordPopupModel changePasswordPopupModel = new changePasswordPopupModel();

    @FXML
    private TextField currentPasswordTF;
    @FXML
    private TextField newPasswordTF;
    @FXML
    private TextField confirmPasswordTF;
    @FXML
    private Button confirmBtn, exitBtn;

    public void handleConfirmBtn() throws SQLException {
        String currentPassword = currentPasswordTF.getText();
        String newPassword = newPasswordTF.getText();
        String confirmPassword = confirmPasswordTF.getText();

        if (changePasswordPopupModel.checkCurrentPassword(currentPassword)){
            if (changePasswordPopupModel.checkNewPasswordMatches(newPassword, confirmPassword)){
                if (!currentPassword.equals(newPassword)) {
                    String title = "Change Password";
                    String issue = "Are you sure you wish change your password?";
                    String instructions = "Select YES to proceed or No to cancel";
                    if (util.confirmationAlert(title,issue,instructions)){
                        changePasswordPopupModel.updatePassword(newPassword, Datastore.getInstance().getEmployeeID());
                        Datastore.getInstance().setPassword(newPassword);
                        handleExitBtn();
                        String title1 = "Change Password";
                        String issue1 = "Password successfully updated";
                        String instructions1 = "Select OK to continue";
                        util.successAlert(title1,issue1,instructions1);
                    }
                } else{
                    String title = "Change Password";
                    String issue = "Your new and current password are the same";
                    String instructions = "Please select a different password to change to";
                    util.errorAlert(title,issue,instructions);
                }
            } else {
                String title = "Change Password";
                String issue = "New password does not match";
                String instructions = "Please re-enter passwords and ensure they match";
                util.errorAlert(title,issue,instructions);
            }
        } else{
            String title = "Change Password";
            String issue = "Incorrect current password";
            String instructions = "Please re-enter correct password";
            util.errorAlert(title,issue,instructions);
        }
    }

    public void handleExitBtn(){
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
}
