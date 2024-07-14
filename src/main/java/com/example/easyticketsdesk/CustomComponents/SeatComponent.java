package com.example.easyticketsdesk.CustomComponents;
import com.example.easyticketsdesk.Controllers.SeatsController;
import com.example.easyticketsdesk.Entities.Seat;
import com.example.easyticketsdesk.Entities.SeatStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Custom button representing a seat with dynamic styling based on its status.
 * Handles click events to toggle seat selection and communicates with a SeatsController.
 */
public class SeatComponent extends Button {
    @FXML
    private SeatsController seatsController;
    private SeatStatus currentStatus;
    private Seat seat;

    public void setSeatsController(SeatsController seatsController) {
        this.seatsController = seatsController;
    }

    // Initializes the button based on seatStatus.
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

    /**
     * Sets the button style to indicate the seat is available.
     */
    private void setAvailableStyle() {
        this.currentStatus = SeatStatus.AVAILABLE;
        setStyle("-fx-background-color: #a4bd91; -fx-background-radius: 50;");
    }

    /**
     * Sets the button style to indicate the seat is reserved.
     */
    private void setReservedStyle() {
        this.currentStatus = SeatStatus.RESERVED;
        setStyle("-fx-background-color: #cf959d; -fx-background-radius: 50;");
    }

    /**
     * Sets the button style to indicate the seat is the user's choice.
     */
    private void setYourChoiceStyle() {
        this.currentStatus = SeatStatus.YOUR_CHOICE;
        setStyle("-fx-background-color: #7d916d; -fx-background-radius: 50;");
    }

    /**
     * Handles the click event on the seat button. It updates the seat status and notifies the SeatsController.
     */
    private void handleClick() {
        this.seatsController.setChosenSeat(this.seat);

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

    public void setSeat(Seat seat){
        this.seat = seat;
    }
}
