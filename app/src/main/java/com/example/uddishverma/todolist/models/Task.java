package com.example.uddishverma.todolist.models;

/**
 * Created by UddishVerma on 15/07/16.
 */
public class Task {

    String taskName;
    String date;
    int id;
    int status;

    public Task()
    {
        this.taskName=null;
        this.status=0;
//        this.date = null;
    }

    public Task(String taskName, int status) {

        this.taskName = taskName;
        this.status = status;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

}
