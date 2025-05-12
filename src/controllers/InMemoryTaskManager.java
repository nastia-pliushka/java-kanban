package controllers;

import model.Task;
import model.SubTask;
import model.Epic;
import model.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private int idTask = 0;

    private final HashMap<Integer, Task> taskList;
    private final HashMap<Integer, SubTask> subTaskList;
    private final HashMap<Integer, Epic> epicList;
    private final HistoryManager historyManager;

    public InMemoryTaskManager(){
        taskList = new HashMap<>();
        subTaskList = new HashMap<>();
        epicList = new HashMap<>();
        historyManager = new Managers().getDefaultHistory();
    }

    @Override
    public int countIdTask(){
        idTask++;
        return idTask;
    }

    @Override
    public ArrayList<Task> getTaskList() {

        return new ArrayList<>(taskList.values());
    }

    @Override
    public ArrayList<SubTask> getSubTaskList() {

        return new ArrayList<>(subTaskList.values());
    }

    @Override
    public ArrayList<Epic> getEpicList() {

        return new ArrayList<>(epicList.values());
    }

    @Override
    public void deleteTaskList() {

        taskList.clear();
    }

    @Override
    public void deleteSubTaskList() {
        subTaskList.clear();
        for (Epic epic : epicList.values()) {
            epic.getSubtask().clear();
            updateEpicStatus(epic.getId());
        }
    }

    @Override
    public void deleteEpicList() {
        epicList.clear();
        deleteSubTaskList();
    }

    @Override
    public Task getTaskForID(int id) {
        Task task = taskList.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public SubTask getSubTaskForID(int id) {
        SubTask subTask = subTaskList.get(id);
        if (subTask !=null) {
            historyManager.add(subTask);
        }
        return subTask;
    }

    @Override
    public Epic getEpicForID(int id) {
        Epic epic = epicList.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Task createTask(Task task) {
        int id = countIdTask();
        task.setId(id);
        taskList.put(task.getId(), task);
        return task;
    }

    @Override
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

    @Override
    public Epic createEpic(Epic epic){
        int id = countIdTask();
        epic.setId(id);
        epicList.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public void updateTask(Task task) {
        taskList.put(task.getId(), task);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        subTaskList.put(subTask.getId(), subTask);
        updateEpicStatus((subTask.getEpicId()));
    }

    @Override
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

    @Override
    public void deleteTaskForID(int id) {
        taskList.remove(id);
    }

    @Override
    public void deleteSubTaskForID(int id) {
        SubTask subTask = subTaskList.get(id);
        subTaskList.remove(id);
        Epic epic = epicList.get(subTask.getEpicId());
        if (epic != null) {
            epic.removeSubtask(id);
            updateEpicStatus(epic.getId());
        }
    }

    @Override
    public void deleteEpicForID(int id) {
        Epic epic = epicList.get(id);
        for (int subTaskID : epic.getSubtask()) {
            subTaskList.remove(subTaskID);
        }
        epicList.remove(id);
    }

    @Override
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

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
