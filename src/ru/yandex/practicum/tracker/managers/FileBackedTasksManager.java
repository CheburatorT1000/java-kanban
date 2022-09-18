package ru.yandex.practicum.tracker.managers;

import ru.yandex.practicum.tracker.exceptions.ManagerSaveException;
import ru.yandex.practicum.tracker.tasks.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager newFileBackedTasksManager = new FileBackedTasksManager(file);
        try {
            String csv = Files.readString(Path.of(file.getPath()));
            String[] lines = csv.split("\n");
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    if (id > newFileBackedTasksManager.nextID)
                        newFileBackedTasksManager.nextID = id;
                    TaskType taskType = TaskType.valueOf(parts[1]);
                    String name = parts[2];
                    Status status = Status.valueOf(parts[3]);
                    String description = parts[4];

                    switch (taskType) {
                        case SIMPLE_TASK: {
                            newFileBackedTasksManager.simpleTasks.put(id, new SimpleTask(id, name, description, status));
                            break;
                        }
                        case EPIC: {
                            newFileBackedTasksManager.epics.put(id, new Epic(id, name, description, status));
                            break;
                        }
                        case SUB_TASK: {
                            int epicId = Integer.parseInt(parts[5]);
                            newFileBackedTasksManager.subTasks.put(id, new SubTask(id, name, description, status, epicId));
                            Epic epic = newFileBackedTasksManager.epics.get(epicId);
                            epic.addSubTask(id);
                            newFileBackedTasksManager.updateEpicStatus(epic);
                            break;
                        }
                    }
                } else {
                    line = lines[i + 1];
                    String[] parts = line.split(",");
                    for (int j = 0; j < parts.length; j++) {
                        int id = Integer.parseInt(parts[j]);
                        if (newFileBackedTasksManager.simpleTasks.containsKey(id)) {
                            newFileBackedTasksManager.inMemoryHistoryManager.add(
                                    newFileBackedTasksManager.simpleTasks.get(id));
                        } else if (newFileBackedTasksManager.epics.containsKey(id)) {
                            newFileBackedTasksManager.inMemoryHistoryManager.add(
                                    newFileBackedTasksManager.simpleTasks.get(id));
                        } else if (newFileBackedTasksManager.subTasks.containsKey(id)) {
                            newFileBackedTasksManager.inMemoryHistoryManager.add(
                                    newFileBackedTasksManager.subTasks.get(id));
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return newFileBackedTasksManager;
    }
    public void save() {
        // читаем по всем мапам и добавляем пробел перед историей
        try (Writer fileWriter = new FileWriter(file)) {
            fileWriter.write("id,type,name,status,description,epic\n");
            for (int ids : simpleTasks.keySet()) {
                fileWriter.write(simpleTasks.get(ids).toString() + '\n');
            }
            for (int ids : epics.keySet()) {
                fileWriter.write(epics.get(ids).toString() + '\n');
            }
            for (int ids : subTasks.keySet()) {
                fileWriter.write(subTasks.get(ids).toString() + '\n');
            }
            fileWriter.write("\n");
            //
            for (SimpleTask task : getHistory()) {
                fileWriter.write(task.getId() + ",");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл!");
        }
    }

    @Override
    public void addSimpleTask(SimpleTask task) {
        super.addSimpleTask(task);
        save();
    }

    @Override
    public void addEpicTask(Epic epic) {
        super.addSimpleTask(epic);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSimpleTask(subTask);
        save();
    }

    @Override
    public void updateSimpleTask(SimpleTask simpleTask) {
        super.updateSimpleTask(simpleTask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void deleteSimpleTaskByID(int id) {
        super.deleteSimpleTaskByID(id);
        save();
    }

    @Override
    public void deleteEpicByID(int id) {
        super.deleteEpicByID(id);
        save();
    }

    @Override
    public void deleteSubTaskByID(int id) {
        super.deleteSubTaskByID(id);
        save();
    }


    @Override
    public SimpleTask getSimpleTaskByID(int id) {
        SimpleTask task = super.getSimpleTaskByID(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicTaskByID(int id) {
        Epic epic = super.getEpicTaskByID(id);
        save();
        return epic;
    }

    @Override
    public SubTask getSubTaskByID(int id) {
        SubTask subTask = super.getSubTaskByID(id);
        save();
        return subTask;
    }

    @Override
    public void deleteAllSimpleTasks() {
        super.deleteAllSimpleTasks();
        save();
    }

    @Override
    public void deleteAllEpicTasks() {
        super.deleteAllEpicTasks();
        save();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }
}
