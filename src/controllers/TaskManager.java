package controllers;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    int countIdTask();

    ArrayList<Task> getTaskList();

    ArrayList<SubTask> getSubTaskList();

    ArrayList<Epic> getEpicList();

    void deleteTaskList();

    void deleteSubTaskList();

    void deleteEpicList();

    Task getTaskForID(int id);

    SubTask getSubTaskForID(int id);

    Epic getEpicForID(int id);

    Task createTask(Task task);

    SubTask createSubTask(SubTask subTask);

    Epic createEpic(Epic epic);

    void updateTask(Task task);

    void updateSubTask(SubTask subTask);

    void updateEpic(Epic epic);

    void deleteTaskForID(int id);

    void deleteSubTaskForID(int id);

    void deleteEpicForID(int id);

}
