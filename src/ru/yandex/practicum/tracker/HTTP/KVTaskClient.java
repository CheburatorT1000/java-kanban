package ru.yandex.practicum.tracker.HTTP;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    private final URI receivedUri;
    private HttpClient httpClient;
    private String apiToken;
    private Gson gson;

    public KVTaskClient(URI receivedUri) {
        gson = new Gson();
        this.receivedUri = receivedUri;
        registration();
    }

    private void registration() {
        httpClient = HttpClient.newHttpClient();
        URI registerUrl = URI.create(receivedUri + "/register");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(registerUrl)
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                apiToken = httpResponse.body();
                System.out.println("KVClient Token " + apiToken);
            } else {
                System.out.println("Error. Server returned a status code " + httpResponse.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Request error!");
        }
    }

    public void put(String key, String json) {
        URI putUrl = URI.create(receivedUri + "/save/" + key + "?API_TOKEN=" + apiToken);
        System.out.println(putUrl);
        String value = gson.toJson(json);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(putUrl)
                .header("Accept", "application/json")
                .POST((HttpRequest.BodyPublishers.ofString(value)))
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                System.out.println("\n Data was successfully transferred and saved!");
            } else {
                System.out.println("Error. Server returned a status code " + httpResponse.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Request error!");
        }

    }

    public String load(String key) {
        String value = "";
        String receivedJson = "";
        URI loadUrl = URI.create(receivedUri + "/load/" + key + "?API_TOKEN=" + apiToken);
        System.out.println(loadUrl);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(loadUrl)
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                receivedJson = httpResponse.body();
            } else {
                System.out.println("Error. Server returned a status code " + httpResponse.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Request error!");
        }
        value = gson.fromJson(receivedJson, String.class);
        return value;
    }
}
