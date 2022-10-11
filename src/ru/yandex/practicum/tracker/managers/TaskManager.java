package ru.yandex.practicum.tracker.managers;

import ru.yandex.practicum.tracker.tasks.*;

import java.util.List;

public interface TaskManager {

    List<SimpleTask> getPrioritizedTasks();
    List<SubTask> getSubTasksFromEpic(int id);

    List<SimpleTask> getAllSimpleTasks();

    List<Epic> getAllEpicTasks();

    List<SubTask> getAllSubTasks();

    void deleteAllSimpleTasks();

    void deleteAllEpicTasks();

    void deleteAllSubTasks();

    SimpleTask getSimpleTaskByID(int id);

    Epic getEpicTaskByID(int id);

    SubTask getSubTaskByID(int id);

    void addSimpleTask(SimpleTask task);

    void addEpicTask(Epic epic);

    void addSubTask(SubTask subTask);

    void updateSimpleTask(SimpleTask simpleTask);

    void updateEpic(Epic epic);

    void updateSubTask(SubTask subTask);

    void deleteSimpleTaskByID(int id);

    void deleteEpicByID(int id);

    void deleteSubTaskByID(int id);

    List<SimpleTask> getHistory();

}