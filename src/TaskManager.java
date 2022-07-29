import java.util.HashMap;

public class TaskManager {
    private int nextID = 1;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();

    public void addTask(Task task) {
        task.setId(nextID++);
        tasks.put(task.getId(), task);
    }


    public void addEpic(Epic task) {
        task.setId(nextID++);
        epics.put(task.getId(), task);
    }


    public void addSubTask(SubTask task) {
        task.setId(nextID++);
        Epic epic = epics.get(task.getParentEpicTaskID());
        epic.addSubTask(task.getId());
        subTasks.put(task.getId(), task);
        epics.put(epic.getId(), epic);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    private void updateEpic(Epic epic) {
        int epicStatusCount = 0;
        for ( int subTasksIDs : epic.getSubTasksIDs()) {
            SubTask subTask = subTasks.get(subTasksIDs);
            if ( subTask.getStatus().equals("IN_PROGRESS") )
                epicStatusCount++;
            else if ( subTask.getStatus().equals("DONE") )
                epicStatusCount += 2;
        }
        if ( epic.getSubTasksIDs().isEmpty() )
            epic.setStatus("NEW");
        else if ( epicStatusCount == 0 )
            epic.setStatus("NEW");
        else if ( epicStatusCount / epic.getSubTasksIDs().size() == 2 )
            epic.setStatus("DONE");
        else
            epic.setStatus("IN_PROGRESS");
        epics.put(epic.getId(), epic);
    }

    public void updateSubTask(SubTask subTask) {
        Epic epic = epics.get(subTask.getParentEpicTaskID());
        subTasks.put(subTask.getId(), subTask);
        updateEpic(epic);
    }

    public String showAllTasks() {
        String taskToString = "";
        for (int tasksIDs : tasks.keySet()) {
            Task task = tasks.get(tasksIDs);
            taskToString += task.toString();
        }
        return taskToString;
    }

    public String showAllEpicTasks() {
        String epicTaskToString = "";
        for (int tasksIDs : epics.keySet()) {
            Task task = epics.get(tasksIDs);
            epicTaskToString += task.toString();
        }
        return epicTaskToString;
    }

    public String showAllSubTasks() {
        String subTaskToString = "";
        for (int tasksIDs : subTasks.keySet()) {
            Task task = subTasks.get(tasksIDs);
            subTaskToString += task.toString();
        }
        return subTaskToString;
    }
    public void deleteAllTasks() {
        tasks.clear();
    }
    public void deleteAllEpics() {
        epics.clear();
    }
    public void deleteAllSubTasks() {
        subTasks.clear();
    }
    public Task getTaskByID(int idNumber) {
        return tasks.get(idNumber);
    }

    public Epic getEpicByID(int idNumber) {
        return epics.get(idNumber);
    }

    public SubTask getSubTaskByID(int idNumber) {
        return subTasks.get(idNumber);
    }

    public void deleteTaskByID(int idNumber) {
        tasks.remove(idNumber);
    }

    public void deleteEpicByID(int idNumber) {
        epics.remove(idNumber);
    }

    public void deleteSubTaskByID(int idNumber) {
        subTasks.remove(idNumber);
    }
}