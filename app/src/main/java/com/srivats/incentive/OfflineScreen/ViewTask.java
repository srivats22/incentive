package com.srivats.incentive.OfflineScreen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.srivats.incentive.DB.TaskDatabase;
import com.srivats.incentive.Helper.TaskModal;
import com.srivats.incentive.R;

import java.util.Objects;

public class ViewTask extends AppCompatActivity{
    TextView taskNameDisplay, taskDescDisplay, taskRewardDisplay, priority;
    ImageButton backBtn;
    private TaskModal taskModal;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        Toolbar myToolbar = findViewById(R.id.viewTaskToolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        taskModal = (TaskModal) getIntent().getSerializableExtra("task");
        String taskName = taskModal.getTask_name();
        String taskDesc = taskModal.getTask_desc();
        String taskReward = taskModal.getTask_reward();
        String taskPriority = taskModal.getPriority();

        taskNameDisplay = findViewById(R.id.detailTaskName);
        taskDescDisplay = findViewById(R.id.detailTaskDesc);
        taskRewardDisplay = findViewById(R.id.detailTaskReward);
        priority = findViewById(R.id.textViewPriority);
        backBtn = findViewById(R.id.viewToHome);

        taskNameDisplay.setText(String.format("Task Name: %s", taskName));
        taskDescDisplay.setText(String.format("Task Description: %s", taskDesc));
        taskRewardDisplay.setText(String.format("Task Reward: %s", taskReward));
        priority.setText(String.format("Task Priority: %s", taskPriority));

        backBtn.setOnClickListener(view -> {
            Intent i = new Intent(ViewTask.this, MainActivity.class);
            startActivity(i);
        });
    }
}