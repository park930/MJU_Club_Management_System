package com.example.springsecurity.todo.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.repository.ClubRepository;
import com.example.springsecurity.todo.dto.TodoCommentDTO;
import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.entity.TodoCommentEntity;
import com.example.springsecurity.todo.entity.TodoEntity;
import com.example.springsecurity.todo.repository.TodoCommentRepository;
import com.example.springsecurity.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoCommentService {
    private final TodoCommentRepository todoCommentRepository;
    private final TodoRepository todoRepository;
    private final ClubRepository clubRepository;


    public TodoCommentDTO save(TodoCommentDTO todoCommentDTO, TodoDTO todoDTO, ClubDTO clubDTO) {
        TodoCommentEntity todoCommentEntity = TodoCommentEntity.toTodoCommentEntity(todoCommentDTO,TodoEntity.toUpdateTodoEntity(todoDTO), ClubEntity.toUpdateClub(clubDTO));
        TodoCommentEntity saved = todoCommentRepository.save(todoCommentEntity);
        return TodoCommentDTO.toTodoCommentDTO(saved);
    }

    public List<TodoCommentDTO> findAll(TodoDTO todoDTO, ClubDTO clubDTO) {
        List<TodoCommentEntity> commentEntityList = todoCommentRepository.findAllByTodoEntityAndClubEntityOrderByCreatedTimeDesc(TodoEntity.toUpdateTodoEntity(todoDTO), ClubEntity.toUpdateClub(clubDTO));
        List<TodoCommentDTO> todoCommentDTOList = new ArrayList<>();
        for (TodoCommentEntity todoCommentEntity : commentEntityList){
            todoCommentDTOList.add(TodoCommentDTO.toTodoCommentDTO(todoCommentEntity));
        }
        return todoCommentDTOList;
    }

    public TodoCommentDTO findById(Long commentId) {
        Optional<TodoCommentEntity> optionalTodoCommentEntity = todoCommentRepository.findById(commentId);
        if (optionalTodoCommentEntity.isPresent()){
            return  TodoCommentDTO.toTodoCommentDTO(optionalTodoCommentEntity.get());
        } else {
            return null;
        }
    }

    public TodoCommentDTO update(TodoCommentDTO commentDTO) {
        Optional<TodoEntity> optionalTodoEntity = todoRepository.findById(commentDTO.getTodoId());
        Optional<ClubEntity> optionalClubEntity = clubRepository.findById(commentDTO.getClubId());
        if (optionalTodoEntity.isPresent() && optionalClubEntity.isPresent()){
            TodoCommentEntity todoCommentEntity = TodoCommentEntity.toUpdateTodoCommentEntity(commentDTO,optionalTodoEntity.get(),optionalClubEntity.get());
            TodoCommentEntity saved = todoCommentRepository.save(todoCommentEntity);
            return TodoCommentDTO.toTodoCommentDTO(saved);
        }
        return null;
    }
}
