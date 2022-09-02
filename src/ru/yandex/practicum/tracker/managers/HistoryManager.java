package ru.yandex.practicum.tracker.managers;



import ru.yandex.practicum.tracker.tasks.SimpleTask;

import java.util.List;


public interface HistoryManager {

     void add(SimpleTask task);

     void remove(int id);
     List<SimpleTask> getHistory();
}
