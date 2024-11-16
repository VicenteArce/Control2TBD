package com.tbd.taskmanager.services;

import com.tbd.taskmanager.models.UsersModel;
import com.tbd.taskmanager.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;

    public ResponseEntity<Object> createUser(UsersModel user){
        return usersRepository.createUser(user);
    }

    // Read
    public ResponseEntity<Object> getUser(int user_id){
        return usersRepository.getUser(user_id);
    }

    // Update
    public ResponseEntity<Object> updateUser(UsersModel user){
        return usersRepository.updateUser(user);
    }

    // Delete
    public ResponseEntity<Object> deleteUser(int user_id){
        return usersRepository.deleteUser(user_id);
    }

    // Other methods
    public ResponseEntity<Object> getUserByEmail(String email){
        return usersRepository.getUserByEmail(email);
    }

    public ResponseEntity<Object> loginUser(String email, String password){
        return usersRepository.loginUser(email, password);
    }

    public ResponseEntity<Object> getUserByName(String name){
        return usersRepository.getUserByName(name);
    }

    public ResponseEntity<List<Object>> getUsers(){
        return usersRepository.getAllUsers();
    }

}
