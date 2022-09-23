package ru.yandex.practicum.tracker.main;

import ru.yandex.practicum.tracker.managers.FileBackedTasksManager;
import ru.yandex.practicum.tracker.managers.Managers;
import ru.yandex.practicum.tracker.managers.TaskManager;
import ru.yandex.practicum.tracker.tasks.Epic;
import ru.yandex.practicum.tracker.tasks.SimpleTask;
import ru.yandex.practicum.tracker.tasks.SubTask;

import java.io.File;
import java.io.IOException;

import static ru.yandex.practicum.tracker.tasks.Status.*;

public class Main {

    public static void main(String[] args) {
        File file = new File("src/ru/yandex/practicum/tracker/resources/save.csv");
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);
        fileBackedTasksManager.addSimpleTask(new SimpleTask(0, "мега задача", "СУПЕРПУПЕР1!", NEW));
        fileBackedTasksManager.getSimpleTaskByID(78);
        System.out.println(fileBackedTasksManager.getHistory().toString());

        /*
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        fileBackedTasksManager.addSimpleTask(new SimpleTask(0, "мега задача", "СУПЕРПУПЕР1!", NEW));
        fileBackedTasksManager.addSimpleTask(new SimpleTask(0, "мега задача", "СУПЕРПУПЕР1!", NEW));
        fileBackedTasksManager.addSimpleTask(new SimpleTask(0, "мега задача", "СУПЕРПУПЕР1!", NEW));
        fileBackedTasksManager.addSimpleTask(new SimpleTask(0, "мега задача", "СУПЕРПУПЕР1!", NEW));
        fileBackedTasksManager.addSimpleTask(new SimpleTask(0, "мега задача", "СУПЕРПУПЕР1!", NEW));
        fileBackedTasksManager.addEpicTask(new Epic(0, "мега задача", "СУПЕРПУПЕР1!", NEW));
        fileBackedTasksManager.addSubTask(new SubTask(0, "мега задача", "СУПЕРПУПЕР1!", NEW, 6));
        fileBackedTasksManager.getSimpleTaskByID(1);
        fileBackedTasksManager.getSimpleTaskByID(4);
        fileBackedTasksManager.getSimpleTaskByID(3);
        fileBackedTasksManager.getSimpleTaskByID(2);
        fileBackedTasksManager.getSimpleTaskByID(1);
        fileBackedTasksManager.getSimpleTaskByID(5);

        System.out.println(fileBackedTasksManager.getHistory().toString());*/
    }
}
