package ru.yandex.practicum.tracker.managers;

import com.google.gson.Gson;
import ru.yandex.practicum.tracker.HTTP.KVTaskClient;
import ru.yandex.practicum.tracker.exceptions.ManagerSaveException;
import ru.yandex.practicum.tracker.tasks.Epic;
import ru.yandex.practicum.tracker.tasks.SimpleTask;
import ru.yandex.practicum.tracker.tasks.SubTask;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

public class HTTPTaskManager extends FileBackedTasksManager {

    private final URI uri;
    private final KVTaskClient kvTaskClient;
    private Gson gson;
    private String savedFile;


    public HTTPTaskManager(URI uri) {
        super(null);
        gson = new Gson();
        this.uri = uri;
        this.kvTaskClient = new KVTaskClient(uri);
        savedFile = "";
    }

    @Override
    public void save() {
        super.save();
        try {
            savedFile = Files.readString(Path.of(file.getPath()));
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка чтения с файла");
        }
        kvTaskClient.put("user1", savedFile);
    }

    public HTTPTaskManager restore() {
        HTTPTaskManager temporaryHTTPTaskManager = new HTTPTaskManager(uri);
        FileBackedTasksManager restoredFileBackedTasksManager;
        savedFile = kvTaskClient.load("user1");
        try (Writer fileWriter = new FileWriter(file)) {
            fileWriter.write(savedFile);
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл!");
        }
        restoredFileBackedTasksManager = loadFromFile(file);

        for (SimpleTask simpleTask : restoredFileBackedTasksManager.getAllSimpleTasks()) {
            temporaryHTTPTaskManager.simpleTasks.put(simpleTask.getId(), simpleTask);
        }
        for (Epic epic : restoredFileBackedTasksManager.getAllEpicTasks()) {
            temporaryHTTPTaskManager.simpleTasks.put(epic.getId(), epic);
        }
        for (SubTask subTask : restoredFileBackedTasksManager.getAllSubTasks()) {
            temporaryHTTPTaskManager.simpleTasks.put(subTask.getId(), subTask);
        }
        for (SimpleTask simpleTask : restoredFileBackedTasksManager.getHistory()) {
            temporaryHTTPTaskManager.inMemoryHistoryManager.add(simpleTask);
        }
         return temporaryHTTPTaskManager;
    }
}
