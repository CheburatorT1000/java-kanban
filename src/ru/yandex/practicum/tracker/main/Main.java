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

        System.out.println("Создаем задачи");
        inMemoryTaskManager.addSimpleTask(new SimpleTask(0, "Убраться в доме", "чистота залог здоровья", NEW));
        inMemoryTaskManager.addSimpleTask(new SimpleTask(0, "Убраться во дворе", "чистота залог здоровья", DONE));
        inMemoryTaskManager.addSimpleTask(new SimpleTask(0, "Убраться в шкафу", "чистота залог здоровья", IN_PROGRESS));
        System.out.println(inMemoryTaskManager.getAllSimpleTasks().toString());
        System.out.println("удаляем одну");
        inMemoryTaskManager.deleteSimpleTaskByID(2);
        System.out.println("смотрим");
        System.out.println(inMemoryTaskManager.getAllSimpleTasks().toString());
        System.out.println("обновляем одну");
        inMemoryTaskManager.updateSimpleTask(new SimpleTask(1, "Убраться в доме", "чистота залог здоровья", DONE));
        System.out.println("смотрим");
        System.out.println(inMemoryTaskManager.getAllSimpleTasks().toString());
        System.out.println("удаляем все задачи");
        inMemoryTaskManager.deleteAllSimpleTasks();
        System.out.println("смотрим");
        System.out.println(inMemoryTaskManager.getAllSimpleTasks().toString());
        System.out.println();
        System.out.println();

        System.out.println("смотрим epic-subtask");
        inMemoryTaskManager.addEpicTask(new Epic(0, "Сходить в кино", "Кино величайшее искусство!", NEW));
        System.out.println(inMemoryTaskManager.getAllEpicTasks().toString());

        inMemoryTaskManager.addSubTask(new SubTask(0, "выбрать фильм", "самая сложная задача", NEW, 4));
        inMemoryTaskManager.addSubTask(new SubTask(0, "выбрать сеанс", "скорее всего вечером", NEW, 4));
        inMemoryTaskManager.addSubTask(new SubTask(0, "купить билет по интернету", "легче простого!", NEW, 4));
        System.out.println(inMemoryTaskManager.getAllEpicTasks().toString());
        System.out.println(inMemoryTaskManager.getAllSubTasks().toString());

        inMemoryTaskManager.updateSubTask(new SubTask(6, "выбрать сеанс", "скорее всего вечером", IN_PROGRESS, 4));
        System.out.println(inMemoryTaskManager.getAllSubTasks().toString());
        System.out.println(inMemoryTaskManager.getAllEpicTasks().toString());
        inMemoryTaskManager.updateSubTask(new SubTask(5, "выбрать фильм", "самая сложная задача", DONE, 4));
        inMemoryTaskManager.updateSubTask(new SubTask(6, "выбрать сеанс", "скорее всего вечером", DONE, 4));
        inMemoryTaskManager.updateSubTask(new SubTask(7, "купить билет по интернету", "легче простого!", DONE, 4));
        System.out.println(inMemoryTaskManager.getSubTasksFromEpic(4).toString());
        System.out.println(inMemoryTaskManager.getAllEpicTasks().toString());
        inMemoryTaskManager.updateSubTask(new SubTask(6, "выбрать сеанс", "скорее всего вечером", IN_PROGRESS, 4));
        System.out.println(inMemoryTaskManager.getAllSubTasks().toString());
        System.out.println(inMemoryTaskManager.getAllEpicTasks().toString());
        inMemoryTaskManager.deleteSubTaskByID(6);
        System.out.println(inMemoryTaskManager.getAllEpicTasks().toString());
        System.out.println(inMemoryTaskManager.getAllSubTasks().toString());
        inMemoryTaskManager.deleteAllSubTasks();
        System.out.println(inMemoryTaskManager.getAllEpicTasks().toString());
        System.out.println(inMemoryTaskManager.getAllSubTasks().toString());
        inMemoryTaskManager.addSubTask(new SubTask(0, "выбрать фильм", "самая сложная задача", NEW, 4));
        inMemoryTaskManager.addSubTask(new SubTask(0, "выбрать фильм", "самая сложная задача", DONE, 4));
        System.out.println(inMemoryTaskManager.getAllEpicTasks().toString());
        System.out.println(inMemoryTaskManager.getAllSubTasks().toString());
        inMemoryTaskManager.updateEpic(new Epic(4, "!!!!!!!!!!!", "!!!!!!!!!!!!!!", NEW));
        System.out.println(inMemoryTaskManager.getAllEpicTasks().toString());
        System.out.println(inMemoryTaskManager.getAllSubTasks().toString());
        inMemoryTaskManager.addSimpleTask(new SimpleTask(0, "Убраться в доме", "чистота залог здоровья", NEW));
        inMemoryTaskManager.getEpicTaskByID(4);
        inMemoryTaskManager.getEpicTaskByID(4);
        inMemoryTaskManager.getSubTaskByID(8);
        inMemoryTaskManager.getEpicTaskByID(4);
        inMemoryTaskManager.getSimpleTaskByID(10);
        inMemoryTaskManager.getEpicTaskByID(4);
        inMemoryTaskManager.getEpicTaskByID(4);
        inMemoryTaskManager.getEpicTaskByID(4);
        inMemoryTaskManager.getEpicTaskByID(4);
        inMemoryTaskManager.getSubTaskByID(8);
        inMemoryTaskManager.getEpicTaskByID(4);
        inMemoryTaskManager.getEpicTaskByID(4);
        inMemoryTaskManager.getEpicTaskByID(4);
        inMemoryTaskManager.getSimpleTaskByID(10);
        System.out.println(inMemoryTaskManager.getHistory().toString());
    }
}
