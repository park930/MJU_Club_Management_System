package com.example.springsecurity.todo.repository;

import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.todo.entity.TodoClubEntity;
import com.example.springsecurity.todo.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoClubRepository extends JpaRepository<TodoClubEntity,Long> {

    List<TodoClubEntity> findAllByTodoEntity (TodoEntity todoEntity);

    TodoClubEntity findByClubEntityAndTodoEntity (ClubEntity clubEntity, TodoEntity todoEntity);

    List<TodoClubEntity> findAllByClubEntity(ClubEntity clubEntity);

}
