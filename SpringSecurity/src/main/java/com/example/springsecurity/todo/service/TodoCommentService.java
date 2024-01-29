package com.example.springsecurity.todo.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.repository.ClubRepository;
import com.example.springsecurity.todo.dto.TodoCommentDTO;
import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.entity.TodoCommentEntity;
import com.example.springsecurity.todo.entity.TodoCommentFileEntity;
import com.example.springsecurity.todo.entity.TodoEntity;
import com.example.springsecurity.todo.repository.TodoCommentFileRepository;
import com.example.springsecurity.todo.repository.TodoCommentRepository;
import com.example.springsecurity.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoCommentService {
    private final TodoCommentRepository todoCommentRepository;
    private final TodoRepository todoRepository;
    private final TodoCommentFileRepository todoCommentFileRepository;
    private final ClubRepository clubRepository;


    @Transactional
    public LocalDateTime save(TodoCommentDTO todoCommentDTO, TodoDTO todoDTO, ClubDTO clubDTO) throws IOException {

        TodoCommentEntity saved = null;
        if (todoCommentDTO.getResultFile().isEmpty()){
            TodoCommentEntity todoCommentEntity = TodoCommentEntity.toSaveTodoCommentEntity(todoCommentDTO,TodoEntity.toUpdateTodoEntity(todoDTO), ClubEntity.toUpdateClub(clubDTO));
            saved = todoCommentRepository.save(todoCommentEntity);
        } else {
            MultipartFile resultFile = todoCommentDTO.getResultFile();
            String originalFilename = resultFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/Users/joohyun/Desktop/login_project/login_project/SpringSecurity/src/main/resources/static/fileFolder/" + storedFileName;
            resultFile.transferTo(new File(savePath));
            TodoCommentEntity todoCommentEntity = TodoCommentEntity.toSaveFileTodoCommentEntity(todoCommentDTO,TodoEntity.toUpdateTodoEntity(todoDTO), ClubEntity.toUpdateClub(clubDTO));
            Long id = todoCommentRepository.save(todoCommentEntity).getId();
            saved = todoCommentRepository.findById(id).get();

            TodoCommentFileEntity todoCommentFileEntity = TodoCommentFileEntity.toTodoCommentFileEntity(saved, originalFilename, storedFileName);
            todoCommentFileRepository.save(todoCommentFileEntity);
        }

        return saved.getCreatedTime();
    }

    @Transactional
    public List<TodoCommentDTO> findAll(TodoDTO todoDTO, ClubDTO clubDTO) {
        List<TodoCommentEntity> commentEntityList = todoCommentRepository.findAllByTodoEntityAndClubEntityOrderByCreatedTimeDesc(TodoEntity.toUpdateTodoEntity(todoDTO), ClubEntity.toUpdateClub(clubDTO));
        List<TodoCommentDTO> todoCommentDTOList = new ArrayList<>();
        for (TodoCommentEntity todoCommentEntity : commentEntityList){
            TodoCommentDTO todoCommentDTO = TodoCommentDTO.toTodoCommentDTO(todoCommentEntity);
            todoCommentDTOList.add(todoCommentDTO);
        }
        return todoCommentDTOList;
    }

    @Transactional
    public TodoCommentDTO findById(Long commentId) {
        Optional<TodoCommentEntity> optionalTodoCommentEntity = todoCommentRepository.findById(commentId);
        if (optionalTodoCommentEntity.isPresent()){
            return  TodoCommentDTO.toTodoCommentDTO(optionalTodoCommentEntity.get());
        } else {
            return null;
        }
    }

    @Transactional
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
