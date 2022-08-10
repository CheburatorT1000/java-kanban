package ru.yandex.practicum.tracker.managers;

import ru.yandex.practicum.tracker.tasks.SimpleTask;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager<T extends SimpleTask> implements HistoryManager<T>{

    private List<T> historyTasks = new ArrayList<T>();

    @Override
    public void add(T task) {
        if ( historyTasks.size() == 10 ) {
            historyTasks.remove(0);
            historyTasks.add(task);
        } else
            historyTasks.add(task);
    }
    @Override
    public List<T> getHistory() {
        return historyTasks;
    }
}
