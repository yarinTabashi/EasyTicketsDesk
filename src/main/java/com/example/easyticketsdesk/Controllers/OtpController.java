package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.RequestsUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import org.json.JSONException;
import org.json.JSONObject;

public class OtpController implements Initializable {
    // OTP interval issues
    private final int INTERVAL_TIME_SECONDS = 60; // Interval time in seconds
    private int timerSeconds = INTERVAL_TIME_SECONDS;
    private Timeline timer;
    private boolean isIntervalOver = false;
    @FXML
    private Label timer_label;

    // Additional
    @FXML
    private TextField textField1, textField2, textField3, textField4, textField5, textField6;  // Fields for entering each digit of the OTP
    public Button submit_btn;
    @FXML
    private Label warning_label;

    @FXML
    private MainSign mainScreenController;
    private String emailAddress; // The email of the user whose trying to sign in.

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startTimer();
    }

    // Starts a countdown timer for OTP resend interval.
    private void startTimer() {
        this.isIntervalOver = false;

        timer = new Timeline(new KeyFrame(
                javafx.util.Duration.seconds(1),
                event -> {
                    timerSeconds--;
                    timer_label.setText("" + timerSeconds + " seconds"); // Update timer label

                    if (timerSeconds <= 0) {
                        timer.stop();
                        this.isIntervalOver = true;
                        timer_label.setText("Resend OTP");
                    }
                }
        ));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    public boolean setEmailAddressAndSend(String emailAddress){
        this.emailAddress = emailAddress;
        boolean otpSent = RequestsUtility.sendOTP(this.emailAddress);

        if (!otpSent) {
            showAlert("Error", "Error Sending OTP",
                    "An error occurred while trying to send OTP to " + this.emailAddress);
            return false;
        }
        return true;
    }

    public void showAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);

        // Showing the alert
        alert.showAndWait(); // This will block execution until the user closes the alert
    }

    public void setMainScreenController(MainSign mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    // Set event handlers for all text fields
    private void setupTextFields() {
        textField1.setOnKeyTyped(this::handleDigitInput);
        textField2.setOnKeyTyped(this::handleDigitInput);
        textField3.setOnKeyTyped(this::handleDigitInput);
        textField4.setOnKeyTyped(this::handleDigitInput);
        textField5.setOnKeyTyped(this::handleDigitInput);
        textField6.setOnKeyTyped(this::handleDigitInput);
    }

    // Handles input of a single digit (0-9) in each text field and moves focus to the next field.
    @FXML
    private void handleDigitInput(KeyEvent event) {
        TextField source = (TextField) event.getSource();

        // Allow only one digit (0-9)
        if (!event.getCharacter().matches("[0-9]")) {
            event.consume();
            return;
        }

        // Moving the focus
        switch (source.getId()) {
            case "textField1":
                textField2.requestFocus();
                break;
            case "textField2":
                textField3.requestFocus();
                break;
            case "textField3":
                textField4.requestFocus();
                break;
            case "textField4":
                textField5.requestFocus();
                break;
            case "textField5":
                textField6.requestFocus();
                break;
            default:
                break;
        }
    }


    // Handles the submission of the OTP and verifies it.
    public void submit_clicked(MouseEvent mouseEvent) {
        String otpString = textField1.getText() + textField2.getText() + textField3.getText() + textField4.getText() + textField5.getText() + textField6.getText();
        try {
            int otp = Integer.parseInt(otpString);

            // Verify OTP using RequestsUtility
            JSONObject jsonObject = RequestsUtility.verifyOTP(emailAddress, otp);
            if (jsonObject == null){
                this.warning_label.setText("Invalid OTP. Try again");
            }
            else {
                System.out.println("OTP verification successful");
                this.mainScreenController.loadRestorePassword(jsonObject.getString("token"));
            }
        } catch (NumberFormatException e) {
            // Display error message for invalid OTP format
            this.warning_label.setText("Invalid OTP format. Enter digits only.");
        } catch (JSONException e) {
            throw new RuntimeException(e); // Throw a runtime exception for JSON parsing error
        }
    }

    // Checks if the resend interval is over and sends OTP using RequestsUtility.
    public void resend_clicked(MouseEvent mouseEvent) {
        if  (isIntervalOver){
            RequestsUtility.sendOTP(this.emailAddress);
            startTimer();
        }
    }
}