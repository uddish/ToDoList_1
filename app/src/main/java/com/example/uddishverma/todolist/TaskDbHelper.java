package com.example.uddishverma.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.uddishverma.todolist.Db.TaskTable;
import com.example.uddishverma.todolist.models.Task;

import java.util.ArrayList;

/**
 * Created by UddishVerma on 15/07/16.
 */
public class TaskDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "taskmanager";
    public static final int DB_VER = 1;


    public TaskDbHelper(Context context) {
        super(context, DB_NAME,null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TaskTable.TABLE_CREATE_CMD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TaskTable.TABLE_UPGRADE_CMD);
        onCreate(db);
    }

    public void addTask(Task task)  {
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //adding the values in the respective columns
        values.put(TaskTable.Columns.NAME, task.getTaskName());
        values.put(TaskTable.Columns.STATUS, task.getStatus());
        db.insert(TaskTable.TABLE_NAME, null, values);
        db.close();
    }


    // this function adds the details into an arraylist so that it can be accessed later
    public ArrayList<Task> getAllTasks()    {

        ArrayList<Task> taskList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TaskTable.TABLE_NAME + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(c.getInt(0));
                task.setTaskName(c.getString(1));
                task.setStatus(c.getInt(2));
                taskList.add(task);
            }
            while(c.moveToNext());
        }
        return taskList;
    }

    public void updateTask(Task task)    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //we are not putting ID here because it is AUTO_INCREMENTED
        values.put(TaskTable.Columns.NAME, task.getTaskName());
        values.put(TaskTable.Columns.STATUS, task.getStatus());
        //TODO search about the below statement
        db.update(TaskTable.TABLE_NAME, values, TaskTable.Columns.ID + " =?", new String[]{String.valueOf(task.getId())});
    }

    public void deleteTask(Task task)   {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TaskTable.TABLE_NAME, TaskTable.Columns.ID + " =?", new String[]{String.valueOf(task.getId())});
    }
}
