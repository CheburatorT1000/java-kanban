package ru.yandex.practicum.tracker.managers;

import ru.yandex.practicum.tracker.tasks.Epic;
import ru.yandex.practicum.tracker.tasks.SimpleTask;
import ru.yandex.practicum.tracker.tasks.SubTask;

import java.util.ArrayList;
import java.util.HashMap;

import static ru.yandex.practicum.tracker.tasks.Status.*;

public class InMemoryTaskManager implements TaskManager {


    HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    private int nextID = 0;
    private HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();


    private int makeID() {
        return ++nextID;
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
        }
        if ( epic.getSubTasksIDs().isEmpty() || epicStatusCount == 0 )
            epic.setStatus(NEW);
        else if (epicStatusCount / epic.getSubTasksIDs().size() == 2)
            epic.setStatus(DONE);
        else
            epic.setStatus(IN_PROGRESS);
    }


    public void getHistory() {
    }

    // Получение списка подзадач определенного эпика
    @Override
    public ArrayList<Integer> getSubTasksFromEpic(int id) {
        Epic epic = epics.get(id);
        ArrayList<Integer> subTasksIDsList = epic.getSubTasksIDs();
        return subTasksIDsList;
    }

    // Получение списка всех задач
    @Override
    public ArrayList<SimpleTask> getAllSimpleTasks() {
        ArrayList<SimpleTask> simpleTasksList = new ArrayList<>();
        for (int simpleTaskID : simpleTasks.keySet()) {
            simpleTasksList.add(simpleTasks.get(simpleTaskID));
        }
        return simpleTasksList;
    }

    @Override
    public ArrayList<Epic> getAllEpicTasks() {
        ArrayList<Epic> epicTasksList = new ArrayList<>();
        for (int epicTaskID : epics.keySet()) {
            epicTasksList.add(epics.get(epicTaskID));
        }
        return epicTasksList;
    }

    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        ArrayList<SubTask> subTasksList = new ArrayList<>();
        for (int subTaskID : subTasks.keySet()) {
            subTasksList.add(subTasks.get(subTaskID));
        }
        return subTasksList;
    }

    // Удаление всех задач
    @Override
    public void deleteAllSimpleTasks() {
        simpleTasks.clear();
    }

    @Override
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

    @Override
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
        if ( simpleTasks.containsKey(simpleTask.getId()) )
            simpleTasks.put(simpleTask.getId(), simpleTask);
    }
    /*
        Тут мне надо было чуть дольше подумать), мы берем оригинальный обьект копируем в него только разрешенные переменные
    */
    @Override
    public void updateEpic(Epic epic) {
        if ( epics.containsKey(epic.getId()) ) {
            Epic epicOriginal = epics.get(epic.getId());
            epicOriginal.setName(epic.getName());
            epicOriginal.setDescription(epic.getDescription());
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if ( subTasks.containsKey(subTask.getId()) ) {
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicID());
            updateEpicStatus(epic);
        }
    }
    // Удаление по идентификатору
    @Override
    public void deleteSimpleTaskByID(int id) {
        simpleTasks.remove(id);
    }

    @Override
    public void deleteEpicByID(int id) {
        Epic epic = epics.get(id);
        for ( int subTasksIDs : epic.getSubTasksIDs()) {
            subTasks.remove(subTasksIDs);
        }
        epics.remove(id);
    }

    @Override
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
}
