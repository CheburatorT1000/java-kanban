package ru.yandex.practicum.tracker.managers;

import com.google.gson.*;
import ru.yandex.practicum.tracker.http.InstantAdapter;
import ru.yandex.practicum.tracker.http.KVTaskClient;
import ru.yandex.practicum.tracker.tasks.Epic;
import ru.yandex.practicum.tracker.tasks.SimpleTask;
import ru.yandex.practicum.tracker.tasks.SubTask;
import ru.yandex.practicum.tracker.tasks.TaskType;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;

public class HTTPTaskManager extends FileBackedTasksManager {

    private final URI uri;
    private final KVTaskClient kvTaskClient;
    private HTTPTaskManager restoredTaskManager;
    private Gson gson;
    private int savedID = 0;


    public HTTPTaskManager(URI uri) {
        super(null);
        gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantAdapter())
                .create();
        this.uri = uri;
        this.kvTaskClient = new KVTaskClient(uri);
    }

    @Override
    public void save() {
        String allSimpleTasks = gson.toJson(getAllSimpleTasks());
        String allEpicTasks = gson.toJson(getAllEpicTasks());
        String allSubtasks = gson.toJson(getAllSubTasks());
        String historyTasks = gson.toJson(getHistory());
        try {
            kvTaskClient.put(TaskType.SIMPLE_TASK.toString(), allSimpleTasks);
            kvTaskClient.put(TaskType.EPIC.toString(), allEpicTasks);
            kvTaskClient.put(TaskType.SUB_TASK.toString(), allSubtasks);
            kvTaskClient.put("History", historyTasks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public HTTPTaskManager restore() {
        restoredTaskManager = Managers.getDefault();
        String returnedSimpleTasks = null;
        try {
            returnedSimpleTasks = kvTaskClient.load("SIMPLE_TASK");
            String returnedEpicTasks = kvTaskClient.load("EPIC");
            String returnedSubtasks = kvTaskClient.load("SUB_TASK");
            String returnedHistoryTasks = kvTaskClient.load("History");
            JsonElement jsonElement = JsonParser.parseString(returnedSimpleTasks);
            restoreSimpleTasks(jsonElement);
            jsonElement = JsonParser.parseString(returnedEpicTasks);
            restoreEpicTasks(jsonElement);
            jsonElement = JsonParser.parseString(returnedSubtasks);
            restoreSubtasks(jsonElement);
            jsonElement = JsonParser.parseString(returnedHistoryTasks);
            restoreHistory(jsonElement);
            restoredTaskManager.setNextID(savedID);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return restoredTaskManager;
    }

    public void restoreSimpleTasks(JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                SimpleTask simpleTask = gson.fromJson(jsonArray.get(i).toString(), SimpleTask.class);
                if (simpleTask.getId() >= nextID)
                    nextID = simpleTask.getId();
                restoredTaskManager.addSimpleTask(simpleTask);
            }
        } else
            System.out.println("Json is not an object");
    }

    public void restoreEpicTasks(JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                Epic epic = gson.fromJson(jsonArray.get(i).toString(), Epic.class);
                if (epic.getId() >= savedID)
                    savedID = epic.getId();
                restoredTaskManager.addEpicTask(epic);
            }
        } else
            System.out.println("Json is not an object");
    }

    public void restoreSubtasks(JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                SubTask subTask = gson.fromJson(jsonArray.get(i).toString(), SubTask.class);
                if (subTask.getId() >= savedID)
                    savedID = subTask.getId();
                restoredTaskManager.addSubTask(subTask);
            }
        } else
            System.out.println("Json is not an object");
    }

    public void restoreHistory(JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                SimpleTask simpleTask = gson.fromJson(jsonArray.get(i).toString(), SimpleTask.class);
                restoredTaskManager.inMemoryHistoryManager.add(simpleTask);
            }
        } else
            System.out.println("Json is not an object");
    }
}
