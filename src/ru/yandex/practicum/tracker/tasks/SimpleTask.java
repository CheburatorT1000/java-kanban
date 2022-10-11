package ru.yandex.practicum.tracker.tasks;


import java.time.Instant;
import java.util.Objects;

import static ru.yandex.practicum.tracker.tasks.TaskType.SIMPLE_TASK;

public class SimpleTask {
    protected int id;
    protected String name;
    protected String description;
    protected Status currentProgress;
    protected TaskType taskType;
    protected Instant startTime;
    protected long duration;

    public SimpleTask(int id,
                      String name,
                      String description,
                      Status currentProgress,
                      Instant startTime,
                      long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currentProgress = currentProgress;
        this.taskType = SIMPLE_TASK;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    public Instant getEndTime() {
        final byte SECONDS_IN_ONE_MINUTE = 60;
        return startTime.plusSeconds(duration * SECONDS_IN_ONE_MINUTE);
    }

    public void setCurrentProgress(Status currentProgress) {
        this.currentProgress = currentProgress;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return currentProgress;
    }

    public void setStatus(Status status) {
        this.currentProgress = status;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleTask)) return false;
        SimpleTask that = (SimpleTask) o;
        return id == that.id && Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.equals(currentProgress, that.currentProgress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, currentProgress);
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
