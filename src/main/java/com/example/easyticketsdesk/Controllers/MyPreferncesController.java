package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.CustomCategory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MyPreferncesController {

    @FXML
    private ImageView logout_image;

    @FXML
    private Label menu_explore;

    @FXML
    private Label menu_my_pref;

    @FXML
    private Label menu_my_tickets;

    @FXML
    private Button save_changes_btn;
    @FXML
    private Pane categories_pane;

    @FXML
    public void initialize() {
        // Add CustomCategory buttons
        CustomCategory category1 = new CustomCategory("Rock");
        CustomCategory category2 = new CustomCategory("Rock");
        CustomCategory category3 = new CustomCategory("Rock");
        CustomCategory category4 = new CustomCategory("Rock");
        CustomCategory category5 = new CustomCategory("Rock");
        CustomCategory category6 = new CustomCategory("Rock");

        // Set positions for the buttons (locate them in 2 lines)
        category1.setLayoutX(50);
        category1.setLayoutY(50);

        category2.setLayoutX(200);
        category2.setLayoutY(50);

        category3.setLayoutX(350);
        category3.setLayoutY(50);

        category4.setLayoutX(50);
        category4.setLayoutY(200);

        category5.setLayoutX(200);
        category5.setLayoutY(200);

        category6.setLayoutX(350);
        category6.setLayoutY(200);

        // Add buttons to the pane
        categories_pane.getChildren().addAll(category1, category2, category3, category4, category5, category6);
    }

    @FXML
    void logoutClicked(MouseEvent event) {

    }

    @FXML
    void menu_explore_clicked(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/mainwindow.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) menu_explore.getScene().getWindow();

            // Switch scene (move to the signup window)
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menu_my_pref_clicked(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/mypreferences.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) menu_my_pref.getScene().getWindow();

            // Switch scene (move to the signup window)
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void menu_my_tickets_clicked(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/myorders.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) menu_my_tickets.getScene().getWindow();

            // Switch scene (move to the signup window)
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void save_changes_clicked(MouseEvent event) {

    }

}
