package com.example.springsecurity.todo.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.todo.dto.TodoCommentDTO;
import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.entity.TodoCommentEntity;
import com.example.springsecurity.todo.entity.TodoEntity;
import com.example.springsecurity.todo.repository.TodoCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoCommentService {
    private final TodoCommentRepository todoCommentRepository;


    public Long save(TodoCommentDTO todoCommentDTO, TodoDTO todoDTO, ClubDTO clubDTO) {
        TodoCommentEntity todoCommentEntity = TodoCommentEntity.toTodoCommentEntity(todoCommentDTO,TodoEntity.toUpdateTodoEntity(todoDTO), ClubEntity.toUpdateClub(clubDTO));
        return todoCommentRepository.save(todoCommentEntity).getId();
    }

    public List<TodoCommentDTO> findAll(TodoDTO todoDTO, ClubDTO clubDTO) {
        List<TodoCommentEntity> commentEntityList = todoCommentRepository.findAllByTodoEntityAndClubEntityOrderByCreatedTimeDesc(TodoEntity.toUpdateTodoEntity(todoDTO), ClubEntity.toUpdateClub(clubDTO));
        List<TodoCommentDTO> todoCommentDTOList = new ArrayList<>();
        for (TodoCommentEntity todoCommentEntity : commentEntityList){
            todoCommentDTOList.add(TodoCommentDTO.toTodoCommentDTO(todoCommentEntity));
        }
        return todoCommentDTOList;
    }
}
