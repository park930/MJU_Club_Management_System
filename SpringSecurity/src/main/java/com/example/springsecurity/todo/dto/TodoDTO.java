package com.example.springsecurity.todo.dto;

import com.example.springsecurity.todo.entity.TodoEntity;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean check;
    private LocalDateTime createdTime;

    public static TodoDTO toTodoDTO(TodoEntity todoEntity) {
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setId(todoEntity.getId());
        todoDTO.setTitle(todoEntity.getTitle());
        todoDTO.setContent(todoEntity.getContent());
        todoDTO.setStartTime(todoEntity.getStartTime());
        todoDTO.setEndTime(todoEntity.getEndTime());
        todoDTO.setCheck(todoEntity.isCheck());
        todoDTO.setCreatedTime(todoEntity.getCreatedTime());
        return todoDTO;
    }
}
