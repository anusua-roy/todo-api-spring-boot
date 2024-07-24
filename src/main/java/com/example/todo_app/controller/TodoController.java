package com.example.todo_app.controller;

import com.example.todo_app.model.Todo;
import com.example.todo_app.repository.TodoRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public List<Todo> getAllTodos() {
        logger.info("Fetching all todos");
        return todoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        logger.info("Fetching todo with id {}", id);
        return todoRepository.findById(id)
                .map(todo -> ResponseEntity.ok().body(todo))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createTodo(
            @Valid @RequestBody Todo todo,
            BindingResult result
    ) {
        logger.info("Creating new todo");
        if (result.hasErrors()) {
            String errors = result.getFieldErrors().stream()
                    .map(e -> e.getField() + " " + e.getDefaultMessage())
                    .collect(Collectors.joining(","));
            logger.error("Validation errors on Creating: {}", errors);
            return ResponseEntity.badRequest().body(errors);
        }
        Todo createdTodo = todoRepository.save(todo);
        return ResponseEntity.ok(createdTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody Todo todoDetails,
            BindingResult result
    ) {
        logger.info("Updating todo with id {}", id);
        if (result.hasErrors()) {
            String errors = result.getFieldErrors().stream()
                    .map(e -> e.getField() + " " + e.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            logger.error("Validation errors on Updating: {}", errors);
            return ResponseEntity.badRequest().body(errors);
        }
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setTitle(todoDetails.getTitle());
                    todo.setDescription(todoDetails.getDescription());
                    todo.setCompleted(todoDetails.isCompleted());
                    todo.setPriority(todoDetails.getPriority());
                    todo.setDueDate(todoDetails.getDueDate());
                    Todo updatedTodo = todoRepository.save(todo);
                    return ResponseEntity.ok().body(updatedTodo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        logger.info("Deleting todo with id {}", id);
        return todoRepository.findById(id)
                .map(todo -> {
                    todoRepository.delete(todo);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filter")
    public List<Todo> getTododsByPriorityAndDueDate(
            @RequestParam(required = false) Integer priority,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate
    ) {
        if (priority != null && dueDate != null) {
            logger.info("Fetching todos with priority {} and due date {}", priority, dueDate);
            return todoRepository.findByPriorityAndDueDate(priority, dueDate);
        } else if (priority != null) {
            logger.info("Fetching todos with priority {}", priority);
            return todoRepository.findByPriority(priority);
        } else if (dueDate != null) {
            logger.info("Fetching todos with due date {}", dueDate);
            return todoRepository.findByDueDate(dueDate);
        } else {
            logger.info("Fetching all todos with no filter");
            return todoRepository.findAll();
        }
    }
}
