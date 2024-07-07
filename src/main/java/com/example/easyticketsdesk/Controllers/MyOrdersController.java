package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.OrderComponent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.scene.layout.VBox;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MyOrdersController {
    @FXML
    private VBox ordersContainer;
//    @FXML
//    private ImageView qrCodeImageView;
//    @FXML
//    private ImageView qrCodeImageView2;

    @FXML
    public void initialize() {
        ordersContainer.getChildren().add(new OrderComponent());
        ordersContainer.getChildren().add(new OrderComponent());
        ordersContainer.getChildren().add(new OrderComponent());
    }


//        // Generate QR code when the window is opened
//        String text = "1234"; // Replace with your desired code or text
//        int width = 300;
//        int height = 300;
//
//        try {
//            Image qrCodeImage = generateQRCodeImage(text, width, height);
//            qrCodeImageView.setImage(qrCodeImage);
//            qrCodeImageView2.setImage(qrCodeImage);
//        } catch (WriterException | IOException e) {
//            e.printStackTrace();
//        }


    // Method to generate QR code for a given text
    private Image generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        javax.imageio.ImageIO.write(bufferedImage, "png", outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        return new Image(inputStream);
    }
}
