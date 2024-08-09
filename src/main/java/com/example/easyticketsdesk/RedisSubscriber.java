package com.example.easyticketsdesk;

import javafx.scene.control.Alert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisSubscriber extends JedisPubSub implements Runnable {

    private final String redisHost;
    private final int redisPort;
    private final String channelName;

    public RedisSubscriber(String redisHost, int redisPort, String  channelName) {
        this.redisHost = redisHost;
        this.redisPort = redisPort;
        this.channelName = channelName;
    }

    @Override
    public void run() {
        try (Jedis jedis = new Jedis(redisHost, redisPort)) {
            jedis.subscribe(this, channelName);
        }
    }

    @Override
    public void onMessage(String channel, String message) {
        // Handle the message received from Redis
        javafx.application.Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hurry up");
            alert.setHeaderText(message); // You can set a header if you want
            alert.setContentText("Please refresh to see the updated seat availability. "); // Set the message content

            // Show the alert and wait for the user to close it
            alert.showAndWait();
            System.out.println("Received message: " + message);
        });
    }
}
