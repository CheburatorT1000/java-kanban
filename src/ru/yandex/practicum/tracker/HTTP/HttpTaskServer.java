package ru.yandex.practicum.tracker.HTTP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.tracker.managers.HTTPTaskManager;
import ru.yandex.practicum.tracker.tasks.Epic;
import ru.yandex.practicum.tracker.tasks.SimpleTask;
import ru.yandex.practicum.tracker.tasks.SubTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;

public class HttpTaskServer {
    private static final int PORT = 8081;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final HTTPTaskManager taskManager;
    private final HttpServer httpServer;
    private final Gson gson;

    public HttpTaskServer(HTTPTaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantAdapter())
                .create();
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler());

    }

    public void serverStart() {
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        httpServer.start();

    }

    public void serverStop() {
        System.out.println("HTTP-сервер остановлен на " + PORT + " порту!");
        httpServer.stop(1);

    }

    class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "";
            String method = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            String query = httpExchange.getRequestURI().getQuery();
            int statusCode = 0;

            switch (method) {
                case "GET": {

                    if (path.contains("/tasks/task") && query == null) {
                        System.out.println("getAllSimpleTasks()");
                        response = gson.toJson(new ArrayList<>(taskManager.getAllSimpleTasks()));
                        statusCode = 200;

                    } else if (path.contains("/tasks/epic") && query == null) {
                        System.out.println("getAllEpicTasks()");
                        response = gson.toJson(new ArrayList<>(taskManager.getAllEpicTasks()));
                        statusCode = 200;

                    } else if (path.contains("/tasks/subtask") && query == null) {
                        System.out.println("getAllSubTasks()");
                        response = gson.toJson(new ArrayList<>(taskManager.getAllSubTasks()));
                        statusCode = 200;

                    } else if (path.contains("/tasks/subtask/epic") && query != null) {
                        System.out.println("getSubTasksFromEpic(id)");
                        int id = Integer.parseInt(query.split("=")[1]);
                        response = gson.toJson(taskManager.getSubTasksFromEpic(id));
                        statusCode = 200;

                    } else if (path.contains("/tasks/task") && query != null) {
                        System.out.println("getSimpleTaskByID(id)");
                        int id = Integer.parseInt(query.split("=")[1]);
                        response = gson.toJson(taskManager.getSimpleTaskByID(id));
                        statusCode = 200;

                    } else if (path.contains("/tasks/epic") && query != null) {
                        System.out.println("getEpicTaskByID(id)");
                        int id = Integer.parseInt(query.split("=")[1]);
                        response = gson.toJson(taskManager.getEpicTaskByID(id));
                        statusCode = 200;

                    } else if (path.contains("/tasks/subtask") && query != null) {
                        System.out.println("getSubTaskByID(id)");
                        int id = Integer.parseInt(query.split("=")[1]);
                        response = gson.toJson(taskManager.getSubTaskByID(id));
                        statusCode = 200;

                    } else if (path.contains("/tasks/history") && query == null) {
                        System.out.println("getHistory()");
                        response = gson.toJson(new ArrayList<>(taskManager.getHistory()));
                        statusCode = 200;
                    } else if (path.contains("/tasks") && query == null) {
                        System.out.println("getPrioritizedTasks()");
                        response = gson.toJson(new ArrayList<>(taskManager.getPrioritizedTasks()));
                        statusCode = 200;
                    }
                    break;
                }

                case "POST": {

                    if (path.contains("/tasks/task")) {

                        InputStream inputStream = httpExchange.getRequestBody();
                        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                        SimpleTask simpleTask = gson.fromJson(body, SimpleTask.class);
                        if (taskManager.getSimpleTaskByID(simpleTask.getId()) == null) {
                            taskManager.addSimpleTask(simpleTask);
                        } else
                            taskManager.updateSimpleTask(simpleTask);
                        statusCode = 201;

                    } else if (path.contains("/tasks/epic")) {

                        InputStream inputStream = httpExchange.getRequestBody();
                        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                        Epic epic = gson.fromJson(body, Epic.class);
                        if (taskManager.getEpicTaskByID(epic.getId()) == null) {
                            taskManager.addEpicTask(epic);
                        } else
                            taskManager.updateEpic(epic);
                        statusCode = 201;

                    } else if (path.contains("/tasks/subtask")) {

                        InputStream inputStream = httpExchange.getRequestBody();
                        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                        SubTask subtask = gson.fromJson(body, SubTask.class);
                        if (taskManager.getSubTaskByID(subtask.getId()) == null) {
                            taskManager.addSubTask(subtask);
                        } else
                            taskManager.updateSubTask(subtask);
                        statusCode = 201;

                    }
                    break;
                }
                case "DELETE": {

                    if (path.contains("/tasks/task") && query == null) {
                        taskManager.deleteAllSimpleTasks();
                        statusCode = 200;

                    } else if (path.contains("/tasks/epic") && query == null) {
                        taskManager.deleteAllEpicTasks();
                        statusCode = 200;

                    } else if (path.contains("/tasks/subtask") && query == null) {
                        taskManager.deleteAllSubTasks();
                        statusCode = 200;

                    } else if (path.contains("/tasks/task") && query != null) {
                        int id = Integer.parseInt(query.split("=")[1]);
                        taskManager.deleteSimpleTaskByID(id);
                        statusCode = 200;

                    } else if (path.contains("/tasks/epic") && query != null) {
                        int id = Integer.parseInt(query.split("=")[1]);
                        taskManager.deleteEpicByID(id);
                        statusCode = 200;

                    } else if (path.contains("/tasks/subtask") && query != null) {
                        int id = Integer.parseInt(query.split("=")[1]);
                        taskManager.deleteSubTaskByID(id);
                        statusCode = 200;

                    }
                    break;
                }
                default: statusCode = 500;
            }

            httpExchange.sendResponseHeaders(statusCode, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}