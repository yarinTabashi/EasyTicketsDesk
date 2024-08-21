package com.example.easyticketsdesk.RequestsUtilty;
import java.io.*;
import java.net.*;
import java.util.*;
import com.example.easyticketsdesk.Entities.Event;
import com.example.easyticketsdesk.Entities.Reservation;
import com.example.easyticketsdesk.Entities.Seat;
import com.example.easyticketsdesk.Entities.UserProfile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Utility class for making HTTP requests to the various server endpoints.
 */
public class MainRequests {
    private static final String MAIN_URL = "http://localhost:8080";

    private static HttpURLConnection createConnection(String endpoint) throws IOException {
        URL url = new URL(MAIN_URL + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty("Content-Type", "application/json");
        return connection;
    }

    /**
     * Helper method to convert JSON object to Map<String, Boolean>
     * where the keys are the category names and the values are the boolean values.
     */
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

    /**
     * A request to get the user preferences for the given token.
     * @param token The token of the user.
     * @return A Map<String, Boolean> containing the user preferences.
     */
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

    /**
     * A request to get the user preferences for the given token.
     * @param token The token of the user.
     * @return A Set<String> containing the user preferences.
     */
    public static Set<String> getCategories(String token) {
        Map<String, Boolean> preferences = getUserPreferences(token);
        if (preferences != null) {
            return preferences.keySet();
        }
        return null;
    }

    /**
     * A request to update the user preferences for the given token.
     * @param token The token of the user.
     * @param preferencesMap A Map<String, Boolean> containing the user preferences.
     */
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

    /**
     * Helper method to convert a Map<String, Boolean> to a JSONObject.
     * @param map The Map<String, Boolean> to convert.
     * @return The JSONObject representation of the map.
     */
    private static JSONObject convertMapToJson(Map<String, Boolean> map) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            jsonObject.put(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }

    /**
     * A request to retrieve the user profile by the given token.
     * @param token The token of the user.
     * @return A UserProfile object containing the user profile details.
     */
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

    /**
     * A request to get the upcoming events.
     * @param token The token of the user.
     * @return A List<Event> containing the upcoming events.
     */
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
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Event event = new Event(jsonObject);
                            events.add(event);
                        } catch (Exception e) {
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

    /**
     * A request to get the seats mapping for the given event.
     * @param token The token of the user.
     * @param eventId The ID of the event.
     * @return A List<Seat> containing the seats mapping for the event.
     */
    public static List<Seat> getSeatsMapping(String token, int eventId) {
        HttpURLConnection connection = null;
        List<Seat> seats = new ArrayList<>();

        try {
            // Create connection
            URL url = new URL(MAIN_URL + "/seats/" + eventId);
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
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Seat seat = new Seat(jsonObject);
                            seats.add(seat);
                        } catch (Exception e) {
                            System.err.println("Error occurred while trying parsing the json object to Seat.");
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

        return seats;
    }

    /**
     *  A request to reserve a seat with the given token and seat ID.
     *  @param token The token of the user.
     *  @param seatId The ID of the seat to reserve.
     * */
    public static boolean reserve(String token, int seatId) {
        HttpURLConnection connection = null;
        try {
            // Create connection to the endpoint
            connection = createConnection("/reservations/" + seatId);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            connection.getOutputStream().close();

            // Check response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true; // Reservation created successfully
            } else {
                System.out.println("Failed to reserve seat. HTTP response code: " + responseCode);
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

    /**
     * A request to get all reservations by the given token.
     * @param token The token of the user.
     * @return A List<Reservation> containing all the reservations.
     */
    public static List<Reservation> getAllReservations(String token) {
        HttpURLConnection connection = null;
        List<Reservation> reservations = new ArrayList<>();

        try {
            // Create connection
            URL url = new URL(MAIN_URL + "/reservations");
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

                    // Parse JSON Array into List<Reservation>
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Reservation reservation = new Reservation(jsonObject);
                            reservations.add(reservation);
                        } catch (Exception e) {
                            System.err.println("Error occurred while trying to parse the json object to Reservation.");
                        }
                    }
                } catch (IOException | JSONException e) {
                    throw new RuntimeException("Error processing response", e);
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

        return reservations;
    }

    /**
     * A request to get the closest upcoming event.
     * @param token The token of the user.
     * @return An Event object containing the closest event.
     */
    public static Event getCloseEvent(String token) {
        HttpURLConnection connection = null;
        List<Reservation> reservations = new ArrayList<>();

        try {
            // Create connection
            URL url = new URL(MAIN_URL + "/reservations/closest");
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
                    JSONObject jsonObject = new JSONObject(response.toString());

                    // Parse JSON object into Event object
                    try {
                        Event event = new Event(jsonObject);
                        return event;
                    } catch (Exception e) {
                        System.err.println("Error occurred while trying to parse the json object to Event.");
                    }

                } catch (IOException | JSONException e) {
                    throw new RuntimeException("Error processing response", e);
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

    /**
     *  A request to update the mutable details of a user with the provided information.
     *  @param token The authentication token of the user.
     * */
    public static boolean updateUserDetails(String token, String firstName, String lastName, String email) {
        HttpURLConnection connection = null;

        try {
            // Create connection
            URL url = new URL(MAIN_URL + "/profile/update");
            connection = (HttpURLConnection) url.openConnection();

            // Set request method and headers
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true); // Enable output for POST request

            // Construct JSON payload
            String jsonInputString = "{\"firstName\":\"" + firstName + "\","
                    + "\"lastName\":\"" + lastName + "\","
                    + "\"email\":\"" + email + "\"}";

            // Write JSON payload to output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get Response
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_NO_CONTENT; // Assuming HTTP 204 is returned on success
        } catch (IOException e) {
            throw new RuntimeException("Error making HTTP request", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
