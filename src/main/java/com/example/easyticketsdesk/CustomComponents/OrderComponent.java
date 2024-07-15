package com.example.easyticketsdesk.CustomComponents;
import com.example.easyticketsdesk.Controllers.MainWindowController;
import com.example.easyticketsdesk.Entities.Reservation;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Custom component representing an order details view with a dynamically generated QR code.
 */
public class OrderComponent extends AnchorPane {
    @FXML
    private MainWindowController mainWindowController;
    @FXML
    private Label event_name_label, event_date_label, order_date_label, price_label, venue_label; // Order details
    @FXML
    private ImageView qrCodeImageView;
    private Reservation reservation;
    private final int QR_WIDTH = 300;
    private final int QR_HEIGHT = 300;

    public void setMainWindowController(MainWindowController mainWindowController, Reservation reservation){
        this.mainWindowController = mainWindowController;
        this.reservation = reservation;
    }

    public void setReservationDetails(){
        this.event_name_label.setText(this.reservation.getEvent().getEventName());
        this.event_date_label.setText(this.reservation.getEvent().getDateFormat());
        this.venue_label.setText(this.reservation.getEvent().getVenue());
        this.order_date_label.setText(this.reservation.getReservationDateFormat());
        setQR(this.reservation.getSerialNum().toString());
    }

    public OrderComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/order_component.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(OrderComponent.this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void setQR(String code){
        try {
            Image qrCodeImage = generateQRCodeImage(code);
            qrCodeImageView.setImage(qrCodeImage);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    // Method to generate QR code for a given text
    private Image generateQRCodeImage(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);

        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        javax.imageio.ImageIO.write(bufferedImage, "png", outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        return new Image(inputStream);
    }
}
