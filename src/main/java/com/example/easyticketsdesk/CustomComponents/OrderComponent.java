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

public class OrderComponent extends AnchorPane {
    @FXML
    private MainWindowController mainWindowController;
    @FXML
    private Label event_name_label;
    @FXML
    private Label event_date_label;
    @FXML
    private Label order_date_label;
    @FXML
    private Label price_label;
    @FXML
    private Label venue_label;
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
        setQR();
    }

    @FXML
    public void setQR(){
        try {
            Image qrCodeImage = generateQRCodeImage("1234");
            qrCodeImageView.setImage(qrCodeImage);
            //qrCodeImageView2.setImage(qrCodeImage);
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

    @FXML
    public void setDetails(){
        this.event_name_label.setText("");
        this.order_date_label.setText("");
        this.price_label.setText("");
        this.venue_label.setText("");
        this.event_date_label.setText("");
    }
}
