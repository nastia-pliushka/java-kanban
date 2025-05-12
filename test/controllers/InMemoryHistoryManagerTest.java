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

class InMemoryHistoryManagerTest {

    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }
//убедимся, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данные.
    @Test
    public void savePreviousVersionTaskInStory() {
        final Task cleanWindows = taskManager.createTask(new Task("Windows", "Clean the windows"));
        taskManager.getTaskForID(cleanWindows.getId());
        taskManager.updateTask(new Task("new Windows", "Wash the window in the kitchen", cleanWindows.getId(), Status.DONE));
        List<Task> task = taskManager.getHistory();
        Task oldTask = task.getFirst();
        assertEquals(oldTask, cleanWindows, "В истории не сохранилась старая версия задачи");
        assertEquals(oldTask.getName(), cleanWindows.getName(), "В истории не сохранилась старая версия задачи");
        assertEquals(oldTask.getDescription(), cleanWindows.getDescription(), "В истории не сохранилась старая версия задачи");
    }
}