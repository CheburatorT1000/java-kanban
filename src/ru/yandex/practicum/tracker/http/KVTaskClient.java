package ru.yandex.practicum.tracker.http;

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
        try {
            registration();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void registration() throws IOException {
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
                throw new IOException("Не правильный код запроса!");
            }
        } catch (IOException | InterruptedException e) {
            throw new IOException("Не правильный код запроса!");
        }
    }

    public void put(String key, String json) throws IOException {
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
                throw new IOException("Не правильный код запроса!");
            }
        } catch (IOException | InterruptedException e) {
            throw new IOException("Не правильный код запроса!");
        }

    }

    public String load(String key) throws InterruptedException {
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
                throw new IOException("Не верный код запроса!");
            }
        } catch (IOException | InterruptedException e) {
            throw new InterruptedException("Не верный код запроса!");
        }
        value = gson.fromJson(receivedJson, String.class);
        return value;
    }
}
