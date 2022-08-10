package ru.yandex.practicum.tracker.managers;

import ru.yandex.practicum.tracker.tasks.*;

import java.util.ArrayList;

public interface TaskManager {
    public ArrayList<Integer> getSubTasksFromEpic(int id);
    public ArrayList<SimpleTask> getAllSimpleTasks();
    public ArrayList<Epic> getAllEpicTasks();
    public ArrayList<SubTask> getAllSubTasks();
    public void deleteAllSimpleTasks();
    public void deleteAllEpicTasks();
    public void deleteAllSubTasks();
    public SimpleTask getSimpleTaskByID(int id);
    public Epic getEpicTaskByID(int id);
    public SubTask getSubTaskByID(int id);
    public void addSimpleTask(SimpleTask task);
    public void addEpicTask(Epic epic);
    public void addSubTask(SubTask subTask);
    public void updateSimpleTask(SimpleTask simpleTask);
    public void updateEpic(Epic epic);
    public void updateSubTask(SubTask subTask);
    public void deleteSimpleTaskByID(int id);
    public void deleteEpicByID(int id);
    public void deleteSubTaskByID(int id);
    public void getHistory();

}