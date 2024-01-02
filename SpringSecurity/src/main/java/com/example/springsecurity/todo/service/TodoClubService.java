package com.example.springsecurity.todo.service;

import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.todo.entity.TodoClubEntity;
import com.example.springsecurity.todo.entity.TodoEntity;
import com.example.springsecurity.todo.repository.TodoClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoClubService {
    private final TodoClubRepository todoClubRepository;

    public void updateTodoClub(TodoEntity todoEntity, ClubEntity clubEntity) {
        TodoClubEntity todoClubEntity = todoClubRepository.findByClubEntityAndTodoEntity(clubEntity, todoEntity);
        todoClubEntity.setResultSubmit(1);
        todoClubRepository.save(todoClubEntity);
    }
}
