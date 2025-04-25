package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtask;

    public Epic(String name, String description){
        super(name, description);
        subtask = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtask(){
        return subtask;
    }

    public void addSubtask(int subtaskId){
        subtask.add(subtaskId);
    }

    public void removeSubtask(int subtaskId){
        subtask.remove(Integer.valueOf(subtaskId));
    }

    public void clearSubtasks(){
        subtask.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id = " + id +
                ", name = " + name +
                ", description = " + description +
                ", status = " + status +
                ", subtask = " + subtask +
                "}";
    }
}
