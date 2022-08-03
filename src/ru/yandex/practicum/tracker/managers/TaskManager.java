package ru.yandex.practicum.tracker.managers;

import ru.yandex.practicum.tracker.tasks.*;

import java.util.ArrayList;
import java.util.HashMap;

import static ru.yandex.practicum.tracker.tasks.Status.*;

public class TaskManager {
    private int nextID = 0;
    private HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();

    private int makeID() {
        return ++nextID;
    }

    // Получение списка всех задач
    public ArrayList<SimpleTask> getAllSimpleTasks() {
        ArrayList<SimpleTask> simpleTasksList = new ArrayList<>();
        for (int simpleTaskID : simpleTasks.keySet()) {
            simpleTasksList.add(simpleTasks.get(simpleTaskID));
        }
        return simpleTasksList;
    }

    public ArrayList<Epic> getAllEpicTasks() {
        ArrayList<Epic> epicTasksList = new ArrayList<>();
        for (int epicTaskID : epics.keySet()) {
            epicTasksList.add(epics.get(epicTaskID));
        }
        return epicTasksList;
    }

    public ArrayList<SubTask> getAllSubTasks() {
        ArrayList<SubTask> subTasksList = new ArrayList<>();
        for (int subTaskID : subTasks.keySet()) {
            subTasksList.add(subTasks.get(subTaskID));
        }
        return subTasksList;
    }

    // Удаление всех задач
    public void deleteAllSimpleTasks() {
        simpleTasks.clear();
    }

    public void deleteAllEpicTasks() {
        for ( int epicKey : epics.keySet() ) {
            Epic epicToDelete = epics.get(epicKey);
            ArrayList<Integer> subTasksIDs = epicToDelete.getSubTasksIDs();
            for (int subTasksID : subTasksIDs) {
                subTasks.remove(subTasksID);
            }
        }
        epics.clear();
    }

    public void deleteAllSubTasks() {
        for (int subTasksKey : subTasks.keySet()) {
            SubTask subTask = subTasks.get(subTasksKey);
            int epicId = subTask.getEpicID();
            Epic epic = epics.get(epicId);
            epic.getSubTasksIDs().clear();
            updateEpicStatus(epic);
        }
        subTasks.clear();
    }

    // Получение задачи по номеру
    public SimpleTask getSimpleTaskByID(int id) {
        SimpleTask simpleTask = simpleTasks.get(id);
        return simpleTask;
    }

    public Epic getEpicTaskByID(int id) {
        Epic epicTask = epics.get(id);
        return epicTask;
    }

    public SubTask getSubTaskByID(int id) {
        SubTask subTask = subTasks.get(id);
        return subTask;
    }

    // Создание задачи
    public void addSimpleTask(SimpleTask task) {
        task.setId(makeID());
        simpleTasks.put(task.getId(), task);
    }

    public void addEpicTask(Epic epic) {
        epic.setId(makeID());
        epics.put(epic.getId(), epic);
    }

    public void addSubTask(SubTask subTask) {
        subTask.setId(makeID());
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicID());
        epic.addSubTask(subTask.getId());
        updateEpicStatus(epic);
    }

    // Обновление задачи
    public void updateSimpleTask(SimpleTask simpleTask) {
        simpleTasks.put(simpleTask.getId(), simpleTask);
    }
/*
    Тут мне надо было чуть дольше подумать), мы берем оригинальный обьект копируем в него только разрешенные переменные
*/
    public void updateEpic(Epic epic) {
        Epic epicOriginal = epics.get(epic.getId());
        epicOriginal.setName(epic.getName());
        epicOriginal.setDescription(epic.getDescription());
    }
    public void updateEpicNameAndDescription(int epicID, String name, String description) {
        Epic epic = epics.get(epicID);
        epic.setName(name);
        epic.setDescription(description);
        epics.put(epic.getId(), epic);
    }

    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicID());
        updateEpicStatus(epic);
    }
    // Удаление по идентификатору
    public void deleteSimpleTaskByID(int id) {
        simpleTasks.remove(id);
    }

    public void deleteEpicByID(int id) {
        Epic epic = epics.get(id);
        for ( int subTasksIDs : epic.getSubTasksIDs()) {
            subTasks.remove(subTasksIDs);
        }
        epics.remove(id);
    }

    public void deleteSubTaskByID(int id) {
        SubTask subTask = subTasks.get(id);
        Epic epic = epics.get(subTask.getEpicID());
        ArrayList<Integer> subTasksIDs = epic.getSubTasksIDs();
        for ( int i = 0; i < subTasksIDs.size(); i++) {
            if (subTasksIDs.get(i) == id ) {
                subTasksIDs.remove(i);
                break;
            }
        }
        epic.setSubTasksIDs(subTasksIDs);
        updateEpicStatus(epic);
        subTasks.remove(id);
    }

    // Получение списка подзадач определенного эпика
    public ArrayList<Integer> getSubTasksFromEpic(int id) {
        Epic epic = epics.get(id);
        ArrayList<Integer> subTasksIDsList = epic.getSubTasksIDs();
        return subTasksIDsList;
    }

    // метод для определения статуса эпика
    private void updateEpicStatus(Epic epic) {
        int epicStatusCount = 0;
        for ( int subTasksIdNum : epic.getSubTasksIDs()) {
            SubTask subTask = subTasks.get(subTasksIdNum);
            switch (subTask.getStatus()) {
                case IN_PROGRESS: {
                    epicStatusCount++;
                    break;
                }
                case DONE: {
                    epicStatusCount += 2;
                    break;
                }
            }
        }//старался сделать код проще
        if ( epic.getSubTasksIDs().isEmpty() )
            epic.setStatus(NEW);
        else if ( epicStatusCount == 0 )
            epic.setStatus(NEW);
        else if (epicStatusCount / epic.getSubTasksIDs().size() == 2)
            epic.setStatus(DONE);
        else
            epic.setStatus(IN_PROGRESS);
    }
}