package com.srivats.incentive;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.srivats.incentive.Adapter.TaskAdapter;
import com.srivats.incentive.DB.TaskDatabase;
import com.srivats.incentive.Helper.TaskModal;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnNoteItemClick{
    //Empty notes
    private ConstraintLayout emptyLayout;
    private RecyclerView recyclerView;
    private TaskDatabase database;
    private List<TaskModal> taskModal;
    private TaskAdapter taskAdapter;
    private FloatingActionButton fab;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setElevation(0);
        myToolbar.setBackgroundColor(Color.parseColor("#ffffff"));
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        loadView();
        displayList();
    }

    private void loadView(){
        emptyLayout = findViewById(R.id.empty_notes);
        fab = findViewById(R.id.addTask);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        taskModal = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskModal, MainActivity.this);
        recyclerView.setAdapter(taskAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddTask.class);
                startActivity(i);
            }
        });
    }

    private void listVisibility(){
        int emptyVisibility = View.GONE;
        if(taskModal.size() == 0){
            if(emptyLayout.getVisibility() == View.GONE){
                emptyVisibility = View.VISIBLE;
            }
        }
        emptyLayout.setVisibility(emptyVisibility);
        taskAdapter.notifyDataSetChanged();
    }

    public void onNoteClick(final int pos) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Options:")
                .setItems(new String[]{"View Tasks","DELETE"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(MainActivity.this, ViewTask.class);
                                intent.putExtra("taskName", taskModal.get(pos).getTask_name());
                                intent.putExtra("taskDesc", taskModal.get(pos).getTask_desc());
                                intent.putExtra("taskReward", taskModal.get(pos).getTask_reward());
                                startActivity(intent);
                                break;
                            case 1:
                                database.getTaskDao().deleteTask(taskModal.get(pos));
                                taskModal.remove(pos);
                                Toast deleteToast = Toast.makeText(getApplicationContext(), "Task Deleted", Toast.LENGTH_SHORT);
                                deleteToast.setGravity(Gravity.CENTER, 0, 0);
                                deleteToast.show();

                                Toast rewardToast = Toast.makeText(getApplicationContext(), "You can now enjoy your reward", Toast.LENGTH_LONG);
                                rewardToast.setGravity(Gravity.CENTER, 0, 0);
                                rewardToast.show();
                                listVisibility();
                                break;
                        }
                    }
                }).show();
    }

    private void displayList() {
        database = TaskDatabase.getInstance(MainActivity.this);
        new RetrieveTask(this).execute();
    }

    private static class RetrieveTask extends AsyncTask<Void, Void, List<TaskModal>> {

        private WeakReference<MainActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<TaskModal> doInBackground(Void... voids) {
            if (activityReference.get() != null)
                return activityReference.get().database.getTaskDao().getTasks();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<TaskModal> notes) {
            if (notes != null && notes.size() > 0) {
                activityReference.get().taskModal.clear();
                activityReference.get().taskModal.addAll(notes);
                // hides empty text view
                activityReference.get().emptyLayout.setVisibility(View.GONE);
                activityReference.get().taskAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        database.cleanUp();
        super.onDestroy();
    }
}