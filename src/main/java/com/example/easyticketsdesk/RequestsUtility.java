package com.example.easyticketsdesk;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class RequestsUtility {
    private static final String MAIN_URL = "http://localhost:8080";
    private static final String AUTH_URL = "/auth";
    private static final String USER_PREFERENCES_URL = "/preferences";

    private RequestsUtility() {
        throw new IllegalStateException("Utility class");
    }

    public static JSONObject login(String email, String password) {
        HttpURLConnection connection = null;
        try {
            // Create connection
            URL url = new URL(MAIN_URL + AUTH_URL + "/login");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            // Enable output and disable caching
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            // Create JSON payload
            String jsonInputString = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";

            // Send request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get Response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    return new JSONObject(response.toString());
                }
            } else {
                System.err.println("HTTP error code: " + responseCode);
                return null;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }


    public static boolean register(String firstName, String lastName, String email, String password) {
        HttpURLConnection connection = null;
        try {
            // Create connection
            URL url = new URL(MAIN_URL + AUTH_URL + "/signup");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            // Enable output and disable caching
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            // Create JSON payload
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("first_name", firstName);
            jsonInput.put("last_name", lastName);
            jsonInput.put("username", lastName);
            jsonInput.put("email", email);
            jsonInput.put("password", password);

            // Send request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get Response
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }

    public static JSONObject getUserPreferences(String token) {
        HttpURLConnection connection = null;
        try {
            // Create connection
            URL url = new URL(MAIN_URL + USER_PREFERENCES_URL + "/map");
            connection = (HttpURLConnection) url.openConnection();

            // Set request method and headers
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Content-Type", "application/json");

            // Get Response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    return new JSONObject(response.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.err.println("HTTP error code: " + responseCode);
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
