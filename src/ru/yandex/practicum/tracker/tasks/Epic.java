package ru.yandex.practicum.tracker.tasks;


import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;

import static ru.yandex.practicum.tracker.tasks.TaskType.EPIC;

public class Epic extends SimpleTask {
    private ArrayList<Integer> subTasksIDs;
    private Instant endTime;

    public Epic(int id, String name, String description, Status currentProgress, Instant startTime, long duration) {
        super(id, name, description, currentProgress, startTime, duration);
        subTasksIDs = new ArrayList<>();
        this.taskType = EPIC;
    }
    public void addSubTask(int subTaskID) {
        this.subTasksIDs.add(subTaskID);
    }
    public void setDuration() {
        final byte SECONDS_IN_ONE_MINUTE = 60;
        duration = (getEndTime().getEpochSecond() - getStartTime().getEpochSecond()) / SECONDS_IN_ONE_MINUTE;
    }

    public ArrayList<Integer> getSubTasksIDs() {
        return subTasksIDs;
    }

    public void setSubTasksIDs(ArrayList<Integer> subTasksIDs) {
        this.subTasksIDs = subTasksIDs;
    }

    @Override
    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
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
                + ',' + description
                + ',' + startTime
                + ',' + duration;
    }
}
