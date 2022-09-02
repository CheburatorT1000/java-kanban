package ru.yandex.practicum.tracker.main;

import ru.yandex.practicum.tracker.managers.Managers;
import ru.yandex.practicum.tracker.managers.TaskManager;
import ru.yandex.practicum.tracker.tasks.Epic;
import ru.yandex.practicum.tracker.tasks.SimpleTask;
import ru.yandex.practicum.tracker.tasks.SubTask;

import static ru.yandex.practicum.tracker.tasks.Status.*;

public class Main {

    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        inMemoryTaskManager.addEpicTask(new Epic(0, "мега задача", "СУПЕРПУПЕР1!", NEW));
        inMemoryTaskManager.addEpicTask(new Epic(0, "ГИГА задача", "СУПЕРПУПЕР1!", NEW));
        inMemoryTaskManager.addSubTask(new SubTask(0, "33333333", "СУПЕРПУПЕР1!", NEW, 2));
        inMemoryTaskManager.addSubTask(new SubTask(0, "44444444", "СУПЕРПУПЕР1!", NEW, 2));
        inMemoryTaskManager.addSubTask(new SubTask(0, "55555555", "СУПЕРПУПЕР1!", NEW, 2));
        inMemoryTaskManager.addSubTask(new SubTask(0, "55555555", "СУПЕРПУПЕР1!", NEW, 1));

        inMemoryTaskManager.getEpicTaskByID(2);
        inMemoryTaskManager.getEpicTaskByID(1);
        inMemoryTaskManager.getSubTaskByID(5);
        inMemoryTaskManager.getSubTaskByID(5);
        inMemoryTaskManager.getSubTaskByID(5);
        inMemoryTaskManager.getSubTaskByID(5);
        inMemoryTaskManager.getSubTaskByID(3);
        inMemoryTaskManager.getSubTaskByID(4);
        inMemoryTaskManager.getSubTaskByID(5);
        inMemoryTaskManager.getSubTaskByID(6);
        inMemoryTaskManager.getSubTaskByID(5);
        inMemoryTaskManager.getSubTaskByID(3);
        inMemoryTaskManager.getEpicTaskByID(1);
        System.out.println(inMemoryTaskManager.getHistory().toString());

        inMemoryTaskManager.deleteEpicByID(2);
        System.out.println(inMemoryTaskManager.getHistory().toString());
    }
}
