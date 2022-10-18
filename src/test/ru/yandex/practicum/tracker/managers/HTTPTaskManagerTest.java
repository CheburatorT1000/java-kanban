package ru.yandex.practicum.tracker.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.*;
import ru.yandex.practicum.tracker.http.HttpTaskServer;
import ru.yandex.practicum.tracker.http.InstantAdapter;
import ru.yandex.practicum.tracker.http.KVServer;
import ru.yandex.practicum.tracker.tasks.Epic;
import ru.yandex.practicum.tracker.tasks.SimpleTask;
import ru.yandex.practicum.tracker.tasks.Status;
import ru.yandex.practicum.tracker.tasks.SubTask;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class HTTPTaskManagerTest extends TaskManagerTest<HTTPTaskManager>{

    private KVServer kvServer;
    private HttpTaskServer httpTaskServer;
    @Override
    protected HTTPTaskManager createManager() {
        return new HTTPTaskManager(URI.create("http://localhost:8078"));

    }

    @BeforeEach
    @Override
    void getManager() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantAdapter())
                .create();
        kvServer = new KVServer();
        kvServer.start();
        managerForTest = createManager();
        httpTaskServer = new HttpTaskServer(managerForTest);
        httpTaskServer.serverStart();
    }

    @AfterEach
     void stop() {
        kvServer.stop();
        httpTaskServer.serverStop();
    }

    @Test
    void shouldSaveAndRestore() {
        managerForTest.addSimpleTask(new SimpleTask(0, "name", "description", Status.NEW, Instant.ofEpochMilli(3),0));
        managerForTest.addSimpleTask(new SimpleTask(0, "name", "description", Status.NEW, Instant.ofEpochMilli(1),0));
        managerForTest.addSimpleTask(new SimpleTask(0, "name", "description", Status.NEW, Instant.ofEpochMilli(2),0));
        System.out.println(managerForTest.getAllSimpleTasks().toString());

        HTTPTaskManager httpTaskManager2 = managerForTest.restore();

        assertEquals(managerForTest.getPrioritizedTasks().toString(), httpTaskManager2.getPrioritizedTasks().toString());
        assertEquals(3, httpTaskManager2.getAllSimpleTasks().size());
    }

    @Test
    void shouldSaveAndRestoreWithEpicSubtasks() {
        managerForTest.addEpicTask(new Epic(0, "name", "description", Status.NEW, Instant.ofEpochMilli(3),0));
        managerForTest.addSubTask(new SubTask(0, "name", "description", Status.NEW, Instant.ofEpochMilli(1),0, 1));
        managerForTest.addSubTask(new SubTask(0, "name", "description", Status.NEW, Instant.ofEpochMilli(2),0, 1));
        System.out.println(managerForTest.getAllSimpleTasks().toString());

        HTTPTaskManager httpTaskManager2 = managerForTest.restore();

        assertEquals(managerForTest.getEpicTaskByID(1).toString(), httpTaskManager2.getEpicTaskByID(1).toString());
        assertEquals(managerForTest.getEpicTaskByID(1).getSubTasksIDs().size(),  httpTaskManager2.getEpicTaskByID(1).getSubTasksIDs().size());
    }

    @Test
    void shouldSaveAndRestoreWithEpicAndEmptySubtasks() {
        managerForTest.addEpicTask(new Epic(0, "name", "description", Status.NEW, Instant.ofEpochMilli(3),0));
        System.out.println(managerForTest.getAllSimpleTasks().toString());

        HTTPTaskManager httpTaskManager2 = managerForTest.restore();

        assertEquals(managerForTest.getEpicTaskByID(1).getId(), httpTaskManager2.getEpicTaskByID(1).getId());
        assertEquals(managerForTest.getEpicTaskByID(1).toString(),  httpTaskManager2.getEpicTaskByID(1).toString());
    }
}