package ru.yandex.practicum.tracker.managers;

import java.io.File;
import java.net.URI;

public class Managers {

    public static HTTPTaskManager getDefault() {
        return new HTTPTaskManager(URI.create("http://localhost:8078"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
