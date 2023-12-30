package com.example.springsecurity.todo.service;

import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.entity.TodoEntity;
import com.example.springsecurity.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public List<TodoDTO> findAll() {
        List<TodoEntity> todoEntityList = todoRepository.findAll();
        List<TodoDTO> todoDTOList = new ArrayList<>();
        for(TodoEntity todoEntity : todoEntityList){
            TodoDTO todoDTO = TodoDTO.toTodoDTO(todoEntity);
            todoDTOList.add(todoDTO);
        }
        return todoDTOList;
    }


//    public void save(TodoDTO clubDTO) {
//        TodoEntity clubEntity = TodoEntity.toClubEntity(clubDTO);
//        clubRepository.save(clubEntity);
//    }

}
