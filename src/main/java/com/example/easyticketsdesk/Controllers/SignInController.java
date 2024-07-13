package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.RequestsUtility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SignInController {
    private MainSign mainScreenController;
    @FXML
    private TextField email_field;
    @FXML
    private Label forgot_password_label;
    @FXML
    private PasswordField password_field;
    @FXML
    private Button signin_btn;
    @FXML
    private Button signup_btn;

    public void setMainScreenController(MainSign mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    @FXML
    void forgotPasswordClicked(MouseEvent event) {
        // TODO: Need to firstly go to the OTP and then to create new password.
        //RequestsUtility.sendOTP(this.email_field.getText());
        mainScreenController.setCurrentEmail(this.email_field.getText());
        mainScreenController.loadOTP();
    }

    @FXML
    void signInBtnClicked(MouseEvent event) {
        if (validateFields()) {
            // Proceed with login logic and scene switching
            JSONObject jsonObject = RequestsUtility.login(email_field.getText(), password_field.getText());
            if (jsonObject == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login failed");
                alert.setContentText("Make sure the properties are ok");
                alert.showAndWait();
            }
            else {
                try {
                    System.out.println(jsonObject.getString("email"));
                    System.out.println(jsonObject.getString("token"));
                    switchToMainWindow(jsonObject.getString("token"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    void signUpClicked(MouseEvent event) {
        mainScreenController.loadSignUpScreen();
    }

    private boolean validateFields() {
        String email = email_field.getText();
        String password = password_field.getText();

        if (email.isEmpty() || !isValidEmail(email)) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return false;
        }

        if (password.isEmpty() || password.length() < 6) {
            showAlert("Invalid Password", "Password must be at least 6 characters long.");
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

    private void switchToMainWindow(String currentJwt) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/main_window.fxml"));
            Parent root = loader.load();

            // Access the controller instance
            MainWindowController controller = loader.getController();

            // Pass currentJwt to the controller
            controller.SetJwt(currentJwt);

            Scene scene = new Scene(root);
            Stage stage = (Stage) signin_btn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
