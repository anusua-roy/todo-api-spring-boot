package com.example.todo_app.repository;

import com.example.todo_app.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByPriority(int priority);
    List<Todo> findByDueDate(LocalDate dueDate);
    List<Todo> findByPriorityAndDueDate(int priority, LocalDate dueDate);
}
