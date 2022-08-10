package ru.yandex.practicum.tracker.managers;


import ru.yandex.practicum.tracker.tasks.SimpleTask;

import java.util.List;


public interface HistoryManager<T extends SimpleTask> {

    public void add(T task);

    public List<T> getHistory();
}
