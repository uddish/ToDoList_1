package com.example.uddishverma.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uddishverma.todolist.models.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    ListView listView;
    ArrayList<Task> list = new ArrayList<>();
    TaskDbHelper db;
    Button btnRemove;
    EditText et_add, etDate;
    MyAdapter adapt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new TaskDbHelper(this);
        list = db.getAllTasks();
        btnRemove = (Button) findViewById(R.id.btn_remove);
        adapt = new MyAdapter(this, R.layout.list_layout, list);
        listView = (ListView) findViewById(R.id.list_main);
        listView.setAdapter(adapt);
    }
    //OnCLick for the button
    public void addTaskNow(View v)  {
        et_add = (EditText) findViewById(R.id.et_task);
        String s = et_add.getText().toString();
        Task task = new Task(s, 0);
        db.addTask(task);
        et_add.setText("");
        adapt.add(task);
        adapt.notifyDataSetChanged();

    }


    public class MyAdapter extends ArrayAdapter<Task>   {

        Context context;
        ArrayList<Task> taskList = new ArrayList<>();
        int layoutResourceId;

        public MyAdapter(Context context, int layoutResourceId,
                         List<Task> objects) {
            super(context, layoutResourceId, objects);
            this.layoutResourceId = layoutResourceId;
            this.taskList = (ArrayList<Task>) objects;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CheckBox chk = null;
            LayoutInflater li = getLayoutInflater();
            if(convertView == null) {
                convertView = li.inflate(R.layout.list_layout, null);
                chk = (CheckBox) convertView.findViewById(R.id.checkBox);
                convertView.setTag(chk);

                if (chk != null) {
                    chk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                           In other words, it asserts to the compiler that v has type CheckBox
//                           and thus it is OK to call the methods of CheckBox on v.
                            CheckBox cb = (CheckBox) v;
                            final Task checkTask = (Task) cb.getTag();
                            if(!cb.isChecked()) {
                                checkTask.setStatus(0);
                                cb.setTextColor(Color.RED);
                                Toast.makeText(getApplicationContext(), "update function " + cb.getText() + " " +  cb.isChecked(), Toast.LENGTH_SHORT).show();
                                db.updateTask(checkTask);
                            }
                            else    {
                                checkTask.setStatus(1);
                                cb.setTextColor(Color.WHITE);
                                    btnRemove.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(checkTask.getStatus() == 1) {
                                                db.deleteTask(checkTask);
                                                adapt.remove(checkTask);
                                                adapt.notifyDataSetChanged();
                                            }
                                        }
                                    });
                                //To strike through the text
//                                cb.setPaintFlags(cb.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                Toast.makeText(getApplicationContext(), "update function " + cb.getText() + " " + cb.isChecked(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
            else    {
                chk = (CheckBox) convertView.getTag();
            }
            Task current = taskList.get(position);
            Log.d(TAG, "getView: " + current.getTaskName());
//            tvDate.setText(current.getDate());
            chk.setText(current.getTaskName());
            chk.setChecked((current.getStatus()==1) ? true : false);
            chk.setTag(current);
            return convertView;
        }
    }
}
