package ru.yandex.practicum.tracker.managers;



import java.util.List;


public interface HistoryManager<SimpleTask> {

    public void add(SimpleTask task);

    public List<SimpleTask> getHistory();
}
