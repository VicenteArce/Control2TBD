package com.tbd.taskmanager.repositories;

import com.tbd.taskmanager.models.UsersModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsersRepository {
    // CRUD
    // Create
    public ResponseEntity<Object> createUser(UsersModel user);

    // Read
    public ResponseEntity<Object> getUser(int user_id);

    // Update
    public ResponseEntity<Object> updateUser(UsersModel user);

    // Delete
    public ResponseEntity<Object> deleteUser(int user_id);

    // Other methods
    public ResponseEntity<Object> getUserByEmail(String email);

    public ResponseEntity<Object> loginUser(String email, String password);

    public ResponseEntity<Object> getUserByName(String name);

    public ResponseEntity<List<Object>> getAllUsers();


}
