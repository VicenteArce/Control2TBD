package com.tbd.taskmanager.repositories;

import com.tbd.taskmanager.models.TaskModel;
import com.tbd.taskmanager.utils.InputVerificationService;
import com.tbd.taskmanager.utils.JwtMiddlewareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class TaskRepositoryImp implements TaskRepository {

    @Autowired
    private Sql2o sql2o;

    @Autowired
    private JwtMiddlewareService jwtMiddlewareService;

    @Autowired
    private InputVerificationService inputVerificationService;

    // Create
    @Override
    public ResponseEntity<Object> createTask(TaskModel task, String token) {
        if (jwtMiddlewareService.validateToken(token)) {
            if (!InputVerificationService.validateInput(task.getTitle()) || !InputVerificationService.validateInput(task.getDescription()) || !InputVerificationService.validateInput(task.getState())) {
                return ResponseEntity.badRequest().body("Error al crear la tarea, los datos contienen caracteres no permitidos");
            }
            try (Connection connection = sql2o.open()) {
                connection.createQuery("INSERT INTO task (user_id, title, description, expiration_date, state) VALUES (:user_id, :title, :description, :expiration_date, :state)")
                        .addParameter("user_id", task.getUser_id())
                        .addParameter("title", task.getTitle())
                        .addParameter("description", task.getDescription())
                        .addParameter("expiration_date", task.getExpiration_date())
                        .addParameter("state", task.getState())
                        .executeUpdate();
                return ResponseEntity.ok(task);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseEntity.badRequest().body("Error al crear la tarea");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No se encuentra autorizado");
    }

    // Read
    @Override
    public ResponseEntity<Object> getTask(int task_id, String token) {
        if (jwtMiddlewareService.validateToken(token)) {
            try (Connection connection = sql2o.open()) {
                TaskModel task = connection.createQuery("SELECT * FROM task WHERE task_id = :task_id")
                        .addParameter("task_id", task_id)
                        .executeAndFetchFirst(TaskModel.class);
                if (task != null) {
                    return ResponseEntity.ok(task);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                e.printStackTrace(); // Agregado para imprimir la pila de errores en la consola.
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // Asegúrate de retornar la respuesta aquí.
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No se encuentra autorizado");
    }

    @Override
    public ResponseEntity<List<Object>> getAllTasks(String token) {

        if (!jwtMiddlewareService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    List.of("No se encuentra autorizado")
            );
        }
        try (Connection connection = sql2o.open()) {
            List<TaskModel> tasks = connection.createQuery("SELECT * FROM task")
                    .executeAndFetch(TaskModel.class);
            List<Object> result = (List) tasks;
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    List.of(e.getMessage())
            );
        }
    }
    @Override
    public ResponseEntity<List<Object>> getTasksByState(String state, String token) {
        if (!jwtMiddlewareService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    List.of("No se encuentra autorizado")
            );
        }
        if (!InputVerificationService.validateInput(state)) {
            return ResponseEntity.badRequest().body(
                    List.of("Error al buscar las tareas, caracteres no permitidos en el estado")
            );
        }
        try (Connection connection = sql2o.open()) {
            List<TaskModel> tasks = connection.createQuery("SELECT * FROM task WHERE state = :state")
                    .addParameter("state", state)
                    .executeAndFetch(TaskModel.class);
            List<Object> result = (List) tasks;
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    List.of(e.getMessage())
            );
        }
    }

    @Override
    public ResponseEntity<List<Object>> getTasksByWord(String word, String token) {
        if (!jwtMiddlewareService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    List.of("No se encuentra autorizado")
            );
        }
        try (Connection connection = sql2o.open()) {
            List<TaskModel> tasks = connection.createQuery("SELECT * FROM task WHERE LOWER(title) LIKE LOWER(:word) OR LOWER(description) LIKE LOWER(:word)")
                    .addParameter("word", "%" + word + "%")
                    .executeAndFetch(TaskModel.class);
            List<Object> result = (List) tasks;
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    List.of(e.getMessage())
            );
        }
    }

    @Override
    public ResponseEntity<List<Object>> getTasksByDateExpire(String token) {
        if (!jwtMiddlewareService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    List.of("No autorizado")
            );
        }
        try (Connection connection = sql2o.open()) {
            List<TaskModel> tasks = connection.createQuery("SELECT * FROM task WHERE DATE(date_of_expire) BETWEEN DATE(CURRENT_DATE) AND DATE(CURRENT_DATE + INTERVAL '2 day')")
                    .executeAndFetch(TaskModel.class);
            List<Object> result = (List) tasks;
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    List.of(e.getMessage())
            );
        }
    }

    @Override
    public ResponseEntity<List<Object>> getTaskByUserId(int user_id, String token) {
        if (!jwtMiddlewareService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    List.of("No autorizado")
            );
        }
        try (Connection connection = sql2o.open()) {
            List<TaskModel> tasks = connection.createQuery("SELECT * FROM task WHERE user_id = :user_id")
                    .addParameter("user_id", user_id)
                    .executeAndFetch(TaskModel.class);
            List<Object> result = (List) tasks;
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    List.of(e.getMessage())
            );
        }
    }

    // Update
    @Override
    public ResponseEntity<Object> updateTask(int task_id, TaskModel task, String token) {
        if (!InputVerificationService.validateInput(task.getTitle()) || !InputVerificationService.validateInput(task.getDescription()) || !InputVerificationService.validateInput(task.getState())) {
            return ResponseEntity.badRequest().body("Error al crear la tarea, caracteres no permitidos en el título, descripción o estado.");
        }
        if(jwtMiddlewareService.validateToken(token)){
            try(Connection connection = sql2o.open()){
                connection.getJdbcConnection().setAutoCommit(false);
                connection.createQuery("UPDATE task SET user_id = :user_id, title = :title, description = :description, date_of_expire = :expiration_date, state = :state WHERE task_id = :task_id")
                        .addParameter("user_id", task.getUser_id())
                        .addParameter("title", task.getTitle())
                        .addParameter("description", task.getDescription())
                        .addParameter("expiration_date", task.getExpiration_date())
                        .addParameter("state", task.getState())
                        .addParameter("task_id", task_id)
                        .executeUpdate();
                connection.commit();
                return ResponseEntity.ok(task);
            }catch(Exception e){
                try (Connection connection = sql2o.open()) {
                    connection.rollback();
                } catch (Exception rollbackException) {
                    e.addSuppressed(rollbackException);
                }
                return ResponseEntity.status(500).body("Error al actualizar la tarea: " + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No se encuentra autorizado");
    }

    // Delete
    @Override
    public ResponseEntity<Object> deleteTask(int task_id, String token) {
        if(jwtMiddlewareService.validateToken(token)){
            try(Connection connection = sql2o.open()){
                connection.createQuery("DELETE FROM task WHERE task_id = :task_id")
                        .addParameter("task_id", task_id)
                        .executeUpdate();
                return ResponseEntity.ok().build();
            }catch(Exception e){
                return ResponseEntity.status(500).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No se encuentra autorizado");
    }





}
