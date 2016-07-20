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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
        adapt = new MyAdapter(list);
        listView = (ListView) findViewById(R.id.list_main);
        listView.setAdapter(adapt);

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteTask();
                list = db.getAllTasks();
                Log.d(TAG, "onClick: " + list.size());
                adapt.notifyDataSetChanged();
            }
        });
    }

    //OnCLick for the button
    //TODO add this task in the database not here
    public void addTaskNow(View v) {
        et_add = (EditText) findViewById(R.id.et_task);
        String s = et_add.getText().toString();
        Task task = new Task(s, 0);

        db.addTask(task);
        list = db.getAllTasks();

        adapt.notifyDataSetChanged();

        et_add.setText("");
    }


    public class MyAdapter extends BaseAdapter {

        ArrayList<Task> taskList = null;


        public MyAdapter(List<Task> objects) {
            this.taskList = (ArrayList<Task>) objects;
        }

        @Override
        public int getCount() {
            return taskList.size();
        }

        @Override
        public Object getItem(int position) {
            return taskList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            CheckBox chk = null;
            LayoutInflater li = getLayoutInflater();
            if (convertView == null) {
                convertView = li.inflate(R.layout.list_layout, null);
                chk = (CheckBox) convertView.findViewById(R.id.checkBox);
                convertView.setTag(chk);
            } else {
                chk = (CheckBox) convertView.getTag();
            }
            chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final Task checkTask = taskList.get(position);
                    if (isChecked) {
                        checkTask.setStatus(1);
                        buttonView.setTextColor(Color.WHITE);
                        db.updateTask(checkTask);
                    } else {
                        checkTask.setStatus(0);
                        buttonView.setTextColor(Color.RED);
                        Toast.makeText(getApplicationContext(), "update function " + buttonView.getText() + " " + isChecked, Toast.LENGTH_SHORT).show();
                        db.updateTask(checkTask);
                    }
                }
            });
            Task current = taskList.get(position);
            Log.d(TAG, "getView: " + current.getTaskName());
//            tvDate.setText(current.getDate());
            chk.setText(current.getTaskName());
            chk.setChecked((current.getStatus() == 1) ? true : false);
            chk.setTag(current);
            return convertView;
        }
    }
}
