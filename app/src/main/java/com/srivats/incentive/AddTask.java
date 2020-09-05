package com.srivats.incentive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.srivats.incentive.DB.TaskDatabase;
import com.srivats.incentive.Helper.TaskModal;

import java.lang.ref.WeakReference;

public class AddTask extends AppCompatActivity {
    ImageButton backBtn;
    Button addTask;
    TextInputLayout taskName, taskDesc, taskReward;
    private TaskDatabase taskDatabase;
    private TaskModal taskModal;
    ProgressBar pBar;
//    EditText taskName, taskDesc, taskReward;
//    String taskNameToSave, taskDescToSave, taskRewardToSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        backBtn = findViewById(R.id.viewToHome);
        addTask = findViewById(R.id.addTaskToDB);
        pBar = findViewById(R.id.progressBar);
        //Edit Text related
        taskName = findViewById(R.id.taskNameEditText);
        taskDesc = findViewById(R.id.taskDescEditText);
        taskReward = findViewById(R.id.taskRewardEditText);
        taskName.getEditText().setInputType(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        taskDesc.getEditText().setInputType(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        taskReward.getEditText().setInputType(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        //Database related
        taskDatabase = TaskDatabase.getInstance(AddTask.this);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pBar.setVisibility(View.VISIBLE);
                if(taskName.getEditText().getText().toString().isEmpty() || taskDesc.getEditText().getText().toString().isEmpty()
                || taskReward.getEditText().getText().toString().isEmpty()){
                    Toast errorToast = Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT);
                    errorToast.setGravity(Gravity.CENTER, 0, 0);
                    errorToast.show();
                }
                else{
                    taskModal = new TaskModal(
                            taskName.getEditText().getText().toString(),
                            taskDesc.getEditText().getText().toString(),
                            taskReward.getEditText().getText().toString()
                    );
                    new InsertTask(AddTask.this, taskModal).execute();
                    pBar.setVisibility(View.GONE);
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private static class InsertTask extends AsyncTask<Void, Void, Boolean>{

        private WeakReference<AddTask> addTaskReference;
        private TaskModal modal;

        InsertTask(AddTask context, TaskModal modal){
            addTaskReference = new WeakReference<>(context);
            this.modal = modal;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            long j = addTaskReference.get().taskDatabase.getTaskDao().addTask(modal);
            modal.setTask_id(j);
            return true;
        }
    }
}