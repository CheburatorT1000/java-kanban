package ru.yandex.practicum.tracker.tasks;

import java.util.Objects;

import static ru.yandex.practicum.tracker.tasks.TaskType.SUB_TASK;

public class SubTask extends SimpleTask {
    private int epicID;

    public SubTask(int id, String name, String description, Status currentProgress, int epicID) {
        super(id, name, description, currentProgress);
        this.epicID = epicID;
        this.taskType = SUB_TASK;
    }

    public int getEpicID() {
        return epicID;
    }

    public void setParentEpicTaskID(int parentEpicTaskID) {
        this.epicID = parentEpicTaskID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubTask)) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return epicID == subTask.epicID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicID);
    }

    @Override
    public String toString() {
        return "" + id
                + ',' + taskType
                + ',' + name
                + ',' + currentProgress
                + ',' + description
                + ',' + epicID;
    }
}
