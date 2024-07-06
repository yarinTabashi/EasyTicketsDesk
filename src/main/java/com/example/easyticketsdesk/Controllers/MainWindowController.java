package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.CustomCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;

public class MainWindowController {
    @FXML
    private HBox cardsContainer;
    @FXML
    private Label menu_explore_btn;
    @FXML
    private Label menu_my_preferences_btn;
    @FXML
    private Label menu_my_tickets_btn;

    @FXML
    public void initialize() {
        menu_explore_btn.getStyleClass().add("active");

        CustomCard myCustom = new CustomCard();
        cardsContainer.getChildren().add(myCustom);
        cardsContainer.getChildren().add(new CustomCard());
        cardsContainer.getChildren().add(new CustomCard());
        cardsContainer.getChildren().add(new CustomCard());
    }

    @FXML
    void explore_clicked(MouseEvent event) {
        // Nothing to do
    }

    @FXML
    void my_tickets_clicked(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/myorders.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) menu_my_tickets_btn.getScene().getWindow();

            // Switch scene (move to the signup window)
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void my_preferences_clicked(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/mypreferences.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) menu_my_preferences_btn.getScene().getWindow();

            // Switch scene (move to the signup window)
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}