package com.example.easyticketsdesk.RequestsUtilty;
import java.io.*;
import java.net.*;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthRequests {
    private static final String MAIN_URL = "http://localhost:8080";

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

    /**
     * Sign-in request with the given email and password.
     * @param email The email of the user.
     * @param password The password of the user.
     * @return A JSONObject containing the response from the server.
     */
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

    /**
     * Register request with the given first name, last name, email and password.
     * */
    public static JSONObject register(String firstName, String lastName, String email, String password)  {
        HttpURLConnection connection = null;
        try {
            connection = createConnection("/auth/signup");

            JSONObject jsonInput = new JSONObject();
            jsonInput.put("first_name", firstName);
            jsonInput.put("last_name", lastName);
            jsonInput.put("username", lastName); // Assuming this was intended as username
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

    /**
     * A request for sending OTP to the given email.
     * @param email The email to send the OTP to.
     * @return True if the OTP was sent successfully, false otherwise.
     */
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

    /**
     * A request to verify the OTP sent to the given email.
     * @param email The email to verify the OTP for.
     * @param otp The OTP to verify.
     * @return A JSONObject containing the response from the server.
     */
    public static JSONObject verifyOTP(String email, int otp) {
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
    }

    /**
     * A request to update the password for the given token.
     * @param token The token of the user.
     * @param newPassword The new password to update.
     * @return True if the password was updated successfully, false otherwise.
     */
    public static boolean updatePassword(String token, String newPassword) {
        HttpURLConnection connection = null;
        try {
            // Create connection to the endpoint
            connection = createConnection("/auth/update-password");
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

}
