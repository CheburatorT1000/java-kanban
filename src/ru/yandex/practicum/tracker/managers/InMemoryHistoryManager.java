package ru.yandex.practicum.tracker.managers;

import ru.yandex.practicum.tracker.tasks.SimpleTask;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final int LIST_MAX_SIZE = 10;
    private LinkedList<SimpleTask> historyTasks = new LinkedList<SimpleTask>();

    @Override
    public void add(SimpleTask task) {
        historyTasks.add(task);
        if (historyTasks.size() > LIST_MAX_SIZE)
            historyTasks.removeFirst();
    }

    @Override
    public List<SimpleTask> getHistory() {
        return historyTasks;
    }
}
