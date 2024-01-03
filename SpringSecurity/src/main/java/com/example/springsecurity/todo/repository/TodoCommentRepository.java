package com.example.springsecurity.todo.repository;

import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.todo.entity.TodoCommentEntity;
import com.example.springsecurity.todo.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoCommentRepository extends JpaRepository<TodoCommentEntity,Long> {

    List<TodoCommentEntity> findAllByTodoEntityAndClubEntityOrderByCreatedTimeDesc (TodoEntity todoEntity, ClubEntity clubEntity);

    List<TodoCommentEntity> findAllByTodoEntityAndTypeOrderByCreatedTimeDesc (TodoEntity todoEntity,String type);

    TodoCommentEntity findByTodoEntityAndClubEntityAndType (TodoEntity todoEntity, ClubEntity clubEntity,String type);


}
