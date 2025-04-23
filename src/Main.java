public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        //TASK
        Task task1 = new Task("Window", "Wash window");
        Task task2 = new Task("Cake", "Cooking biscotti");
        Task task3 = new Task("Shop", "Buy tomato");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        System.out.println("\nСписок задач:\n");
        System.out.println(taskManager.getTaskList());

        //получить ид задачи и удалить ее
        System.out.println("\nБудет удалена задача:\n");
        System.out.println(taskManager.getTaskForID(task1.getId()));
        taskManager.deleteTaskForID(task1.getId());
        System.out.println("\nСписок оставшихся задач:\n");
        System.out.println(taskManager.getTaskList());

        //обновить задачу
        task2.setName("Pie");
        taskManager.updateTask(task2);
        System.out.println("\nСписок обновленных задач:\n");
        System.out.println(taskManager.getTaskList());

        //удалить все задачи
        taskManager.deleteTaskList();
        System.out.println("\nСписок задач:\n");
        System.out.println(taskManager.getTaskList());

        //EPIC
        Epic epic = new Epic("List street", "Name street");
        Epic epic1 = new Epic("Clothes", "Casual wear");
        taskManager.createEpic(epic);
        taskManager.createEpic(epic1);
        System.out.println("\nСписок эпиков:\n");
        System.out.println(taskManager.getEpicList());

        //SUBTASK
        SubTask subTask1 = new SubTask("Street", "Soap and rope", epic.getId());
        SubTask subTask2 = new SubTask("Alley", "Diagon", epic.getId());
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        System.out.println("\nEpic с подзадачами:\n");
        System.out.println(taskManager.getEpicForID(epic.getId()));
        System.out.println("\nПодзадачи Эпика:\n");
        System.out.println(taskManager.getSubTaskForEpic(epic.getId()));

        //Обновить статус подзадач и эпика
        subTask1.setStatus(Status.DONE);
        subTask2.setStatus(Status.IN_PROGRESS);
        taskManager.updateEpicStatus(epic.getId());
        System.out.println("\nОбновили статус у эпика: " + epic.getId());
        System.out.println("Новый статус: " + epic.getStatus());

        //Удалить все подзадачи эпика и обновить ему статус
        taskManager.deleteSubTaskList();
        System.out.println("\nEpic, в котором были подзадачи:\n");
        System.out.println(taskManager.getEpicForID(epic.getId()));
        System.out.println("\nПодзадачи Эпика:\n");
        System.out.println(taskManager.getSubTaskForEpic(epic.getId()));

        //Удалить эпик по ид
        taskManager.deleteEpicForID(epic.getId());
        System.out.println("\nСписок эпиков после удаления:\n");
        System.out.println(taskManager.getEpicList());
    }
}