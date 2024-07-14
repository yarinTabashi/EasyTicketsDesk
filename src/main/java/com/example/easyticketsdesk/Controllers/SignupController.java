package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.RequestsUtility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.IOException;

public class SignupController {
    @FXML
    private MainSign mainScreenController;

    // Sign-up fields
    @FXML
    private TextField first_name_field, last_name_field, email_field;
    @FXML
    private PasswordField password_field, re_password_field;

    @FXML
    private Button signup_btn; // Save btn
    @FXML
    private Label password_warning_label; // to display warnings related to password issues

    public void initialize(MainSign mainScreenController){
        this.mainScreenController = mainScreenController;
    }

    @FXML
    void already_exists_clicked(MouseEvent event) {
        mainScreenController.loadSignInScreen();
    }

    @FXML
    void signup_clicked(MouseEvent event) {
        if (validateFields()) {
            boolean succeed = RequestsUtility.register(first_name_field.getText(), last_name_field.getText(), email_field.getText(), password_field.getText());
            if (succeed){
                System.out.println("Succeed. Now move to verify your email (OTP)");
                //mainScreenController.loadOTP();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Register failed");
                alert.setContentText("An error occurred while registration");
                alert.showAndWait();
            }
        }
    }

    private boolean validateFields() {
        String email = email_field.getText();
        String firstName = first_name_field.getText();
        String lastName = last_name_field.getText();
        String password = password_field.getText();
        String repassword = re_password_field.getText();

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