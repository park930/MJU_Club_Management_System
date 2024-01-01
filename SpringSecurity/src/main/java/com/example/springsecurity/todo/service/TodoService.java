package com.example.springsecurity.todo.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.repository.ClubRepository;
import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.entity.TodoClubEntity;
import com.example.springsecurity.todo.entity.TodoEntity;
import com.example.springsecurity.todo.repository.TodoClubRepository;
import com.example.springsecurity.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
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

    public List<ClubDTO> getClubList(Long id) {
        Optional<TodoEntity> optionalTodoEntity = todoRepository.findById(id);
        if (optionalTodoEntity.isPresent()){
            List<TodoClubEntity> todoEntityList = todoClubRepository.findAllByTodoEntity(optionalTodoEntity.get());
            List<ClubDTO> clubDTOList = new ArrayList<>();

            for (TodoClubEntity todoClubEntity : todoEntityList) {
                Optional<ClubEntity> optionalClubEntity = clubRepository.findById(todoClubEntity.getClubEntity().getId());
                if (optionalClubEntity.isPresent()){
                    clubDTOList.add(ClubDTO.toClubDTO(optionalClubEntity.get()));
                }
            }
            return clubDTOList;
        } else {
            return null;
        }
    }

    public List<TodoDTO> findAllByWriter(String userId) {
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
}
