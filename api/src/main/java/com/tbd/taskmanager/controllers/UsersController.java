package com.tbd.taskmanager.controllers;

import com.tbd.taskmanager.models.UsersModel;
import com.tbd.taskmanager.services.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController("*")
@RequestMapping("/api")
public class UsersController {

    private final UsersService userService;

    public UsersController(UsersService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/register")
    public ResponseEntity<Object> createUser(@RequestBody UsersModel user) {
        return userService.createUser(user);
    }

    @PostMapping("/user/login")
    public ResponseEntity<Object> loginUser(@RequestBody UsersModel user) {
        return userService.loginUser(user.getName(), user.getPassword());
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<Object> getUser(@PathVariable int user_id) {
        return userService.getUser(user_id);
    }

    @DeleteMapping("/user/{user_id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int user_id) {
        return userService.deleteUser(user_id);
    }

    @PutMapping("/user")
    public ResponseEntity<Object> updateUser(@RequestBody UsersModel user) {
        return userService.updateUser(user);
    }

    @GetMapping("/user/name/{name}")
    public ResponseEntity<Object> getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Object>> getUsers() {
        return userService.getUsers();
    }


}