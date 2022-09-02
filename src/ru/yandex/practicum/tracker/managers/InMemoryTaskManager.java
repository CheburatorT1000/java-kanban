package ru.yandex.practicum.tracker.managers;

import ru.yandex.practicum.tracker.tasks.Epic;
import ru.yandex.practicum.tracker.tasks.SimpleTask;
import ru.yandex.practicum.tracker.tasks.SubTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.yandex.practicum.tracker.tasks.Status.*;

public class InMemoryTaskManager implements TaskManager {


    private HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    private int nextID = 0;
    private final HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();


    private int makeID() {
        return ++nextID;
    }

    // метод для определения статуса эпика
    private void updateEpicStatus(Epic epic) {
        int epicStatusCount = 0;
        for (int subTasksIdNum : epic.getSubTasksIDs()) {
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
        }
        if (epic.getSubTasksIDs().isEmpty() || epicStatusCount == 0)
            epic.setStatus(NEW);
        else if (epicStatusCount / epic.getSubTasksIDs().size() == 2)
            epic.setStatus(DONE);
        else
            epic.setStatus(IN_PROGRESS);
    }

    @Override
    public List<SimpleTask> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    // Получение списка подзадач определенного эпика
    @Override
    public List<SubTask> getSubTasksFromEpic(int id) {
        ArrayList<SubTask> subTaskList = new ArrayList<>();

        Epic epic = epics.get(id);
        for (int subTaskIds : epic.getSubTasksIDs()) {
            subTaskList.add(subTasks.get(subTaskIds));
        }
            return subTaskList;
    }

    // Получение списка всех задач
    @Override
    public List<SimpleTask> getAllSimpleTasks() {
        ArrayList<SimpleTask> simpleTasksList = new ArrayList<>();
        for (int simpleTaskID : simpleTasks.keySet()) {
            simpleTasksList.add(simpleTasks.get(simpleTaskID));
        }
        return simpleTasksList;
    }

    @Override
    public List<Epic> getAllEpicTasks() {
        ArrayList<Epic> epicTasksList = new ArrayList<>();
        for (int epicTaskID : epics.keySet()) {
            epicTasksList.add(epics.get(epicTaskID));
        }
        return epicTasksList;
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        ArrayList<SubTask> subTasksList = new ArrayList<>();
        for (int subTaskID : subTasks.keySet()) {
            subTasksList.add(subTasks.get(subTaskID));
        }
        return subTasksList;
    }

    // Удаление всех задач
    @Override
    public void deleteAllSimpleTasks() {
        for (int id : simpleTasks.keySet())
            inMemoryHistoryManager.remove(id);
        simpleTasks.clear();
    }

    @Override
    public void deleteAllEpicTasks() {
        for (int id : epics.keySet()) {
            inMemoryHistoryManager.remove(id);
        }
        for (int id : subTasks.keySet()) {
            inMemoryHistoryManager.remove(id);
        }
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void deleteAllSubTasks() {
        for ( int epicIds : epics.keySet()) {
            Epic epic = epics.get(epicIds);
            epic.getSubTasksIDs().clear();
            updateEpicStatus(epic);
        }
        for (int id : subTasks.keySet()) {
            inMemoryHistoryManager.remove(id);
        }
        subTasks.clear();
    }

    @Override
    // Получение задачи по номеру
    public SimpleTask getSimpleTaskByID(int id) {
        SimpleTask simpleTask = simpleTasks.get(id);
        inMemoryHistoryManager.add(simpleTask);
        return simpleTask;
    }

    @Override
    public Epic getEpicTaskByID(int id) {
        Epic epicTask = epics.get(id);
        inMemoryHistoryManager.add(epicTask);
        return epicTask;
    }

    @Override
    public SubTask getSubTaskByID(int id) {
        SubTask subTask = subTasks.get(id);
        inMemoryHistoryManager.add(subTask);
        return subTask;
    }

    // Создание задачи
    @Override
    public void addSimpleTask(SimpleTask task) {
        task.setId(makeID());
        simpleTasks.put(task.getId(), task);
    }

    @Override
    public void addEpicTask(Epic epic) {
        epic.setId(makeID());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addSubTask(SubTask subTask) {
        subTask.setId(makeID());
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicID());
        epic.addSubTask(subTask.getId());
        updateEpicStatus(epic);
    }

    // Обновление задачи
    @Override
    public void updateSimpleTask(SimpleTask simpleTask) {
        if (simpleTasks.containsKey(simpleTask.getId()))
            simpleTasks.put(simpleTask.getId(), simpleTask);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic epicOriginal = epics.get(epic.getId());
            epicOriginal.setName(epic.getName());
            epicOriginal.setDescription(epic.getDescription());
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicID());
            updateEpicStatus(epic);
        }
    }

    // Удаление по идентификатору
    @Override
    public void deleteSimpleTaskByID(int id) {
        inMemoryHistoryManager.remove(id);
        simpleTasks.remove(id);
    }

    @Override
    public void deleteEpicByID(int id) {
        Epic epic = epics.get(id);
        for (int subTasksIDs : epic.getSubTasksIDs()) {
            subTasks.remove(subTasksIDs);
            inMemoryHistoryManager.remove(subTasksIDs);
        }
        inMemoryHistoryManager.remove(id);
        epics.remove(id);
    }

    @Override
    public void deleteSubTaskByID(int id) {
        SubTask subTask = subTasks.get(id);
        Epic epic = epics.get(subTask.getEpicID());
        ArrayList<Integer> subTasksIDs = epic.getSubTasksIDs();
        for (int i = 0; i < subTasksIDs.size(); i++) {
            if (subTasksIDs.get(i) == id) {
                inMemoryHistoryManager.remove(i);
                subTasksIDs.remove(i);
                break;
            }
        }
        epic.setSubTasksIDs(subTasksIDs);
        updateEpicStatus(epic);
        subTasks.remove(id);
    }
}
