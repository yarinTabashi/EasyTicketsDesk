package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.Entities.UserProfile;
import com.example.easyticketsdesk.RequestsUtilty.MainRequests;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class EditProfileController {
    private UserProfile userProfile;
    @FXML
    private MainWindowController mainWindowController;
    @FXML
    private Label welcome_label;
    @FXML
    private TextField emailTextField, firstTextField, lastTextField; // Fields to edit
    @FXML
    private Button save_btn;

    public void initialize(MainWindowController mainWindowController, UserProfile userProfile) {
        this.mainWindowController = mainWindowController;
        this.userProfile = userProfile;

        initializeDetails();
    }

    public void initializeDetails(){
        this.welcome_label.setText(mainWindowController.getWelcomeText());
        this.emailTextField.setText(userProfile.getEmail());
        this.firstTextField.setText(userProfile.getFirstName());
        this.lastTextField.setText(userProfile.getLastName());
    }

    public void save_clicked(MouseEvent mouseEvent) {
        try
        {
            MainRequests.updateUserDetails(this.mainWindowController.getJwt(), firstTextField.getText(), lastTextField.getText(), emailTextField.getText());
        }
        catch (Exception e){
            System.out.println("Error occurred while trying to save.");
        }
    }
}
