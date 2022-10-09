package ru.yandex.practicum.tracker.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tracker.tasks.Epic;
import ru.yandex.practicum.tracker.tasks.SimpleTask;
import ru.yandex.practicum.tracker.tasks.Status;
import ru.yandex.practicum.tracker.tasks.SubTask;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.tracker.tasks.Status.NEW;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T managerForTest;
    protected abstract T createManager();

    @BeforeEach
    void getManager() {
        managerForTest = createManager();
    }

    @Test
    void shouldGetSubTasksFromEpic() {
        Epic epic = new Epic(0, "ИмяЭпика", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        SubTask subTask1 = new SubTask(0, "ИмяСабТаска1", "ОписаниеСабТаска", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        SubTask subTask2 = new SubTask(0, "ИмяСабТаска1", "ОписаниеСабТаска", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        SubTask subTask3 = new SubTask(0, "ИмяСабТаска1", "ОписаниеСабТаска", Status.IN_PROGRESS, Instant.EPOCH, 0, 2);
        managerForTest.addEpicTask(epic);
        managerForTest.addSubTask(subTask1);
        managerForTest.addSubTask(subTask2);
        managerForTest.addSubTask(subTask3);
        List<SubTask> subTasksFromEpic = managerForTest.getSubTasksFromEpic(1);

        assertEquals(2, subTasksFromEpic.size(), "Размер списка подзадач не верен!");
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус эпика не верен!");
        assertEquals(2, managerForTest.getAllSubTasks().size());
    }

    @Test
    void shouldReturnAllSimpleTasks() {
        SimpleTask simpleTask = new SimpleTask(0, "ИмяЗадачи", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        managerForTest.addSimpleTask(simpleTask);
        assertEquals(1, managerForTest.getAllSimpleTasks().size());
        managerForTest.deleteAllSimpleTasks();
        assertEquals(0, managerForTest.getAllSimpleTasks().size());
    }

    @Test
    void shouldReturnAllEpicTasks() {
        Epic epic1 = new Epic(0, "ИмяЭпика", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        Epic epic2 = new Epic(1, "ИмяЭпика", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        managerForTest.addEpicTask(epic1);
        List<Epic> listEpics = managerForTest.getAllEpicTasks();
        Epic epicFromMethod = listEpics.get(0);
        assertEquals(epicFromMethod, epic2);
    }

    @Test
    void shouldReturnAllSubTasks() {
        Epic epic = new Epic(0, "ИмяЭпика", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        SubTask subTask1 = new SubTask(0, "ИмяСабТаска1", "ОписаниеСабТаска", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        SubTask subTask2 = new SubTask(0, "ИмяСабТаска2", "ОписаниеСабТаска", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        managerForTest.addEpicTask(epic);
        managerForTest.addSubTask(subTask1);
        managerForTest.addSubTask(subTask2);
        assertEquals(2,managerForTest.getAllSubTasks().size());
    }

    @Test
    void shouldDeleteAllSimpleTasks() {
        SimpleTask simpleTask1 = new SimpleTask(0, "ИмяЗадачи", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        SimpleTask simpleTask2 = new SimpleTask(0, "ИмяЗадачи", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        SimpleTask simpleTask3 = new SimpleTask(0, "ИмяЗадачи", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        managerForTest.addSimpleTask(simpleTask1);
        managerForTest.addSimpleTask(simpleTask2);
        managerForTest.addSimpleTask(simpleTask3);
        assertEquals(3, managerForTest.getAllSimpleTasks().size());
        managerForTest.deleteAllSimpleTasks();
        assertEquals(0, managerForTest.getAllSimpleTasks().size());
    }

    @Test
    void shouldDeleteAllEpicTasks() {
        Epic epic = new Epic(0, "ИмяЭпика", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        SubTask subTask1 = new SubTask(0, "ИмяСабТаска1", "ОписаниеСабТаска", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        SubTask subTask2 = new SubTask(0, "ИмяСабТаска2", "ОписаниеСабТаска", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        managerForTest.addEpicTask(epic);
        managerForTest.addSubTask(subTask1);
        managerForTest.addSubTask(subTask2);
        managerForTest.deleteAllEpicTasks();
        assertTrue(managerForTest.getAllEpicTasks().isEmpty());
        assertTrue(managerForTest.getAllSubTasks().isEmpty());
    }

    @Test
    void shouldDeleteAllSubTasks() {
        Epic epic = new Epic(0, "ИмяЭпика", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        SubTask subTask1 = new SubTask(0, "ИмяСабТаска1", "ОписаниеСабТаска", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        SubTask subTask2 = new SubTask(0, "ИмяСабТаска2", "ОписаниеСабТаска", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        managerForTest.addEpicTask(epic);
        managerForTest.addSubTask(subTask1);
        managerForTest.addSubTask(subTask2);
        managerForTest.deleteAllSubTasks();
        assertTrue(managerForTest.getAllSubTasks().isEmpty());
    }

    @Test
    void shouldGetSimpleTaskByID() {
        SimpleTask simpleTask1 = new SimpleTask(0, "ИмяЗадачи", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        SimpleTask simpleTask2 = new SimpleTask(1, "ИмяЗадачи", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        managerForTest.addSimpleTask(simpleTask1);
        assertEquals(managerForTest.getSimpleTaskByID(1), simpleTask2);
        assertNull(managerForTest.getSimpleTaskByID(4));
    }

    @Test
    void shouldGetEpicTaskByID() {
        Epic epic1 = new Epic(0, "ИмяЭпика", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        Epic epic2 = new Epic(1, "ИмяЭпика", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        managerForTest.addEpicTask(epic1);
        assertEquals(epic2, managerForTest.getEpicTaskByID(1));
        assertNull(managerForTest.getEpicTaskByID(6));
    }

    @Test
    void shouldGetSubTaskByID() {
        Epic epic1 = new Epic(0, "ИмяЭпика", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        SubTask subTask1 = new SubTask(0, "ИмяСабТаска", "ОписаниеСабТаска", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        SubTask subTask2 = new SubTask(2, "ИмяСабТаска", "ОписаниеСабТаска", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        managerForTest.addEpicTask(epic1);
        managerForTest.addSubTask(subTask1);
        assertEquals(subTask2, managerForTest.getSubTaskByID(2));
        assertNull(managerForTest.getSubTaskByID(6));
    }

    @Test
    void shouldAddSimpleTask() {
        SimpleTask simpleTask = new SimpleTask(0,"имя", "описание", NEW, Instant.EPOCH, 0);
        managerForTest.addSimpleTask(simpleTask);
        assertNotEquals(0, managerForTest.getAllSimpleTasks().size());
        assertEquals(1, managerForTest.getAllSimpleTasks().size());
    }

    @Test
    void shouldAddEpicTask() {
        Epic epic1 = new Epic(0, "ИмяЭпика", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        Epic epic2 = new Epic(1, "ИмяЭпика", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        managerForTest.addEpicTask(epic1);
        assertEquals(epic2, managerForTest.getEpicTaskByID(1));
        assertEquals(1, managerForTest.getAllEpicTasks().size());
        assertNull(managerForTest.getEpicTaskByID(6));
    }

    @Test
    void shouldAddSubTask() {
        Epic epic1 = new Epic(0, "ИмяЭпика", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        SubTask subTask1 = new SubTask(0, "ИмяСабТаска", "ОписаниеСабТаска", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        SubTask subTask2 = new SubTask(2, "ИмяСабТаска", "ОписаниеСабТаска", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        managerForTest.addEpicTask(epic1);
        managerForTest.addSubTask(subTask1);
        assertEquals(subTask2, managerForTest.getSubTaskByID(2));
        assertEquals(1, managerForTest.getAllSubTasks().size());
        assertEquals(1, epic1.getSubTasksIDs().size());
        assertNull(managerForTest.getSubTaskByID(6));
    }

    @Test
    void shouldUpdateSimpleTask() {
        SimpleTask simpleTask1 = new SimpleTask(1, "name", "description", NEW, Instant.EPOCH, 0);
        SimpleTask simpleTask2 = new SimpleTask(1, "anotherName", "anotherDescription", NEW, Instant.EPOCH, 0);
        managerForTest.addSimpleTask(simpleTask1);
        managerForTest.updateSimpleTask(simpleTask2);
        assertEquals(simpleTask2, managerForTest.getSimpleTaskByID(1));
    }

    @Test
    void shouldUpdateEpic() {
        Epic epic1 = new Epic(1, "name", "description", NEW, Instant.EPOCH, 0);
        Epic epic2 = new Epic(1, "anotherName", "anotherDescription", NEW, Instant.EPOCH, 0);
        managerForTest.addEpicTask(epic1);
        managerForTest.updateEpic(epic2);
        assertEquals(epic2, managerForTest.getEpicTaskByID(1));
    }

    @Test
    void shouldUpdateSubTask() {
        Epic epic1 = new Epic(0, "ИмяЭпика", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        SubTask subTask1 = new SubTask(0, "ИмяСабТаска", "ОписаниеСабТаска1", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        SubTask subTask2 = new SubTask(2, "ИмяСабТаска", "ОписаниеСабТаска2", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        managerForTest.addEpicTask(epic1);
        managerForTest.addSubTask(subTask1);
        managerForTest.updateSubTask(subTask2);
        assertEquals(subTask2, managerForTest.getSubTaskByID(subTask1.getId()));
    }

    @Test
    void shouldDeleteSimpleTaskByID() {
        SimpleTask simpleTask1 = new SimpleTask(1, "name", "description", NEW, Instant.EPOCH, 0);
        managerForTest.addSimpleTask(simpleTask1);
        managerForTest.deleteSimpleTaskByID(simpleTask1.getId());
        assertEquals(0, managerForTest.getAllSimpleTasks().size());
    }

    @Test
    void shouldDeleteEpicByID() {
        Epic epic1 = new Epic(0, "ИмяЭпика", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        SubTask subTask1 = new SubTask(0, "ИмяСабТаска", "ОписаниеСабТаска1", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        managerForTest.addEpicTask(epic1);
        managerForTest.addSubTask(subTask1);
        managerForTest.deleteEpicByID(epic1.getId());
        assertTrue(managerForTest.getAllEpicTasks().isEmpty());
    }

    @Test
    void shouldDeleteSubTaskByID() {
        Epic epic1 = new Epic(0, "ИмяЭпика", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        SubTask subTask1 = new SubTask(0, "ИмяСабТаска", "ОписаниеСабТаска1", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        managerForTest.addEpicTask(epic1);
        managerForTest.addSubTask(subTask1);
        managerForTest.deleteSubTaskByID(subTask1.getId());
        assertTrue(managerForTest.getAllSubTasks().isEmpty());
        assertTrue(epic1.getSubTasksIDs().isEmpty());
    }
    @Test
    void shouldGetHistory() {
        Epic epic1 = new Epic(0, "ИмяЭпика", "ОписаниеЗадачи", NEW, Instant.EPOCH, 0);
        SubTask subTask1 = new SubTask(0, "ИмяСабТаска", "ОписаниеСабТаска1", Status.IN_PROGRESS, Instant.EPOCH, 0, 1);
        managerForTest.addEpicTask(epic1);
        managerForTest.addSubTask(subTask1);
        managerForTest.getSubTaskByID(2);
        managerForTest.getEpicTaskByID(1);
        List<SimpleTask> fromHistory = managerForTest.getHistory();
        List<SimpleTask> fromTest = List.of(subTask1, epic1);
        assertArrayEquals(fromTest.toArray(), fromHistory.toArray());
    }
}


