package com.example.easyticketsdesk;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

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

    public static Map<String, Boolean> getUserPreferences(String token) {
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
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    return convertJsonToMap(jsonResponse);
                } catch (IOException | RuntimeException e) {
                    throw new RuntimeException("Error reading response", e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.err.println("HTTP error code: " + responseCode);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error making HTTP request", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    private static Map<String, Boolean> convertJsonToMap(JSONObject jsonObject) {
        Map<String, Boolean> map = new HashMap<>();
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            try {
                boolean value = jsonObject.getBoolean(key);
                map.put(key, value);
            } catch (JSONException e) {
                // Handle JSONException appropriately
                e.printStackTrace();
            }
        }
        return map;
    }

    public static boolean sendOTP(String email) {
        HttpURLConnection connection = null;
        try {
            // Create connection
            URL url = new URL(MAIN_URL + AUTH_URL + "/send-otp");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            // Enable output and disable caching
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            // Create JSON payload
            String jsonInputString =email;

            // Send request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get Response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                return true;
            }
            else {
                System.err.println("HTTP error code: " + responseCode);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }

    public static boolean verifyOTP(String email, Integer otp) {
        HttpURLConnection connection = null;
        try {
            // Create connection
            URL url = new URL(MAIN_URL + AUTH_URL + "/verify-otp");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Create JSON payload
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("email", email);
            jsonInput.put("otp", otp);

            // Send request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get Response
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String response = in.readLine(); // Assuming response is a single line JSON like "true" or "false"
                    return Boolean.parseBoolean(response); // Convert response to boolean
                }
            } else {
                System.err.println("HTTP error code: " + responseCode);
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException("Error making HTTP request", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }
}
