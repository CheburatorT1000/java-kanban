package ru.yandex.practicum.tracker.main;

import com.google.gson.Gson;
import ru.yandex.practicum.tracker.HTTP.HttpTaskServer;
import ru.yandex.practicum.tracker.HTTP.KVServer;
import ru.yandex.practicum.tracker.exceptions.ManagerSaveException;
import ru.yandex.practicum.tracker.managers.FileBackedTasksManager;
import ru.yandex.practicum.tracker.managers.HTTPTaskManager;
import ru.yandex.practicum.tracker.managers.Managers;
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

public class Main {

    public static void main(String[] args) throws IOException {
    }
}
