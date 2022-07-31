package ru.yandex.practicum.tracker.main;

import ru.yandex.practicum.tracker.managers.TaskManager;
import ru.yandex.practicum.tracker.tasks.Epic;
import ru.yandex.practicum.tracker.tasks.SimpleTask;
import ru.yandex.practicum.tracker.tasks.SubTask;

import static ru.yandex.practicum.tracker.tasks.Status.*;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        System.out.println("Создаем задачи");
        taskManager.addSimpleTask(new SimpleTask(0, "Убраться в доме", "чистота залог здоровья" , NEW));
        taskManager.addSimpleTask(new SimpleTask(0, "Убраться во дворе", "чистота залог здоровья" , DONE));
        taskManager.addSimpleTask(new SimpleTask(0, "Убраться в шкафу", "чистота залог здоровья" , IN_PROGRESS));
        System.out.println(taskManager.getAllSimpleTasks().toString());
        System.out.println("удаляем одну");
        taskManager.deleteSimpleTaskByID(2);
        System.out.println("смотрим");
        System.out.println(taskManager.getAllSimpleTasks().toString());
        System.out.println("обновляем одну");
        taskManager.updateSimpleTask(new SimpleTask(1, "Убраться в доме", "чистота залог здоровья" , DONE));
        System.out.println("смотрим");
        System.out.println(taskManager.getAllSimpleTasks().toString());
        System.out.println("удаляем все задачи");
        taskManager.deleteAllSimpleTasks();
        System.out.println("смотрим");
        System.out.println(taskManager.getAllSimpleTasks().toString());
        System.out.println();
        System.out.println();

        System.out.println("смотрим epic-subtask");
        taskManager.addEpicTask(new Epic(0, "Сходить в кино", "Кино величайшее искусство!", NEW));
        System.out.println(taskManager.getAllEpicTasks().toString());

        taskManager.addSubTask(new SubTask(0, "выбрать фильм", "самая сложная задача", NEW, 4));
        taskManager.addSubTask(new SubTask(0, "выбрать сеанс", "скорее всего вечером", NEW, 4));
        taskManager.addSubTask(new SubTask(0, "купить билет по интернету", "легче простого!", NEW, 4));
        System.out.println(taskManager.getAllEpicTasks().toString());
        System.out.println(taskManager.getAllSubTasks().toString());

        taskManager.updateSubTask(new SubTask(6, "выбрать сеанс", "скорее всего вечером", IN_PROGRESS, 4));
        System.out.println(taskManager.getAllSubTasks().toString());
        System.out.println(taskManager.getAllEpicTasks().toString());
        taskManager.updateSubTask(new SubTask(5, "выбрать фильм", "самая сложная задача", DONE, 4));
        taskManager.updateSubTask(new SubTask(6, "выбрать сеанс", "скорее всего вечером", DONE, 4));
        taskManager.updateSubTask(new SubTask(7, "купить билет по интернету", "легче простого!", DONE, 4));
        System.out.println(taskManager.getSubTasksFromEpic(4).toString());
        System.out.println(taskManager.getAllEpicTasks().toString());
        taskManager.updateSubTask(new SubTask(6, "выбрать сеанс", "скорее всего вечером", IN_PROGRESS, 4));
        System.out.println(taskManager.getAllSubTasks().toString());
        System.out.println(taskManager.getAllEpicTasks().toString());
        taskManager.deleteSubTaskByID(6);
        System.out.println(taskManager.getAllSubTasks().toString());
        System.out.println(taskManager.getAllEpicTasks().toString());
        taskManager.deleteAllSubTasks();
        System.out.println(taskManager.getAllSubTasks().toString());
        System.out.println(taskManager.getAllEpicTasks().toString());
        taskManager.addSubTask(new SubTask(0, "выбрать фильм", "самая сложная задача", IN_PROGRESS, 4));
        System.out.println(taskManager.getAllSubTasks().toString());
        System.out.println(taskManager.getAllEpicTasks().toString());
        taskManager.updateEpicNameAndDescription(4, "пойдем в боулинг", "современное кино - стыд и позор");
        System.out.println(taskManager.getAllSubTasks().toString());
        System.out.println(taskManager.getAllEpicTasks().toString());
    }
}
