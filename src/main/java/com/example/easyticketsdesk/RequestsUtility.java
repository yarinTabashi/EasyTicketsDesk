package com.example.easyticketsdesk;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestsUtility {
    private static final String MAIN_URL = "http://localhost:8080";
    private static final String AUTH_URL = "/auth";
    private static final String LOGIN_URL = "/login";

    private RequestsUtility() {
        throw new IllegalStateException("Utility class");
    }

    public static class LoginResponse {
        private String email;
        private String token;

        public LoginResponse(String email, String token) {
            this.email = email;
            this.token = token;
        }

        public String getEmail() {
            return email;
        }

        public String getToken() {
            return token;
        }
    }

    public static LoginResponse login(String email, String password) {
        HttpURLConnection connection = null;
        try {
            // Create connection
            URL url = new URL(MAIN_URL+ AUTH_URL+ LOGIN_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            // Enable output and disable caching
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            // Create JSON payload
            String jsonInputString = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";

            // Send request
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                byte[] input = jsonInputString.getBytes("utf-8");
                wr.write(input, 0, input.length);
            }

            // Get Response
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String receivedEmail = in.readLine();
                String token = in.readLine();

                 return new LoginResponse(email, token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
}
