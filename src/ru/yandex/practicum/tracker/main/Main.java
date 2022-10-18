package ru.yandex.practicum.tracker.main;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import ru.yandex.practicum.tracker.http.HttpTaskServer;
import ru.yandex.practicum.tracker.http.KVServer;
import ru.yandex.practicum.tracker.exceptions.ManagerSaveException;
import ru.yandex.practicum.tracker.managers.FileBackedTasksManager;
import ru.yandex.practicum.tracker.managers.HTTPTaskManager;
import ru.yandex.practicum.tracker.managers.Managers;
import ru.yandex.practicum.tracker.tasks.Epic;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Main {

    public static void main(String[] args) throws IOException {

        KVServer kvServer = new KVServer();
        kvServer.start();
        HTTPTaskManager httpTaskManager = Managers.getDefault();
        HttpTaskServer httpTaskServer = new HttpTaskServer(httpTaskManager);
        httpTaskServer.serverStart();

        httpTaskManager.addSimpleTask(new SimpleTask(0, "name", "description", Status.NEW, Instant.ofEpochMilli(3),0));
        httpTaskManager.addSimpleTask(new SimpleTask(0, "name", "description", Status.NEW, Instant.ofEpochMilli(1),0));
        httpTaskManager.addSimpleTask(new SimpleTask(0, "name", "description", Status.NEW, Instant.ofEpochMilli(2),0));
        System.out.println(httpTaskManager.getAllSimpleTasks().toString());

        HTTPTaskManager httpTaskManager2 = httpTaskManager.restore();
        System.out.println(httpTaskManager2.getPrioritizedTasks().toString());
        System.out.println(httpTaskManager.getPrioritizedTasks().toString());

    }
}
