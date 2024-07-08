package com.example.easyticketsdesk.CustomComponents;
import com.example.easyticketsdesk.Controllers.SeatsController;
import com.example.easyticketsdesk.Entities.SeatStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SeatComponent extends Button {
    private SeatStatus currentStatus;

    public SeatComponent(SeatStatus seatStatus) {
        super();
        setPrefWidth(50);
        setPrefHeight(30.0);

        // Apply different styles based on seatStatus
        switch (seatStatus) {
            case AVAILABLE:
                setAvailableStyle();
                break;
            case RESERVED:
                setReservedStyle();
                break;
            case YOUR_CHOICE:
                setYourChoiceStyle();
                break;
            default:
                break;
        }

        // Handle click event
        setOnAction(e -> handleClick());
    }

    private void setAvailableStyle() {
        this.currentStatus = SeatStatus.AVAILABLE;
        setStyle("-fx-background-color: #a4bd91; -fx-background-radius: 50;");
    }

    private void setReservedStyle() {
        this.currentStatus = SeatStatus.RESERVED;
        setStyle("-fx-background-color: #cf959d; -fx-background-radius: 50;");
    }

    private void setYourChoiceStyle() {
        this.currentStatus = SeatStatus.YOUR_CHOICE;
        setStyle("-fx-background-color: #7d916d; -fx-background-radius: 50;");
    }

    private void handleClick() {
        switch (currentStatus) {
            case AVAILABLE:
                setYourChoiceStyle();
                break;
            case RESERVED:
                break; // Nothing to do
            case YOUR_CHOICE:
                // Cancel the choosing - mark as available (it was necessarily available before)
                setAvailableStyle();
                break;
            default:
                break;
        }
    }
}
