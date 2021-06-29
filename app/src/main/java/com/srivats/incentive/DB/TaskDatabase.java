package com.srivats.incentive.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.srivats.incentive.Helper.TaskDao;
import com.srivats.incentive.Helper.TaskModal;

@Database(entities = {TaskModal.class }, version = 1)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao getTaskDao();

    private static TaskDatabase taskDatabase;

    public static TaskDatabase getInstance(Context context){
        if(null == taskDatabase){
            taskDatabase = buildDatabaseInstance(context);
        }
        return taskDatabase;
    }

    private static TaskDatabase buildDatabaseInstance(Context context){
        return Room.databaseBuilder(context, TaskDatabase.class, "Task_table").allowMainThreadQueries().build();
    }

    public void cleanUp(){
        taskDatabase = null;
    }

}
