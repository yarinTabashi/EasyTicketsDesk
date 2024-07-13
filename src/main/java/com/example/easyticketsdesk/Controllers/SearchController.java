package com.example.easyticketsdesk.Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SearchController {
    @FXML
    private MainWindowController mainWindowController;
    @FXML
    private TextField search_field;
    @FXML
    private Button search_btn;
    @FXML
    private Label welcome_label;

    public void setMainWindowController(MainWindowController mainWindowController){
        this.mainWindowController = mainWindowController;
    }

    public void initializeScreen(){
        this.welcome_label.setText(mainWindowController.getWelcomeText());
    }

    public void searchClicked(MouseEvent mouseEvent) {

    }
}
