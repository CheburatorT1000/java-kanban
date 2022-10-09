package ru.yandex.practicum.tracker.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tracker.tasks.SimpleTask;
import ru.yandex.practicum.tracker.tasks.Status;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    private InMemoryHistoryManager historyManager;

    @BeforeEach
    void getHistoryManager() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void add() {
        SimpleTask simpleTask = new SimpleTask(2, "name", "description", Status.NEW, Instant.EPOCH, 0);
        historyManager.add(simpleTask);
        final List<SimpleTask> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void shouldRemove() {
        SimpleTask simpleTask = new SimpleTask(2, "name", "description", Status.NEW, Instant.EPOCH, 0);
        historyManager.add(simpleTask);
        final List<SimpleTask> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        historyManager.remove(simpleTask.getId());
        assertTrue(historyManager.getHistory().isEmpty(), "История не пустая.");
    }
}