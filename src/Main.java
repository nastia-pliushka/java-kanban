import controllers.InMemoryHistoryManager;
import controllers.InMemoryTaskManager;
import controllers.Managers;
import model.*;

public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager taskManager = Managers.getDefault();

        //TASK
        Task task1 = new Task("Window", "Wash window");
        Task task2 = new Task("Cake", "Cooking biscotti");
        Task task3 = new Task("Shop", "Buy tomato");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);

        //EPIC
        Epic epic4 = new Epic("List street", "Name street");
        Epic epic5 = new Epic("Clothes", "Casual wear");
        taskManager.createEpic(epic4);
        taskManager.createEpic(epic5);

        //SUBTASK
        SubTask subTask6 = new SubTask("Street", "Soap and rope", epic4.getId());
        SubTask subTask7 = new SubTask("Alley", "Diagon", epic4.getId());
        taskManager.createSubTask(subTask6);
        taskManager.createSubTask(subTask7);

        printAllTasks(taskManager);
        printHistory(taskManager);
    }

    private static void printAllTasks(InMemoryTaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getTaskList()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getEpicList()) {
            System.out.println(epic);

            for (Task task : manager.getSubTaskForEpic(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getSubTaskList()) {
            System.out.println(subtask);
        }
    }

    private static void printHistory(InMemoryTaskManager manager) {
        manager.getTaskForID(1);
        manager.getTaskForID(3);
        manager.getTaskForID(2);
        manager.getTaskForID(3);
        manager.getEpicForID(4);
        manager.getEpicForID(5);
        manager.getSubTaskForID(6);
        manager.getSubTaskForID(7);

        System.out.println("\nИстория:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}