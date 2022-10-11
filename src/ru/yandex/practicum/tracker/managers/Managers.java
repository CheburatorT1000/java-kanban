package ru.yandex.practicum.tracker.managers;

import java.io.File;

public class Managers {

    public static TaskManager getDefault() {
        File file = new File("src/ru/yandex/practicum/tracker/resources/save.csv");
        return new FileBackedTasksManager(file);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
