package com.srivats.incentive;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.srivats.incentive.Adapter.TaskAdapter;
import com.srivats.incentive.DB.TaskDatabase;
import com.srivats.incentive.Helper.TaskModal;

import java.util.List;

public class ViewTask extends AppCompatActivity{
    TextView taskNameDisplay, taskDescDisplay, taskRewardDisplay;
    ImageButton backBtn;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        Toolbar myToolbar = findViewById(R.id.viewTaskToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        String taskName = getIntent().getStringExtra("taskName");
        String taskDesc = getIntent().getStringExtra("taskDesc");
        String taskReward = getIntent().getStringExtra("taskReward");

        taskNameDisplay = findViewById(R.id.detailTaskName);
        taskDescDisplay = findViewById(R.id.detailTaskDesc);
        taskRewardDisplay = findViewById(R.id.detailTaskReward);
        backBtn = findViewById(R.id.viewToHome);

        taskNameDisplay.setText(taskName);
        taskDescDisplay.setText(taskDesc);
        taskRewardDisplay.setText(taskReward);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewTask.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}