package com.example.springsecurity.todo.repository;

import com.example.springsecurity.todo.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

    List<TodoEntity> findAllByWriterOrderByEndTimeDesc (String writer);

}
