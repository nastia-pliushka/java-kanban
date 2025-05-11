package controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import model.Task;
import model.SubTask;
import model.Epic;
import model.Status;

import java.util.ArrayList;
import java.util.List;

class InMemoryTaskManagerTest {

    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }
//проверяем, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    @Test
    void createSubTaskAndEpics() {
        final Epic apartmentCleaning = taskManager.createEpic(new Epic("Apartment cleaning", "rooms cleaning"));
        final SubTask floors = taskManager.createSubTask(new SubTask("Floors", "Clean the floors", apartmentCleaning.getId()));
        final SubTask windows = taskManager.createSubTask(new SubTask("Windows", "Clean the windows", apartmentCleaning.getId()));

        //ищем эпик и подзадачу по ИД
        final Epic findEpicForID = taskManager.getEpicForID(apartmentCleaning.getId());
        final SubTask findSubTaskForID = taskManager.getSubTaskForID(floors.getId());

        //проверяем найденные задачи
        assertEquals(apartmentCleaning, findEpicForID, "Эпики разные.");
        assertEquals(floors, findSubTaskForID, "Подзадачи разные.");
    }

//проверим неизменность задачи (по всем полям) при добавлении задачи в менеджер
    @Test
    void matchTaskParametrsWhenCreateInTaskManager() {

        final String taskName = "Windows";
        final String taskDescription = "Clean the windows";
        final int taskID = 1;
        final Status taskStatus = Status.IN_PROGRESS;
        final Task windows = new Task(taskName, taskDescription, taskID, taskStatus);
        taskManager.createTask(windows);
        List<Task> taskList = taskManager.getTaskList();
        Task actualTask = taskList.getFirst();
        assertEquals(actualTask.getName(), windows.getName(), "Name задачи сохранился не тот.");
        assertEquals(actualTask.getDescription(), windows.getDescription(), "Description задачи сохранился не тот.");
        assertEquals(actualTask.getId(), windows.getId(), "ID задачи сохранился не тот.");
        assertEquals(actualTask.getStatus(), windows.getStatus(), "Status задачи сохранился не тот.");
    }

//проверим, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    void chekTask1EqualsTask2() {
        final Task task1 = new Task("Windows", "Clean the windows");
        taskManager.createTask(task1);
        int taskID = task1.getId();
        final Task task2 = new Task("new Windows", "Clean the windows in the room", taskID, Status.NEW);
        assertEquals(task1, task2, "Экземпляры класса Task неравны друг другу, но равен их id");
    }
}