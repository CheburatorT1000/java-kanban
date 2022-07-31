package ru.yandex.practicum.tracker.tasks;

import ru.yandex.practicum.tracker.tasks.SimpleTask;

import java.util.Objects;

public class SubTask extends SimpleTask {
    protected int epicID;

    public SubTask(int id, String name, String description, Status currentProgress, int epicID) {
        super(id, name, description, currentProgress);
        this.epicID = epicID;
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
        return '\n' + "SubTask{" +
                "epicID=" + epicID +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", currentProgress=" + currentProgress +
                '}';
    }
}
