package com.srivats.incentive.OfflineScreen;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.srivats.incentive.Adapter.TaskAdapter;
import com.srivats.incentive.Common.SettingsActivity;
import com.srivats.incentive.DB.TaskDatabase;
import com.srivats.incentive.Helper.TaskModal;
import com.srivats.incentive.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnNoteItemClick{
    //Empty notes
    private ConstraintLayout emptyLayout;
    private TaskDatabase database;
    private List<TaskModal> taskModal;
    private TaskAdapter taskAdapter;
    private  int pos;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setElevation(0);
        myToolbar.setBackgroundColor(Color.parseColor("#ffffff"));
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        loadView();
        displayList();
    }

    private void loadView(){
        emptyLayout = findViewById(R.id.empty_notes);
        FloatingActionButton fab = findViewById(R.id.addTask);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        taskModal = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskModal, MainActivity.this);
        recyclerView.setAdapter(taskAdapter);

        fab.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), AddTask.class);
            startActivity(i);
        });

        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        TaskModal modal = taskAdapter.taskAtPosition(position);
                        database.getTaskDao().deleteTask(modal);
                        taskModal.remove(position);
                        Toast rewardToast = Toast.makeText(getApplicationContext(), "You can now enjoy your reward", Toast.LENGTH_LONG);
                        rewardToast.setGravity(Gravity.CENTER, 0, 0);
                        rewardToast.show();
                        listVisibility();
                    }
                }
        );

        touchHelper.attachToRecyclerView(recyclerView);
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
                .setTitle("Select Options")
                .setItems(new String[]{"View", "Update"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                MainActivity.this.pos = pos;
                                startActivityForResult(
                                        new Intent(
                                                MainActivity.this,
                                                ViewTask.class).putExtra("task", taskModal.get(pos)),
                                        100
                                );
                                break;
                            case 1:
                                MainActivity.this.pos = pos;
                                startActivityForResult(
                                        new Intent(MainActivity.this,
                                                EditTask.class).putExtra("task", taskModal.get(pos)),
                                        100);

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

        private final WeakReference<MainActivity> activityReference;

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
        if (item.getItemId() == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
        }// If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        database.cleanUp();
        super.onDestroy();
    }
}