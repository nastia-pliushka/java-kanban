package model;

import java.util.Objects;

public class Task {

    protected String name;
    protected String description;
    protected int id;
    protected Status status;

    public Task (String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    public Task (String name, String description, int id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id = " + id +
                ", name = " + name +
                ", description = " + description +
                ", status = " + status +
                "}";
    }
}
