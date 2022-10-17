package ru.yandex.practicum.tracker.managers;

import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import ru.yandex.practicum.tracker.HTTP.HttpTaskServer;
import ru.yandex.practicum.tracker.HTTP.KVServer;
import ru.yandex.practicum.tracker.exceptions.ManagerSaveException;
import ru.yandex.practicum.tracker.tasks.SimpleTask;
import ru.yandex.practicum.tracker.tasks.Status;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class HTTPTaskManagerTest {


    @Test
    void shouldSaveAndRestore() throws IOException {
        Gson gson = new Gson();
        KVServer kvServer = new KVServer();
        kvServer.start();
        HTTPTaskManager httpTaskManager = Managers.getDefault();
        HttpTaskServer httpTaskServer = new HttpTaskServer(httpTaskManager);
        httpTaskServer.serverStart();


        httpTaskManager.addSimpleTask(new SimpleTask(0, "name", "description", Status.NEW, Instant.ofEpochMilli(3),0));
        httpTaskManager.addSimpleTask(new SimpleTask(0, "name", "description", Status.NEW, Instant.ofEpochMilli(1),0));
        httpTaskManager.addSimpleTask(new SimpleTask(0, "name", "description", Status.NEW, Instant.ofEpochMilli(2),0));
        System.out.println(httpTaskManager.getAllSimpleTasks().toString());

        //httpTaskManager = httpTaskManager.restore();
        System.out.println("asdasd");
        System.out.println(httpTaskManager.getPrioritizedTasks().toString());


        String value = "";
        String receivedJson = "";
        HttpClient httpClient = HttpClient.newHttpClient();
        URI registerUrl = URI.create("http://localhost:8078/load/user1?API_TOKEN=DEBUG");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(registerUrl)
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
        System.out.println(value);

        File file = new File("src/test/testFile.csv");
        try (Writer fileWriter = new FileWriter(file)) {
            fileWriter.write(value);
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл!");
        }
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);
        System.out.println("Проверяем создание и соответствие");
        System.out.println(httpTaskManager.getAllSimpleTasks());
        System.out.println(fileBackedTasksManager.getAllSimpleTasks());
        System.out.println(httpTaskManager.getAllSimpleTasks().toString().equals(fileBackedTasksManager.getAllSimpleTasks().toString()));

        assertEquals(httpTaskManager.getAllSimpleTasks().toString(), fileBackedTasksManager.getAllSimpleTasks().toString(), "Восстановленные данные не совпадают");
        httpTaskServer.serverStop();
        kvServer.stop();
    }
}