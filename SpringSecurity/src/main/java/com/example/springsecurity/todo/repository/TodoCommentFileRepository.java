package com.example.springsecurity.todo.repository;

import com.example.springsecurity.todo.entity.TodoCommentEntity;
import com.example.springsecurity.todo.entity.TodoCommentFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoCommentFileRepository extends JpaRepository<TodoCommentFileEntity,Long> {

    TodoCommentFileEntity findByStoredFileName(String fileName);

    TodoCommentFileEntity findByTodoCommentEntity(TodoCommentEntity todoCommentEntity);

}
