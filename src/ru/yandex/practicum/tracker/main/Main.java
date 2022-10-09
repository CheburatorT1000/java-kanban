package ru.yandex.practicum.tracker.main;

import ru.yandex.practicum.tracker.managers.FileBackedTasksManager;
import ru.yandex.practicum.tracker.managers.Managers;
import ru.yandex.practicum.tracker.managers.TaskManager;
import ru.yandex.practicum.tracker.tasks.Epic;
import ru.yandex.practicum.tracker.tasks.SimpleTask;
import ru.yandex.practicum.tracker.tasks.SubTask;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import static ru.yandex.practicum.tracker.tasks.Status.*;

public class Main {

    public static void main(String[] args) {
        File file = new File("src/ru/yandex/practicum/tracker/resources/save.csv");
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        fileBackedTasksManager.addEpicTask(new Epic(0, "мега задача", "123!", NEW, Instant.now(), 0));
        fileBackedTasksManager.addSubTask(new SubTask(0, "мега задача", "123!", NEW, Instant.EPOCH,40,1));
        fileBackedTasksManager.getEpicTaskByID(1);
        System.out.println(fileBackedTasksManager.getHistory().toString());
    }
}
