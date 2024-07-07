package com.example.easyticketsdesk.Controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

// TODO: Fix behavior to restrict each text field to one digit and auto-focus to the next field on input. UPDATE:FIXED!
public class OtpController implements Initializable {
    @FXML
    private MainSign mainScreenController;
    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private TextField textField3;

    @FXML
    private TextField textField4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize method
    }

    public void setMainScreenController(MainSign mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    private void setupTextFields() {
        // Set event handlers for all text fields
        textField1.setOnKeyTyped(this::handleDigitInput);
        textField2.setOnKeyTyped(this::handleDigitInput);
        textField3.setOnKeyTyped(this::handleDigitInput);
        textField4.setOnKeyTyped(this::handleDigitInput);
    }

    @FXML
    private void handleDigitInput(KeyEvent event) {
        TextField source = (TextField) event.getSource();

        // Allow only one digit (0-9)
        if (!event.getCharacter().matches("[0-9]")) {
            event.consume();
            return;
        }

        // Move focus to the next text field
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
                // Focus stays on textField4, or do something else like submit
                break;
            default:
                break;
        }
    }
}