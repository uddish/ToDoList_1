package com.example.uddishverma.todolist.Db;

/**
 * Created by UddishVerma on 15/07/16.
 */
public class TaskTable extends Utilities {

    public static final String TABLE_NAME = "tasks";

    public interface Columns    {

        String ID = "id";
        String NAME = "taskName";
        String STATUS = "status";
    }

    public static final String TABLE_CREATE_CMD =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                    + LBR
                    + Columns.ID + TYPE_INT_PK + COMMA
                    + Columns.NAME + TYPE_TEXT + COMMA
                    + Columns.STATUS + TYPE_INT
                    + RBR + ";";

    public static final String TABLE_UPGRADE_CMD =
            "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


}
