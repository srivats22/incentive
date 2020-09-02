package com.srivats.incentive.Helper;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
   @Query("SELECT * From Task_table")
   List<TaskModal> getTasks();

   @Insert
    long addTask(TaskModal taskModal);

   @Update
    void updateTask(TaskModal updatedTask);

   @Delete
    void deleteTask(TaskModal taskModal);

   @Delete
    void deleteTasks(TaskModal... taskModal);

}
