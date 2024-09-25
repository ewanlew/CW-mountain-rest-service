/**
 * Connector/client layer in the REST server.
 *
 * @Author Ewan Lewis
 */

package com.example.MountainClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MountainConnector {

    /**
     * Client used to handle HTTP responses
     */
    private final HttpClient CLIENT = HttpClient.newHttpClient();

    /**
     * Base URI that all server interactions use
     */
    private final String BASE_URI;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Constructor for the "client"
     * @param baseUri URI to be used for the server
     */
    public MountainConnector(String baseUri) {
        BASE_URI = baseUri;
    }

    /**
     * Requests for mountains to be added to the list of mountains
     * @param mountains List of mountains to be added
     * @return Response
     */
    public Optional<Response> addMountains(List<Mountain> mountains) {
        try {

            // JSON string to be passed through the body of the request
            String json = MAPPER.writeValueAsString(mountains);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URI))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.OK.value() || response.statusCode() == HttpStatus.CREATED.value()
                    || response.statusCode() == HttpStatus.CONFLICT.value()) {
                return Optional.of(new Response(new ArrayList<>(), response));
            } else {
                return Optional.empty();
            }

        } catch (IOException | InterruptedException e) {
            return Optional.empty();
        }
    }

    /**
     * Requests for all the mountains currently in the server
     * @return Response containing all mountains
     */
    public Optional<Response> getAll() {
        try {
            URI getUri = new URI(BASE_URI + "mountains");
            HttpRequest request = HttpRequest.newBuilder().uri(getUri).GET().build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.OK.value()) {
                List<Mountain> mountains = MAPPER.readValue(response.body(), new TypeReference<>() {});
                return Optional.of(new Response(mountains, response));
            } else {
                return Optional.empty();
            }

        } catch (IOException | InterruptedException | URISyntaxException e) {
            return Optional.empty();
        }
    }

    /**
     * Requests to see all mountains within a certain country
     * @param param1 country to search for mountains within
     * @return Response containing all mountains within the country
     */
    public Optional<Response> getByCountry(String param1){
        try {

            URI getUri = new URI(BASE_URI + "mountains?country=" + param1);

            HttpRequest request = HttpRequest.newBuilder().uri(getUri).GET().build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.OK.value()) {
                List<Mountain> mountains = MAPPER.readValue(response.body(), new TypeReference<>() {});
                return Optional.of(new Response(mountains, response));
            } else {
                return Optional.empty();
            }

        } catch (IOException | InterruptedException | URISyntaxException e) {
            return Optional.empty();
        }
    }

    /**
     * Requests to see all mountains, searching by country and range
     * @param param1 Country to search
     * @param param2 Range to search
     * @return Response containing all mountains within the country & range
     */
    public Optional<Response> getByCountryAndRange(String param1, String param2){
        try {

            URI getUri = new URI(BASE_URI + "mountains?country=" + param1 + "&range=" + param2);

            HttpRequest request = HttpRequest.newBuilder().uri(getUri).GET().build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.OK.value()) {
                List<Mountain> mountains = MAPPER.readValue(response.body(), new TypeReference<>() {
                });
                return Optional.of(new Response(mountains, response));
            } else {
                return Optional.empty();
            }

        } catch (IOException | InterruptedException | URISyntaxException e) {
            return Optional.empty();
        }
    }

    /**
     * Requests to see all mountains, searching by halves of hemisphere
     * @param param1 True if in north hemisphere, false if in south
     * @return Response containing all mountains within desired hemisphere
     */
    public Optional<Response> getByHemisphere(Boolean param1){
        try {

            URI getUri = new URI(BASE_URI + "mountains?north=" + param1.toString());

            HttpRequest request = HttpRequest.newBuilder().uri(getUri).GET().build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.OK.value()) {
                List<Mountain> mountains = MAPPER.readValue(response.body(), new TypeReference<>() {
                });
                return Optional.of(new Response(mountains, response));
            } else {
                return Optional.empty();
            }

        } catch (IOException | InterruptedException | URISyntaxException e) {
            return Optional.empty();
        }
    }

    /**
     * Requests to see all mountains, searching by country and minimum altitude
     * @param param1 Country to search
     * @param param2 Minimum altitude
     * @return Response containing all mountains in the country, and above the altitude given
     */
    public Optional<Response> getByCountryAltitude(String param1, int param2){
        try {

            URI getUri = new URI(BASE_URI + "mountains?country=" + param1 + "&alt=" + param2);

            HttpRequest request = HttpRequest.newBuilder().uri(getUri).GET().build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.OK.value()) {
                List<Mountain> mountains = MAPPER.readValue(response.body(), new TypeReference<>() {
                });
                return Optional.of(new Response(mountains, response));
            } else {
                return Optional.empty();
            }

        } catch (IOException | InterruptedException | URISyntaxException e) {
            return Optional.empty();
        }
    }

    /**
     * Searches for specific mountain, using the country, range, and name
     * @param param1 Country of the mountain
     * @param param2 Range the mountain's in
     * @param param3 Name of the mountain
     * @return Response, containing the mountain that the criteria fit
     */
    public Optional<Response> getByName(String param1, String param2, String param3){
        try {

            URI getUri = new URI(BASE_URI + "mountains?country=" + param1 + "&range=" + param2 + "&name=" + param3);

            HttpRequest request = HttpRequest.newBuilder().uri(getUri).GET().build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.OK.value()) {
                List<Mountain> mountains = MAPPER.readValue(response.body(), new TypeReference<>() {
                });
                return Optional.of(new Response(mountains, response));
            } else {
                return Optional.empty();
            }

        } catch (IOException | InterruptedException | URISyntaxException e) {
            return Optional.empty();
        }
    }

    /**
     * Searches for mountains by specific ID
     * @param param1 ID of the mountain
     * @return Mountain that matches the ID
     */
    public Optional<Response> getById(int param1){
        try {

            URI getUri = new URI(BASE_URI + "mountains?id=" + param1);

            HttpRequest request = HttpRequest.newBuilder().uri(getUri).GET().build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.OK.value()) {
                List<Mountain> mountains = MAPPER.readValue(response.body(), new TypeReference<>() {
                });
                return Optional.of(new Response(mountains, response));
            } else {
                return Optional.empty();
            }

        } catch (IOException | InterruptedException | URISyntaxException e) {
            return Optional.empty();
        }
    }

    /**
     * Update a mountain's information
     * @param id ID of the mountain to be updated
     * @param mountain New mountain information
     * @return Response
     */
    public Optional<Response> updateMountain(int id, Mountain mountain){
        try {

            URI getUri = new URI(BASE_URI + "mountains/update/" + id);
            String json = MAPPER.writeValueAsString(mountain);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(getUri)
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.OK.value()) {
                return Optional.of(new Response(new ArrayList<>(), response));
            } else {
                return Optional.empty();
            }

        } catch (IOException | InterruptedException | URISyntaxException e) {
            return Optional.empty();
        }
    }

    /**
     * Delete a mountain's information
     * @param id ID of the mountain to be deleted
     * @return Response
     */
    public Optional<Response> deleteMountain(int id){
        try {

            URI getUri = new URI(BASE_URI + "mountains/delete");
            String json = MAPPER.writeValueAsString(id);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(getUri)
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.OK.value()) {
                return Optional.of(new Response(new ArrayList<>(), response));
            } else {
                return Optional.empty();
            }

        } catch (IOException | InterruptedException | URISyntaxException e) {
            return Optional.empty();
        }
    }

}
