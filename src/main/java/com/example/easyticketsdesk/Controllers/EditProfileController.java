package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.Entities.UserProfile;
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
    private TextField emailTextField;
    @FXML
    private TextField firstTextField;
    @FXML
    private TextField lastTextField;
    @FXML
    private Button save_btn;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void setWelcomeLabel(UserProfile userProfile){
        this.welcome_label.setText(mainWindowController.getWelcomeText());
        this.userProfile = userProfile;
        this.emailTextField.setText(userProfile.getEmail());
        this.firstTextField.setText(userProfile.getFirstName());
        this.lastTextField.setText(userProfile.getLastName());
    }

    public void save_clicked(MouseEvent mouseEvent) {
        // TODO: Save the details
        System.out.println("Details saved");
    }
}
