package ru.yandex.practicum.tracker.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tracker.exceptions.ManagerSaveException;
import ru.yandex.practicum.tracker.tasks.Epic;
import ru.yandex.practicum.tracker.tasks.Status;
import ru.yandex.practicum.tracker.tasks.SubTask;

import java.io.File;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    private File file = new File("src/test/testFile.csv");

    @Override
    protected FileBackedTasksManager createManager() {
        return new FileBackedTasksManager(file);
    }

    @BeforeEach
    void getManager() {
        managerForTest = createManager();
    }

    @Test
    void shouldLoadFromEmptyFile() {
        Epic epic1 = new Epic(1, "name", "description", Status.NEW, Instant.EPOCH, 0);
        managerForTest.addEpicTask(epic1);
        managerForTest.deleteAllEpicTasks();
        FileBackedTasksManager tempForTest = FileBackedTasksManager.loadFromFile(file);
        assertEquals(0, tempForTest.getAllEpicTasks().size());
    }

    @Test
    void shouldLoadFromFileEpicWithEmptySubtasks() {
        Epic epic1 = new Epic(1, "name", "description", Status.NEW, Instant.EPOCH, 0);
        SubTask subTask1 = new SubTask(2, "name", "description", Status.NEW, Instant.now(), 0, 1);
        managerForTest.addEpicTask(epic1);
        managerForTest.addSubTask(subTask1);
        managerForTest.deleteAllSubTasks();
        FileBackedTasksManager tempForTest = FileBackedTasksManager.loadFromFile(file);
        assertEquals(1, tempForTest.getAllEpicTasks().size());
    }

    @Test
    void shouldLoadFromFileWithEmptyHistory() {
        Epic epic1 = new Epic(1, "name", "description", Status.NEW, Instant.EPOCH, 0);
        SubTask subTask1 = new SubTask(2, "name", "description", Status.NEW, Instant.now(), 0, 1);
        managerForTest.addEpicTask(epic1);
        managerForTest.addSubTask(subTask1);
        FileBackedTasksManager tempForTest = FileBackedTasksManager.loadFromFile(file);
        assertEquals(1, tempForTest.getAllEpicTasks().size());
    }

    @Test
    public void throwManagerSaveExceptionTest() {
        File badFile = new File("Ба-дум-тсс!");

        assertThrows(ManagerSaveException.class, () -> FileBackedTasksManager.loadFromFile(badFile));
    }
}