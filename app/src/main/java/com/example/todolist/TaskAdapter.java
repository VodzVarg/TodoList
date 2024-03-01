package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_view_item, parent, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.title.setText(task.getTitle());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = holder.getAdapterPosition();
                tasks.remove(position);
                notifyItemRemoved(position);
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                View dialogView = inflater.inflate(R.layout.dialog_edit_task, null);
                EditText taskTitleEditText = dialogView.findViewById(R.id.taskTitleEditText);

                builder.setView(dialogView)
                        .setPositiveButton("Update", (dialog, id) -> {
                            String title = taskTitleEditText.getText().toString();
                            if (!title.isEmpty()) {
                                Task task = tasks.get(position);
                                task.setTitle(title);
                                notifyItemChanged(position);
                            }
                        })
                        .setNegativeButton("Cancel", (dialog, id) -> {
                        });

                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public TaskViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.taskTitle);
        }
    }
}