package com.srivats.incentive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.srivats.incentive.Helper.OnlineItems;
import com.srivats.incentive.R;

import java.util.List;

public class OnlineAdapter extends RecyclerView.Adapter<OnlineAdapter.ViewHolder> {

    private List<OnlineItems> tasks;
    private Context context;

    public OnlineAdapter(Context context, List<OnlineItems> tasks){
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public OnlineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.task_display, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToDo:Add intent
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineAdapter.ViewHolder holder, int position) {
        final OnlineItems onlineItems = tasks.get(position);
        holder.titleTextView.setText(onlineItems.getTextTile());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView layout;
        TextView titleTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.task_display);
            titleTextView = itemView.findViewById(R.id.task_title_tv);
        }
    }
}
