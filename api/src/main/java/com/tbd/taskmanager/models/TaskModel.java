package com.tbd.taskmanager.models;

public class TaskModel {

    // Attributes
    private int task_id;
    private int user_id;
    private String title;
    private String description;
    private String expiration_date;
    private String state;

    // Default constructor
    public TaskModel() {
    }

    // TaskModel (Constructor)
    public TaskModel(int task_id, int user_id, String title, String description, String expiration_date, String state) {
        this.task_id = task_id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.expiration_date = expiration_date;
        this.state = state;
    }

    // Getters
    public int getTask_id() {
        return task_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public String getState() {
        return state;
    }

    // Setters
    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public void setState(String state) {
        this.state = state;
    }
}
