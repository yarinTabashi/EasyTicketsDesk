package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.Entities.UserProfile;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditProfileController {
    private UserProfile userProfile;
    @FXML
    private MainWindowController mainWindowController;
    @FXML
    private Label welcome_label;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField phoneTextField;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void setWelcomeLabel(UserProfile userProfile){
        this.welcome_label.setText(mainWindowController.getWelcomeText());
        this.userProfile = userProfile;
        this.emailTextField.setText(userProfile.getEmail());
    }
}
