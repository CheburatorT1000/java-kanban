package ru.yandex.practicum.tracker.managers;

import ru.yandex.practicum.tracker.exceptions.TimeCrossingException;
import ru.yandex.practicum.tracker.tasks.Epic;
import ru.yandex.practicum.tracker.tasks.SimpleTask;
import ru.yandex.practicum.tracker.tasks.SubTask;

import java.util.*;

import static ru.yandex.practicum.tracker.tasks.Status.*;

public class InMemoryTaskManager implements TaskManager, Comparator<SimpleTask> {


    protected HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    protected int nextID = 0;
    protected final HashMap<Integer, SimpleTask> simpleTasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    protected final TreeSet<SimpleTask> prioritizedTasks = new TreeSet<>(this);


    private int makeID() {
        return ++nextID;
    }

    @Override
    public List<SimpleTask> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    private void checkTimeCrossing(SimpleTask task) {
        List<SimpleTask> tempTasks = getPrioritizedTasks();
        for (int i = 1; i < tempTasks.size(); i++) {
            if (tempTasks.get(i).getStartTime().isBefore(tempTasks.get(i-1).getEndTime())) {
                prioritizedTasks.removeIf(t -> t.getId() == task.getId());
                throw new TimeCrossingException("Найдено пересечение между "
                        + tempTasks.get(i).toString()
                        + " и "
                        + tempTasks.get(i - 1).toString());
            }
        }
    }

    protected void addToPrioritizedTasks(SimpleTask task) {
        prioritizedTasks.add(task);
        checkTimeCrossing(task);
    }


    // метод для определения статуса эпика
    protected void updateEpicStatus(Epic epic) {
        int epicStatusCount = 0;
        long duration = 0;
        final byte SECONDS_IN_ONE_MINUTE = 60;
        for (int subTasksIdNum : epic.getSubTasksIDs()) {
            if (subTasks.containsKey(subTasksIdNum)) {
                SubTask subTask = subTasks.get(subTasksIdNum);

                if (subTask.getStartTime().isBefore(epic.getStartTime()))
                    epic.setStartTime(subTask.getStartTime());
                if (epic.getEndTime() != null && subTask.getStartTime().isAfter(epic.getEndTime()))
                    epic.setEndTime(subTask.getEndTime());
                else if (epic.getEndTime() == null)
                    epic.setEndTime(subTask.getEndTime());

                duration += subTask.getDuration();

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
        }
        epic.setDuration(duration / SECONDS_IN_ONE_MINUTE);
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
        for (int id : simpleTasks.keySet()) {
            inMemoryHistoryManager.remove(id);
            prioritizedTasks.removeIf(t -> t.getId() == id);
        }
        simpleTasks.clear();
    }

    @Override
    public void deleteAllEpicTasks() {
        for (int id : epics.keySet()) {
            inMemoryHistoryManager.remove(id);
            prioritizedTasks.removeIf(t -> t.getId() == id);
        }
        for (int id : subTasks.keySet()) {
            inMemoryHistoryManager.remove(id);
            prioritizedTasks.removeIf(t -> t.getId() == id);
        }
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void deleteAllSubTasks() {
        for (int epicIds : epics.keySet()) {
            Epic epic = epics.get(epicIds);
            epic.getSubTasksIDs().clear();
            updateEpicStatus(epic);
            prioritizedTasks.removeIf(t -> t.getId() == epicIds);
        }
        for (int id : subTasks.keySet()) {
            inMemoryHistoryManager.remove(id);
            prioritizedTasks.removeIf(t -> t.getId() == id);
        }
        subTasks.clear();
    }

    @Override
    // Получение задачи по номеру
    public SimpleTask getSimpleTaskByID(int id) {
        SimpleTask simpleTask = null;
        if (simpleTasks.containsKey(id)) {
            simpleTask = simpleTasks.get(id);
            inMemoryHistoryManager.add(simpleTask);
        }
        return simpleTask;
    }

    @Override
    public Epic getEpicTaskByID(int id) {
        Epic epic = null;
        if (epics.containsKey(id)) {
            epic = epics.get(id);
            inMemoryHistoryManager.add(epic);
        }
        return epic;
    }

    @Override
    public SubTask getSubTaskByID(int id) {
        SubTask subTask = null;
        if (subTasks.containsKey(id)) {
            subTask = subTasks.get(id);
            inMemoryHistoryManager.add(subTask);
        }
        return subTask;
    }

    // Создание задачи
    @Override
    public void addSimpleTask(SimpleTask task) {
        task.setId(makeID());
        simpleTasks.put(task.getId(), task);
        addToPrioritizedTasks(task);
    }

    @Override
    public void addEpicTask(Epic epic) {
        epic.setId(makeID());
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
        addToPrioritizedTasks(epic);
    }

    @Override
    public void addSubTask(SubTask subTask) {
        if (epics.containsKey(subTask.getEpicID())) {
            subTask.setId(makeID());
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicID());
            epic.addSubTask(subTask.getId());
            addToPrioritizedTasks(subTask);
            updateEpicStatus(epic);
        }
    }

    // Обновление задачи
    @Override
    public void updateSimpleTask(SimpleTask simpleTask) {
        if (simpleTasks.containsKey(simpleTask.getId())) {
            simpleTasks.put(simpleTask.getId(), simpleTask);
            prioritizedTasks.removeIf(t -> t.getId() == simpleTask.getId());
             addToPrioritizedTasks(simpleTask);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic epicOriginal = epics.get(epic.getId());
            epicOriginal.setName(epic.getName());
            epicOriginal.setDescription(epic.getDescription());
            prioritizedTasks.removeIf(t -> t.getId() == epicOriginal.getId());
            addToPrioritizedTasks(epic);
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicID());
            updateEpicStatus(epic);
            prioritizedTasks.removeIf(t -> t.getId() == subTask.getId());
            addToPrioritizedTasks(subTask);
        }
    }

    // Удаление по идентификатору
    @Override
    public void deleteSimpleTaskByID(int id) {
        inMemoryHistoryManager.remove(id);
        simpleTasks.remove(id);
        prioritizedTasks.removeIf(t -> t.getId() == id);
    }

    @Override
    public void deleteEpicByID(int id) {
        Epic epic = epics.get(id);
        for (int subTasksIDs : epic.getSubTasksIDs()) {
            subTasks.remove(subTasksIDs);
            prioritizedTasks.removeIf(t -> t.getId() == subTasksIDs);
            inMemoryHistoryManager.remove(subTasksIDs);
        }
        inMemoryHistoryManager.remove(id);
        prioritizedTasks.removeIf(t -> t.getId() == id);
        epics.remove(id);
    }

    @Override
    public void deleteSubTaskByID(int id) {
        SubTask subTask = subTasks.get(id);
        Epic epic = epics.get(subTask.getEpicID());
        ArrayList<Integer> subTasksIDs = epic.getSubTasksIDs();
        for (int i = 0; i < subTasksIDs.size(); i++) {
            if (subTasksIDs.get(i) == id) {
                subTasksIDs.remove(i);
                break;
            }
        }
        epic.setSubTasksIDs(subTasksIDs);
        updateEpicStatus(epic);
        inMemoryHistoryManager.remove(id);
        prioritizedTasks.removeIf(t -> t.getId() == id);
        subTasks.remove(id);
    }

    @Override
    public int compare(SimpleTask o1, SimpleTask o2) {
        return o1.getStartTime().compareTo(o2.getStartTime());
    }
}
