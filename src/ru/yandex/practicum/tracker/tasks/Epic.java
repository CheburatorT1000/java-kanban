package ru.yandex.practicum.tracker.tasks;


import java.util.ArrayList;
import java.util.Objects;

import static ru.yandex.practicum.tracker.tasks.TaskType.EPIC;

public class Epic extends SimpleTask {
    private ArrayList<Integer> subTasksIDs;

    public Epic(int id, String name, String description, Status currentProgress) {
        super(id, name, description, currentProgress);
        subTasksIDs = new ArrayList<>();
        this.taskType = EPIC;
    }
    public void addSubTask(int subTaskID) {
        this.subTasksIDs.add(subTaskID);
    }

    public ArrayList<Integer> getSubTasksIDs() {
        return subTasksIDs;
    }

    public void setSubTasksIDs(ArrayList<Integer> subTasksIDs) {
        this.subTasksIDs = subTasksIDs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTasksIDs, epic.subTasksIDs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasksIDs);
    }

    @Override
    public String toString() {
        return "" + id
                + ',' + taskType
                + ',' + name
                + ',' + currentProgress
                + ',' + description;
    }
}
