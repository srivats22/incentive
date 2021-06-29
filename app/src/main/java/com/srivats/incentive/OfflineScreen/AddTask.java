package com.srivats.incentive.OfflineScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.srivats.incentive.DB.TaskDatabase;
import com.srivats.incentive.Helper.TaskModal;
import com.srivats.incentive.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {
    ImageButton backBtn;
    Button addTask;
    EditText taskName, taskDesc, taskReward;
    private TaskDatabase taskDatabase;
    private TaskModal taskModal;
    Spinner priority;
    ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        backBtn = findViewById(R.id.viewToHome);
        addTask = findViewById(R.id.addTaskToDB);
        pBar = findViewById(R.id.progressBar);
        priority = findViewById(R.id.priority);

        List<String> priorities = new ArrayList<>();
        priorities.add("High");
        priorities.add("Medium");
        priorities.add("Default");

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, priorities);
        priority.setAdapter(priorityAdapter);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        //Edit Text related
        taskName = findViewById(R.id.taskNameEditText);
        taskName.addTextChangedListener(addTaskWatcher);
        taskDesc = findViewById(R.id.taskDescEditText);
        taskDesc.addTextChangedListener(addTaskWatcher);
        taskReward = findViewById(R.id.taskRewardEditText);
        taskReward.addTextChangedListener(addTaskWatcher);
        //Database related
        taskDatabase = TaskDatabase.getInstance(AddTask.this);


        backBtn.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        });

        addTask.setOnClickListener(view -> {
            pBar.setVisibility(View.VISIBLE);
            if(taskName.getText().toString().isEmpty() || taskDesc.getText().toString().isEmpty()
            || taskReward.getText().toString().isEmpty()){
                Toast errorToast = Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT);
                errorToast.setGravity(Gravity.CENTER, 0, 0);
                errorToast.show();
            }
            else{
                taskModal = new TaskModal(
                        taskName.getText().toString(),
                        taskDesc.getText().toString(),
                        taskReward.getText().toString(),
                        priority.getSelectedItem().toString()
                );
                new InsertTask(AddTask.this, taskModal).execute();
                pBar.setVisibility(View.GONE);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    private final TextWatcher addTaskWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String taskNameValue = taskName.getText().toString();
            String taskDescValue = taskDesc.getText().toString();
            String taskRewardValue = taskReward.getText().toString();
            String priorityValue = priority.getSelectedItem().toString();

            if(!taskNameValue.isEmpty() && !taskDescValue.isEmpty() && !taskRewardValue.isEmpty()){
                addTask.setEnabled(true);
                addTask.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                addTask.setOnClickListener(view -> {
                    pBar.setVisibility(View.VISIBLE);
                    taskModal = new TaskModal(
                            taskNameValue,
                            taskDescValue,
                            taskRewardValue,
                            priorityValue
                    );
                    new InsertTask(AddTask.this, taskModal).execute();
                    pBar.setVisibility(View.GONE);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                });
            }
            else{
                addTask.setEnabled(false);
                addTask.setBackgroundColor(getResources().getColor(R.color.disabledBtnColor));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private static class InsertTask extends AsyncTask<Void, Void, Boolean>{

        private final WeakReference<AddTask> addTaskReference;
        private final TaskModal modal;

        InsertTask(AddTask context, TaskModal modal){
            addTaskReference = new WeakReference<>(context);
            this.modal = modal;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            long  j = addTaskReference.get().taskDatabase.getTaskDao().addTask(modal);
            modal.setTask_id(j);
            return true;
        }
    }
}