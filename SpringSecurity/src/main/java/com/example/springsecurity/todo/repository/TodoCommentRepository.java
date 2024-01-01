package com.example.springsecurity.todo.repository;

import com.example.springsecurity.todo.entity.TodoCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoCommentRepository extends JpaRepository<TodoCommentEntity,Long> {

}
