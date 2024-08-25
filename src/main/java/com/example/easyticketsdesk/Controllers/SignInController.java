package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.Entities.UserProfile;
import com.example.easyticketsdesk.RequestsUtilty.AuthRequests;
import com.example.easyticketsdesk.RequestsUtilty.MainRequests;
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
    private PasswordField password_field;

    // Navigation controllers (e.g., sign-in, sign-up, forgot-password)
    @FXML
    private Label forgot_password_label;
    @FXML
    private Button signin_btn, signup_btn;

    public void setMainScreenController(MainSign mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    @FXML
    void forgotPasswordClicked(MouseEvent event) {
        //RequestsUtility.sendOTP(this.email_field.getText());
        mainScreenController.setCurrentEmail(this.email_field.getText());
        mainScreenController.loadOTP();
    }

    @FXML
    void signInBtnClicked(MouseEvent event) throws JSONException {
        if (validateFields()) {
            // Proceed with login logic and scene switching
            JSONObject jsonObject = AuthRequests.login(email_field.getText(), password_field.getText());
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

    // Validates the email and password fields and displays alerts for invalid input.
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

    //Checks if the given email string matches a basic email format using regex.
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Shows a warning alert dialog with the given title and message.
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Switches the scene to the main window after successful sign-in.
    private void switchToMainWindow(String currentJwt) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/main_window.fxml"));
            Parent root = loader.load();

            // Access the controller instance
            MainWindowController controller = loader.getController();

            // Pass currentJwt to the controller and load user details
            UserProfile userProfile = MainRequests.getUserProfile(currentJwt);
            controller.SetUserData(userProfile);
            controller.load_dashboard();

            Scene scene = new Scene(root);
            Stage stage = (Stage) signin_btn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
