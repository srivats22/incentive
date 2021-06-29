package com.srivats.incentive.OfflineScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.srivats.incentive.DB.TaskDatabase;
import com.srivats.incentive.Helper.TaskModal;
import com.srivats.incentive.R;

import java.util.ArrayList;
import java.util.List;

public class EditTask extends AppCompatActivity {
    EditText taskName, taskDesc, taskReward;
    Spinner prioritySpinner;
    Button saveChangedBtn;
    private TaskDatabase taskDB;
    private TaskModal taskModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        taskModal = (TaskModal) getIntent().getSerializableExtra("task");
        taskName = findViewById(R.id.EditTaskNameEditText);
        taskName.setText(taskModal.getTask_name());
        taskDesc = findViewById(R.id.EditDescriptionEditText);
        taskDesc.setText(taskModal.getTask_desc());
        taskReward = findViewById(R.id.EditRewardEditText);
        taskReward.setText(taskModal.getTask_reward());
        taskDB = TaskDatabase.getInstance(EditTask.this);
        saveChangedBtn = findViewById(R.id.saveChangesBtn);
        prioritySpinner = findViewById(R.id.EditPrioritySpinner);

        List<String> priorities = new ArrayList<>();
        priorities.add("High");
        priorities.add("Medium");
        priorities.add("Default");

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, priorities);
        prioritySpinner.setAdapter(priorityAdapter);


        saveChangedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskModal.setTask_name(taskName.getText().toString());
                taskModal.setTask_desc(taskDesc.getText().toString());
                taskModal.setTask_reward(taskReward.getText().toString());
                taskModal.setPriority(prioritySpinner.getSelectedItem().toString());
                taskDB.getTaskDao().updateTask(taskModal);
                Toast.makeText(getApplicationContext(), "Task Updated", Toast.LENGTH_LONG).show();
                Intent i = new Intent(EditTask.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}