package com.example.easyticketsdesk.Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.IOException;

public class SignupController {
    @FXML
    private MainSign mainScreenController;

    @FXML
    private Label already_exists;

    @FXML
    private TextField email_field;

    @FXML
    private TextField first_name_field;

    @FXML
    private TextField last_name_field;

    @FXML
    private Pane left_pane;

    @FXML
    private PasswordField password_field;

    @FXML
    private PasswordField register_repassword_field;

    @FXML
    private Button signup_btn;
    @FXML
    private Label password_warning_label;

    @FXML
    void already_exists_clicked(MouseEvent event) {
        mainScreenController.loadSignInScreen();
    }

    @FXML
    void signup_clicked(MouseEvent event) {
        //if (validateFields()) {
            mainScreenController.loadOTP();
        //}
    }

    public void setMainScreenController(MainSign mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    private boolean validateFields() {
        String email = email_field.getText();
        String firstName = first_name_field.getText();
        String lastName = last_name_field.getText();
        String password = password_field.getText();
        String repassword = register_repassword_field.getText();

        if (email.isEmpty() || !isValidEmail(email)) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return false;
        }

        if (firstName.isEmpty()) {
            showAlert("Empty First Name", "Please enter your first name.");
            return false;
        }

        if (lastName.isEmpty()) {
            showAlert("Empty Last Name", "Please enter your last name.");
            return false;
        }

        if (password.isEmpty() || password.length() < 6) {
            password_warning_label.setText("Password must be at least 6 characters long.");
            return false;
        }

        if (!password.equals(repassword)) {
            password_warning_label.setText("Passwords do not match.");
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        // Simple email validation using regex
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void switchToMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/dashboard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) signup_btn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}