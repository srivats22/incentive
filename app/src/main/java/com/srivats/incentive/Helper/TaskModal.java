package com.srivats.incentive.Helper;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Task_table")
public class TaskModal implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long task_id;
    @NonNull
    @ColumnInfo(name = "task_content")
    private String task_name;
    private String task_desc;
    private String task_reward;
    private String priority;

    public TaskModal(String task_name, String task_desc, String task_reward, String priority){
        this.task_name = task_name;
        this.task_desc = task_desc;
        this.task_reward = task_reward;
        this.priority = priority;
    }

    public long getTask_id() {
        return task_id;
    }

    public void setTask_id(long task_id) {
        this.task_id = task_id;
    }

    @NonNull
    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(@NonNull String task_name) {
        this.task_name = task_name;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public void setTask_desc(String task_desc) {
        this.task_desc = task_desc;
    }

    public String getTask_reward() {
        return task_reward;
    }

    public void setTask_reward(String task_reward) {
        this.task_reward = task_reward;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
