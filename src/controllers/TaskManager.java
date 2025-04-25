package controllers;

import model.Task;
import model.SubTask;
import model.Epic;
import model.Status;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int idTask = 0;

    private HashMap<Integer, Task> taskList;
    private HashMap<Integer, SubTask> subTaskList;
    private HashMap<Integer, Epic> epicList;

    public TaskManager(){
        taskList = new HashMap<>();
        subTaskList = new HashMap<>();
        epicList = new HashMap<>();
    }

    private int countIdTask(){
        idTask++;
        return idTask;
    }

    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(taskList.values());
    }

    public ArrayList<SubTask> getSubTaskList() {
        return new ArrayList<>(subTaskList.values());
    }

    public ArrayList<Epic> getEpicList() {
        return new ArrayList<>(epicList.values());
    }

    public void deleteTaskList() {
        taskList.clear();
    }

    public void deleteSubTaskList() {
        subTaskList.clear();
        for (Epic epic : epicList.values()) {
            epic.getSubtask().clear();
            updateEpicStatus(epic.getId());
        }
    }

    public void deleteEpicList() {
        epicList.clear();
        deleteSubTaskList();
    }

    public Task getTaskForID(int id) {
        return taskList.get(id);
    }

    public SubTask getSubTaskForID(int id) {
        return subTaskList.get(id);
    }

    public Epic getEpicForID(int id) {
        return epicList.get(id);
    }

    public Task createTask(Task task) {
        int id = countIdTask();
        task.setId(id);
        taskList.put(task.getId(), task);
        return task;
    }

    public SubTask createSubTask(SubTask subTask){
        Epic epic = epicList.get(subTask.getEpicId());
        if (epic == null) {
            return null;
        }
        int id = countIdTask();
        subTask.setId(id);
        subTaskList.put(subTask.getId(), subTask);
        epic.addSubtask(subTask.getId());
        updateEpicStatus(epic.getId());
        return subTask;
    }

    public Epic createEpic(Epic epic){
        int id = countIdTask();
        epic.setId(id);
        epicList.put(epic.getId(), epic);
        return epic;
    }

    public void updateTask(Task task) {
        taskList.put(task.getId(), task);
    }

    public void updateSubTask(SubTask subTask) {
        subTaskList.put(subTask.getId(), subTask);
        updateEpicStatus((subTask.getEpicId()));
    }

    public void updateEpic(Epic epic) {
        epicList.put(epic.getId(), epic);
    }

    public void updateEpicStatus(int epicId){
        boolean statusNew = true;
        boolean statusDone = true;

        Epic epic = epicList.get(epicId);
        ArrayList<Integer> subTasks = epic.getSubtask();

        if (epic == null) {
            return;
        } else if (subTasks.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        for (int subTaskID : subTasks) {
            SubTask subTask = subTaskList.get(subTaskID);
            if (subTask == null) {
                continue;
            }
            if (subTask.getStatus().equals(Status.IN_PROGRESS)) {
                epic.setStatus(Status.IN_PROGRESS);
                return;
            }
            if (!subTask.getStatus().equals(Status.NEW)) {
                statusNew = false;
                return;
            }
            if (!subTask.getStatus().equals(Status.DONE)) {
                statusDone = false;
            }
        }
        if (statusDone) {
            epic.setStatus((Status.DONE));
        } else if (statusNew) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus((Status.IN_PROGRESS));
        }
    }

    public  void deleteTaskForID(int id) {
        taskList.remove(id);
    }

    public void deleteSubTaskForID(int id) {
        SubTask subTask = subTaskList.get(id);
        subTaskList.remove(id);
        Epic epic = epicList.get(subTask.getEpicId());
        if (epic != null) {
            epic.removeSubtask(id);
            updateEpicStatus(epic.getId());
        }
    }

    public void deleteEpicForID(int id) {
        Epic epic = epicList.get(id);
        for (int subTaskID : epic.getSubtask()) {
            subTaskList.remove(subTaskID);
        }
        epicList.remove(id);
    }

    public ArrayList<SubTask> getSubTaskForEpic(int epicId) {
        ArrayList<SubTask> array = new ArrayList<>();
        Epic epic = epicList.get(epicId);
        if (epic != null) {
            for (int subTaskId : epic.getSubtask()) {
                array.add(subTaskList.get(subTaskId));
            }
        }
        return array;
    }
}
