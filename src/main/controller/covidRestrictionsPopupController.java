package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.model.adminMenuModel;
import main.model.restrictionObj;
import main.model.util;

import java.sql.SQLException;

public class covidRestrictionsPopupController {
    adminMenuModel adminMenuModel = new adminMenuModel();

    @FXML
    private ComboBox<String> selectRestriction;
    @FXML
    private DatePicker selectDate;
    @FXML
    private TableView<restrictionObj> currentRestrictionsTable;
    @FXML
    private TableColumn<restrictionObj, String> restrictionTypeCol;
    @FXML
    private TableColumn<restrictionObj, String> restrictionDateCol;
    @FXML
    private Button placeRestrictionBtn, exitBtn, removeRestrictionBtn;

    // create list of options for the roleSelection combo box
    ObservableList<String> restrictionOptions = FXCollections.observableArrayList("Social Distancing",
            "Complete Lockdown");

    @FXML
    private void initialize(){
        selectRestriction.setItems(restrictionOptions);
        ObservableList<restrictionObj> items = adminMenuModel.getRestrictions();
        currentRestrictionsTable.setItems(items);
        restrictionTypeCol.setCellValueFactory(new PropertyValueFactory<restrictionObj,String>("restrictionType"));
        restrictionDateCol.setCellValueFactory(new PropertyValueFactory<restrictionObj,String>("restrictionDate"));
    }

    public void handlePlaceRestrictionsBtn() throws SQLException {
        if (selectDate.getValue() != null && selectRestriction.getValue() != null){
            String date = selectDate.getValue().toString();
            String restrictionType = selectRestriction.getValue();
            if (!adminMenuModel.checkExistingRestrictions(date)){
                adminMenuModel.addRestriction(restrictionType, date);
                String title = "Place Restriction";
                String issue = "Restriction successfully placed";
                String instruction = "Please select OK to continue";
                util.successAlert(title,issue,instruction);
                initialize();
            }
            else {
                String title = "Place Restriction";
                String issue = "Date selected already has an existing restriction";
                String instructions = "Please select another date or delete existing restriction";
                util.errorAlert(title, issue, instructions);
            }
        }
        else {
            String title = "Place Restriction";
            String issue = "Empty Fields";
            String instructions = "Please fill out all fields before proceeding";
            util.errorAlert(title,issue,instructions);
        }
    }

    public void handleRemoveRestrictionBtn() throws SQLException {
        if (currentRestrictionsTable.getSelectionModel().getSelectedItem() != null){

            String title = "Remove Restriction";
            String issue = "Are you sure you want to remove this restriction?";
            String instructions = "Select YES to proceed or NO to cancel";
            if (util.confirmationAlert(title, issue, instructions)){
                String date = currentRestrictionsTable.getSelectionModel().getSelectedItem().getRestrictionDate();
                adminMenuModel.removeRestriction(date);
                initialize();
            }
        }
        else {
            String title = "Remove Restriction";
            String issue = "ERROR: No set restriction has been selected";
            String instructions = "Please click and select a set restriction from the table before continuing";
            util.errorAlert(title, issue, instructions);
        }
    }

    public void handleExitBtn(){
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }


}
