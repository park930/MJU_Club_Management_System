package com.example.springsecurity.todo.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.repository.ClubRepository;
import com.example.springsecurity.todo.dto.TodoCommentDTO;
import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.entity.TodoClubEntity;
import com.example.springsecurity.todo.entity.TodoCommentEntity;
import com.example.springsecurity.todo.entity.TodoEntity;
import com.example.springsecurity.todo.repository.TodoClubRepository;
import com.example.springsecurity.todo.repository.TodoCommentRepository;
import com.example.springsecurity.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final TodoClubRepository todoClubRepository;
    private final ClubRepository clubRepository;
    private final TodoCommentRepository todoCommentRepository;

    public List<TodoDTO> findAll() {
        List<TodoEntity> todoEntityList = todoRepository.findAll();
        List<TodoDTO> todoDTOList = new ArrayList<>();
        for(TodoEntity todoEntity : todoEntityList){
            TodoDTO todoDTO = TodoDTO.toTodoDTO(todoEntity);
            todoDTOList.add(todoDTO);
        }
        return todoDTOList;
    }

    public TodoEntity saveTodo(TodoDTO todoDTO) {
        TodoEntity todoEntity = TodoEntity.toTodoEntity(todoDTO);
        TodoEntity saved = todoRepository.save(todoEntity);
        System.out.println(" 저장한 entity = " + saved);
        return saved;
    }


    public void saveTodoClub(ClubEntity clubEntity, TodoEntity savedTodo) {
        todoClubRepository.save(TodoClubEntity.newTodoClub(clubEntity,savedTodo));
    }

    public TodoDTO findById(Long id) {
        Optional<TodoEntity> optionalTodoEntity = todoRepository.findById(id);
        if (optionalTodoEntity.isPresent()){
            TodoEntity todoEntity = optionalTodoEntity.get();
            return TodoDTO.toTodoDTO(todoEntity);
        } else {
            return null;
        }
    }

    public Long flipChecked(TodoDTO todoDTO) {
        TodoEntity todoEntity = TodoEntity.toFlipChecked(todoDTO);
        return todoRepository.save(todoEntity).getId();
    }


    public List<TodoDTO> findAllByUserWriter(String userId) {
        List<TodoEntity> todoEntityList = todoRepository.findAllByWriterOrderByEndTimeDesc(userId);
        List<TodoDTO> todoDTOList = new ArrayList<>();
        for(TodoEntity todoEntity : todoEntityList){
            TodoDTO todoDTO = TodoDTO.toTodoDTO(todoEntity);
            todoDTOList.add(todoDTO);
        }
        return todoDTOList;
    }

    public List<TodoDTO> findAllByClubEntity(ClubEntity clubEntity) {
        List<TodoClubEntity> todoClubList = todoClubRepository.findAllByClubEntity(clubEntity);
        List<TodoDTO> todoDTOList = new ArrayList<>();

        for(TodoClubEntity todoClubEntity : todoClubList){
            TodoEntity todoEntity = todoClubEntity.getTodoEntity();
            todoDTOList.add(TodoDTO.toTodoDTO(todoEntity));
        }
        return todoDTOList;
    }

    public List<TodoCommentDTO> filterCompleteClub(TodoDTO todoDTO) {
        if (todoDTO != null){
            List<TodoCommentEntity> todoCommentEntityList = todoCommentRepository.findAllByTodoEntityAndTypeOrderByCreatedTimeDesc(TodoEntity.toUpdateTodoEntity(todoDTO), "result");
            List<TodoCommentDTO> todoCommentDTOList = new ArrayList<>();
            for(TodoCommentEntity todoCommentEntity : todoCommentEntityList){
                TodoCommentDTO todoCommentDTO = TodoCommentDTO.toTodoCommentDTO(todoCommentEntity);
                Optional<ClubEntity> optionalClubEntity = clubRepository.findById(todoCommentDTO.getClubId());
                if (optionalClubEntity.isPresent()) {
                    todoCommentDTO.setClubName(optionalClubEntity.get().getClubName());
                }
                todoCommentDTOList.add(todoCommentDTO);
            }
            return todoCommentDTOList;
        } else {
            return null;
        }
    }

    public List<ClubDTO> getImcompleteClubList(TodoDTO todoDTO,List<TodoCommentDTO> completeCommentList) {
        if (todoDTO != null){
            List<TodoClubEntity> todoEntityList = todoClubRepository.findAllByTodoEntity(TodoEntity.toUpdateTodoEntity(todoDTO));
            List<ClubDTO> clubDTOList = new ArrayList<>();
            List<Long> completeId = new ArrayList<>();
            for(TodoCommentDTO todoCommentDTO : completeCommentList){
                completeId.add(todoCommentDTO.getClubId());
            }

            for (TodoClubEntity todoClubEntity : todoEntityList) {
                Optional<ClubEntity> optionalClubEntity = clubRepository.findById(todoClubEntity.getClubEntity().getId());
                if (optionalClubEntity.isPresent()){
                    ClubEntity clubEntity = optionalClubEntity.get();
                    if (!completeId.contains(clubEntity.getId())) {
                        clubDTOList.add(ClubDTO.toClubDTO(clubEntity));
                    }
                }
            }
            return clubDTOList;
        } else {
            return null;
        }
    }

    public List<TodoDTO> findAllByAdminWriter() {
        List<TodoEntity> todoEntityList = todoRepository.findAllByWriterStartingWithOrderByEndTimeDesc("admin");
        List<TodoDTO> todoDTOList = new ArrayList<>();
        for( TodoEntity todoEntity : todoEntityList){
            todoDTOList.add(TodoDTO.toTodoDTO(todoEntity));
        }
        return todoDTOList;
    }

    public List<TodoDTO> filterReceivedTodo(List<TodoDTO> totalTodoList) {
        List<TodoDTO> recivedTodoList = new ArrayList<>();
        for(TodoDTO todoDTO : totalTodoList){
            if (todoDTO.getWriter().startsWith("admin")){
                recivedTodoList.add(todoDTO);
            }
        }
        return recivedTodoList;
    }

    public List<TodoDTO> filterMyTodo(List<TodoDTO> totalTodoList) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TodoDTO> myTodoList = new ArrayList<>();
        for(TodoDTO todoDTO : totalTodoList){
            if (todoDTO.getWriter().equals(id)){
                myTodoList.add(todoDTO);
            }
        }
        return myTodoList;
    }
}
