package com.tbd.taskmanager.services;


import com.tbd.taskmanager.models.TaskModel;
import com.tbd.taskmanager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    // Create
    public ResponseEntity<Object> createTask(TaskModel task, String token){
        return taskRepository.createTask(task, token);
    }

    // Read
    public ResponseEntity<Object> getTask(int task_id, String token){
        return taskRepository.getTask(task_id, token);
    }

    public ResponseEntity<List<Object>> getAllTasks(String token){
        return taskRepository.getAllTasks(token);
    }

    public ResponseEntity<List<Object>> getTasksByState(String state, String token){
        return taskRepository.getTasksByState(state, token);
    }

    public ResponseEntity<List<Object>> getTasksByWord(String word, String token){
        return taskRepository.getTasksByWord(word, token);
    }

    public ResponseEntity<List<Object>> getTasksByDateExpire(String token){
        return taskRepository.getTasksByDateExpire(token);
    }

    public ResponseEntity<List<Object>> getTaskByUserId(int user_id, String token){
        return taskRepository.getTaskByUserId(user_id, token);
    }

    // Update
    public ResponseEntity<Object> updateTask(int task_id, TaskModel task, String token){
        return taskRepository.updateTask(task_id, task, token);
    }

    public ResponseEntity<Object> completeTask(int task_id, String token){
        return taskRepository.completeTask(task_id, token);
    }

    // Delete
    public ResponseEntity<Object> deleteTask(int task_id, String token){
        return taskRepository.deleteTask(task_id, token);
    }


}
