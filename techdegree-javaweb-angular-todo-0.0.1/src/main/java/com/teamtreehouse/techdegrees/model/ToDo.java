package com.teamtreehouse.techdegrees.model;

public class ToDo {
    private int id;
    private String name;
    private boolean completed;

    public ToDo(String name, boolean completed) {
        this.name = name;
        completed = completed;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ToDo toDo = (ToDo) o;

        if (id != toDo.id) return false;
        if (completed != toDo.completed) return false;
        return name != null ? name.equals(toDo.name) : toDo.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (completed ? 1 : 0);
        return result;
    }
}
