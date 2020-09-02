package com.srivats.incentive.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srivats.incentive.DB.TaskDatabase;
import com.srivats.incentive.Helper.TaskDao;
import com.srivats.incentive.Helper.TaskModal;
import com.srivats.incentive.MainActivity;
import com.srivats.incentive.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<TaskModal> taskList;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnNoteItemClick onNoteItemClick;


    public TaskAdapter(List<TaskModal> taskList, Context context){
        layoutInflater = LayoutInflater.from(context);
        this.taskList = taskList;
        this.context = context;
        this.onNoteItemClick = (OnNoteItemClick) context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.task_display, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.titlePlaceHolder.setText("Task: " + taskList.get(position).getTask_name());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView titlePlaceHolder, displayTaskDesc, displayTaskReward;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            titlePlaceHolder = itemView.findViewById(R.id.task_title_tv);
//            displayTaskDesc = itemView.findViewById(R.id.task_desc_tv);
//            displayTaskReward = itemView.findViewById(R.id.task_reward_tv);
        }

        @Override
        public void onClick(View view) {
            onNoteItemClick.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteItemClick {
        void onNoteClick(int pos);
    }
}
