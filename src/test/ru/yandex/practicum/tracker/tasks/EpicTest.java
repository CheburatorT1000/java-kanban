package ru.yandex.practicum.tracker.tasks;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tracker.managers.FileBackedTasksManager;
import ru.yandex.practicum.tracker.tasks.Epic;
import ru.yandex.practicum.tracker.tasks.Status;
import ru.yandex.practicum.tracker.tasks.SubTask;

import java.io.File;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private FileBackedTasksManager taskManager;

    @BeforeEach
    void createNewTaskManager() {
        taskManager = new FileBackedTasksManager(new File("src/ru/yandex/practicum/tracker/resources/save.csv"));
    }

    @Test
    public void shouldBeNewWithEmptySubTaskList() {
        Epic epic = new Epic(1, "ИмяЭпика", "ОписаниеЗадачи", Status.DONE, Instant.EPOCH, 0);
        taskManager.addEpicTask(epic);
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    public void shouldBeNewWithAllSubTasksNew() {
        Epic epic = new Epic(1, "ИмяЭпика", "ОписаниеЗадачи", Status.DONE, Instant.EPOCH, 0);
        SubTask subTask1 = new SubTask(2, "ИмяСабТаска", "ОписаниеСабТаска", Status.NEW, Instant.EPOCH, 0, 1);
        SubTask subTask2 = new SubTask(2, "ИмяСабТаска", "ОписаниеСабТаска", Status.NEW, Instant.EPOCH, 0, 1);
        SubTask subTask3 = new SubTask(2, "ИмяСабТаска", "ОписаниеСабТаска", Status.NEW, Instant.EPOCH, 0, 1);
        taskManager.addEpicTask(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    public void shouldBeDoneWithAllSubTasksDone() {
        Epic epic = new Epic(0, "ИмяЭпика", "ОписаниеЗадачи", Status.NEW, Instant.EPOCH, 0);
        SubTask subTask1 = new SubTask(0, "ИмяСабТаска", "ОписаниеСабТаска", Status.DONE, Instant.EPOCH, 0, 1);
        SubTask subTask2 = new SubTask(0, "ИмяСабТаска", "ОписаниеСабТаска", Status.DONE, Instant.EPOCH, 0, 1);
        SubTask subTask3 = new SubTask(0, "ИмяСабТаска", "ОписаниеСабТаска", Status.DONE, Instant.EPOCH, 0, 1);
        taskManager.addEpicTask(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    public void shouldBeInProgressWithAllSubTasksNewDone() {
        Epic epic = new Epic(0, "ИмяЭпика", "ОписаниеЗадачи", Status.NEW, Instant.EPOCH, 0);
        SubTask subTask1 = new SubTask(0, "ИмяСабТаска", "ОписаниеСабТаска", Status.NEW, Instant.EPOCH, 0, 1);
        SubTask subTask2 = new SubTask(0, "ИмяСабТаска", "ОписаниеСабТаска", Status.NEW, Instant.EPOCH, 0, 1);
        SubTask subTask3 = new SubTask(0, "ИмяСабТаска", "ОписаниеСабТаска", Status.DONE, Instant.EPOCH, 0, 1);
        taskManager.addEpicTask(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void shouldBeInProgressWithAllSubTasksInProgress() {
        Epic epic = new Epic(0, "ИмяЭпика", "ОписаниеЗадачи", Status.NEW, Instant.EPOCH, 0);
        SubTask subTask1 = new SubTask(0, "ИмяСабТаска", "ОписаниеСабТаска", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        SubTask subTask2 = new SubTask(0, "ИмяСабТаска", "ОписаниеСабТаска", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        SubTask subTask3 = new SubTask(0, "ИмяСабТаска", "ОписаниеСабТаска", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        taskManager.addEpicTask(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }
}