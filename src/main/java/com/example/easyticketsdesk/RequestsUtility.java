package com.example.easyticketsdesk;
import java.io.*;
import java.net.*;
import java.util.*;

import com.example.easyticketsdesk.Entities.Event;
import com.example.easyticketsdesk.Entities.UserProfile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestsUtility {
    private static final String MAIN_URL = "http://localhost:8080";

    private RequestsUtility() {
        throw new IllegalStateException("Utility class");
    }

    private static HttpURLConnection createConnection(String endpoint) throws IOException {
        URL url = new URL(MAIN_URL + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty("Content-Type", "application/json");
        return connection;
    }

    private static JSONObject sendRequest(HttpURLConnection connection, JSONObject jsonInput) throws IOException, JSONException {
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInput.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
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
    }

    public static JSONObject login(String email, String password) {
        HttpURLConnection connection = null;
        try {
            connection = createConnection("/auth/login");

            JSONObject jsonInput = new JSONObject();
            jsonInput.put("email", email);
            jsonInput.put("password", password);

            return sendRequest(connection, jsonInput);
        } catch (ConnectException e) {
            System.err.println("Connection refused: Please check server availability.");
            return null;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static boolean register(String firstName, String lastName, String email, String password) {
        HttpURLConnection connection = null;
        try {
            connection = createConnection("/auth/signup");

            JSONObject jsonInput = new JSONObject();
            jsonInput.put("first_name", firstName);
            jsonInput.put("last_name", lastName);
            jsonInput.put("username", lastName); // Assuming this was intended as username
            jsonInput.put("email", email);
            jsonInput.put("password", password);

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (ConnectException e) {
            System.err.println("Connection refused: Please check server availability.");
            return false;
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
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
                e.printStackTrace();
            }
        }
        return map;
    }

    public static boolean sendOTP(String email) {
        HttpURLConnection connection = null;
        try {
            connection = createConnection("/auth/send-otp");

            String jsonInputString = email;

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static JSONObject verifyOTP(String email, Integer otp) {
        HttpURLConnection connection = null;
        try {
            connection = createConnection("/auth/verify-otp");

            JSONObject jsonInput = new JSONObject();
            jsonInput.put("email", email);
            jsonInput.put("otp", otp);

            return sendRequest(connection, jsonInput);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
        //return false;
    }

    public static boolean updatePassword(String token, String newPassword) {
        HttpURLConnection connection = null;
        try {
            // Create connection to the endpoint
            connection = createConnection("/auth/update-password");
            //URL url = new URL("/auth/update-password");
            //connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Create JSON payload for the request body
            String jsonInput = newPassword;

            // Write JSON payload to the connection output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Check response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                return true; // Password updated successfully
            } else {
                System.out.println("Failed to update password. HTTP response code: " + responseCode);
                return false;
            }
        } catch (ConnectException e) {
            System.err.println("Connection refused: Please check server availability.");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static Map<String, Boolean> getUserPreferences(String token) {
        HttpURLConnection connection = null;
        try {
            // Create connection
            URL url = new URL(MAIN_URL + "/preferences/map");
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

    public static void setUserPreferencesMapping(String token, Map<String, Boolean> preferencesMap) {
        HttpURLConnection connection = null;
        try {
            // Create connection
            URL url = new URL(MAIN_URL + "/preferences/map");
            connection = (HttpURLConnection) url.openConnection();

            // Set request method and headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Content-Type", "application/json");

            // Enable output and set request body
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream();
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"))) {
                JSONObject jsonRequest = convertMapToJson(preferencesMap);
                writer.write(jsonRequest.toString());
                writer.flush();
            }

            // Get Response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Preferences updated successfully");
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
    }

    // Helper method to convert Map<String, Boolean> to JSONObject
    private static JSONObject convertMapToJson(Map<String, Boolean> map) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            jsonObject.put(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }

    public static UserProfile getUserProfile(String token) {
        HttpURLConnection connection = null;
        try {
            // Create connection
            URL url = new URL(MAIN_URL + "/profile");
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
                    return new UserProfile(
                            jsonResponse.getString("first_name"),
                            jsonResponse.getString("last_name"),
                            jsonResponse.getString("email"),
                            jsonResponse.getString("jwt")
                    );
                } catch (IOException e) {
                    throw new RuntimeException("Error reading response", e);
                } catch (JSONException e) {
                    throw new RuntimeException("Error parsing JSON response", e);
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

    public static List<Event> getUpcomingEvents(String token) {
        HttpURLConnection connection = null;
        List<Event> events = new ArrayList<>();

        try {
            // Create connection
            URL url = new URL(MAIN_URL + "/events/possible");
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
                    JSONArray jsonArray = new JSONArray(response.toString());

                    // Parse JSON Array into List<Event>
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Event event = new Event(jsonObject);
                            events.add(event);
                        }
                        catch (Exception e){
                            System.err.println("Error occurred while trying parsing the json object to Event.");
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error reading response", e);
                } catch (JSONException e) {
                    throw new RuntimeException("Error parsing JSON response", e);
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

        return events;
    }
}
