package com.srivats.incentive.Helper;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
   @Query("SELECT * From Task_table ORDER BY priority")
   List<TaskModal> getTasks();

   @Insert
    long addTask(TaskModal taskModal);

   @Update
    void updateTask(TaskModal taskModal);

   @Delete
    void deleteTask(TaskModal taskModal);

   @Delete
    void deleteTasks(TaskModal... taskModal);

}
