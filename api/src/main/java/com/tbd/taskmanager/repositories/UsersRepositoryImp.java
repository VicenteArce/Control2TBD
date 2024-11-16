package com.tbd.taskmanager.repositories;

import com.tbd.taskmanager.models.UsersModel;
import com.tbd.taskmanager.utils.InputVerificationService;
import com.tbd.taskmanager.utils.JwtMiddlewareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

@Repository
public class UsersRepositoryImp implements UsersRepository {
    @Autowired
    private Sql2o sql2o;

    @Autowired
    private JwtMiddlewareService jwtMiddlewareService;

    @Autowired
    private InputVerificationService inputVerificationService;


    @Override
    public ResponseEntity<Object> createUser(UsersModel user){
        try(Connection connection = sql2o.open()){
            if (!InputVerificationService.validateInput(user.getName()) || !InputVerificationService.validateInput(user.getEmail()) ||
                    !InputVerificationService.validateInput(user.getPassword())) {
                return ResponseEntity.badRequest().body("Error al crear el usuario, caracteres no permitidos en el nombre, email o contraseña");
            }

            Integer count = connection.createQuery("SELECT COUNT(*) FROM users WHERE email = :email OR name = :name")
                    .addParameter("email", user.getEmail())
                    .addParameter("name", user.getName())
                    .executeScalar(Integer.class);

            if(count != null && count > 0){
                return ResponseEntity.status(409).body("Ya existe un usuario con mismo nombre o email.");
            }

            //si no existe, proceder con la creacion del usuario
            Integer userId = (Integer) connection.createQuery("INSERT INTO users (name, email, password) VALUES (:name, :email, :password)", true)
                    .addParameter("name", user.getName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword())
                    .executeUpdate().getKey();

            user.setUser_id(userId);
            String token = jwtMiddlewareService.generateToken(user);
            return ResponseEntity.ok(token);

        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    @Override
    public ResponseEntity<Object> getUser(int user_id) {
        try(Connection connection = sql2o.open()){
            UsersModel user = connection.createQuery("SELECT * FROM users WHERE user_id = :user_id")
                    .addParameter("user_id", user_id)
                    .executeAndFetchFirst(UsersModel.class);
            if(user != null){
                return ResponseEntity.ok(user);
            }else{
                return ResponseEntity.notFound().build();
            }
        }
    }

    @Override
    public ResponseEntity<Object> getUserByEmail(String email) {
        if (!InputVerificationService.validateInput(email)) {
            return ResponseEntity.badRequest().body("Error al crear el usuario, caracteres no permitidos en el email.");
        }
        try(Connection connection = sql2o.open()){
            UsersModel user = connection.createQuery("SELECT * FROM users WHERE email = :email")
                    .addParameter("email", email)
                    .executeAndFetchFirst(UsersModel.class);
            if(user != null){
                return ResponseEntity.ok(user);
            }else{
                return ResponseEntity.notFound().build();
            }
        }
    }
    @Override
    public ResponseEntity<Object> getUserByName(String name) {
        if (!InputVerificationService.validateInput(name)) {
            return ResponseEntity.badRequest().body("Error al obtener usuarios, el nombre contiene caracteres no permitidos");
        }
        try(Connection connection = sql2o.open()){
            UsersModel user = connection.createQuery("SELECT * FROM users WHERE name = :name")
                    .addParameter("name", name)
                    .executeAndFetchFirst(UsersModel.class);
            if(user != null){
                return ResponseEntity.ok(user);
            }else{
                return ResponseEntity.notFound().build();
            }
        }
    }




    @Override
    public ResponseEntity<Object> updateUser(UsersModel user) {
        if (!InputVerificationService.validateInput(user.getName()) || !InputVerificationService.validateInput(user.getEmail()) ||
                !InputVerificationService.validateInput(user.getPassword())) {
            return ResponseEntity.badRequest().body("Error al actualizar el usuario, caracteres no permitidos en el nombre, email o contraseña");
        }
        try (Connection connection = sql2o.open()) {
            connection.createQuery("UPDATE users SET name = :name, email = :email, password = :password WHERE user_id = :user_id")
                    .addParameter("name", user.getName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword())
                    .addParameter("user_id", user.getUser_id())
                    .executeUpdate();
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> deleteUser(int user_id) {
        try (Connection connection = sql2o.open()) {
            connection.createQuery("DELETE FROM users WHERE user_id = :user_id")
                    .addParameter("user_id", user_id)
                    .executeUpdate();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> loginUser(String name, String password) {
        if (!InputVerificationService.validateInput(name) || !InputVerificationService.validateInput(password)) {
            return ResponseEntity.badRequest().body("Error al iniciar sesion , caracteres no permitidos en el nombre o contraseña");
        }
        try {
            UsersModel user = (UsersModel) getUserByName(name).getBody();
            if (user == null) {
                // Usuario no encontrado
                return ResponseEntity.status(401).body("Usuario no encontrado");
            }
            if (user.getPassword().compareTo(password) == 0) {
                String token = jwtMiddlewareService.generateToken(user);
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
