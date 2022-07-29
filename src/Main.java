public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask(new Task(22, "умыться", "чистота залог здоровья", "NEW"));
        taskManager.addTask(new Task(22, "умыться", "чистота залог здоровья", "NEW"));
        taskManager.addTask(new Task(22, "умыться", "чистота залог здоровья", "NEW"));
        taskManager.addEpic(new Epic(22, "умыться", "чистота залог здоровья", "NEW"));
        taskManager.addSubTask(new SubTask(5, "умыться", "чистота залог здоровья", "NEW", 4));
        taskManager.addSubTask(new SubTask(6, "умыться", "чистота залог здоровья", "NEW", 4));
        System.out.println(taskManager.showAllTasks());
        System.out.println(taskManager.showAllEpicTasks());
        System.out.println(taskManager.showAllSubTasks());
        taskManager.updateSubTask(new SubTask(5,"умыться", "чистота залог здоровья", "NEW", 4));
        taskManager.updateSubTask(new SubTask(6,"умыться", "чистота залог здоровья", "NEW", 4));
        System.out.println();
        System.out.println();
        System.out.println(taskManager.showAllTasks());
        System.out.println(taskManager.showAllEpicTasks());
        System.out.println(taskManager.showAllSubTasks());
        System.out.println();
        System.out.println();
        System.out.println(taskManager.showAllTasks());
        System.out.println(taskManager.showAllEpicTasks());
        System.out.println(taskManager.showAllSubTasks());
    }
}
