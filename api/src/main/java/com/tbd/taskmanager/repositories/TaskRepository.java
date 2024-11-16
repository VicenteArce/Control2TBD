package com.tbd.taskmanager.repositories;

import com.tbd.taskmanager.models.TaskModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskRepository {
    // CRUD

    // Create
    public ResponseEntity<Object> createTask(TaskModel task, String token);

    // Read
    public ResponseEntity<Object> getTask(int task_id, String token);

    public ResponseEntity<List<Object>> getAllTasks(String token);

    public ResponseEntity<List<Object>> getTasksByState(String state, String token);

    public ResponseEntity<List<Object>> getTasksByWord(String word, String token);

    public ResponseEntity<List<Object>> getTasksByDateExpire(String token);

    public ResponseEntity<List<Object>> getTaskByUserId(int user_id, String token);

    // Update
    public ResponseEntity<Object> updateTask(int task_id, TaskModel task, String token);

    // Delete
    public ResponseEntity<Object> deleteTask(int task_id, String token);



}
