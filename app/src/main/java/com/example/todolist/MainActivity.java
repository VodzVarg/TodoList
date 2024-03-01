package com.example.todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        taskAdapter = new TaskAdapter(tasks);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем AlertDialog для ввода названия задачи
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_add_task, null);
                EditText taskTitleEditText = dialogView.findViewById(R.id.taskTitleEditText);

                builder.setView(dialogView)
                        .setPositiveButton("Add", (dialog, id) -> {
                            String title = taskTitleEditText.getText().toString();
                            if (!title.isEmpty()) {
                                // Создаем новую задачу и добавляем ее в список
                                Task task = new Task(tasks.size(), title, false);
                                tasks.add(task);
                                taskAdapter.notifyItemInserted(tasks.size() - 1);
                            }
                        })
                        .setNegativeButton("Cancel", (dialog, id) -> {
                            // Закрываем диалог, если пользователь нажимает "Cancel"
                        });

                builder.create().show();
            }
        });
    }
}