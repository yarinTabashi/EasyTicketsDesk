package com.example.easyticketsdesk;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.image.Image;

public class ImageLoadingService extends Service<Image> {

    private final String imageUrl;

    public ImageLoadingService(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    protected Task<Image> createTask() {
        return new Task<>() {
            @Override
            protected Image call() throws Exception {
                return new Image(imageUrl);
            }
        };
    }
}