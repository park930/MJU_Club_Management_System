package com.example.springsecurity.todo.dto;

import com.example.springsecurity.todo.entity.TodoEntity;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {

    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean check;
    private LocalDateTime createdTime;

    public static TodoDTO toTodoDTO(TodoEntity todoEntity) {
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setWriter(todoEntity.getWriter());
        todoDTO.setId(todoEntity.getId());
        todoDTO.setTitle(todoEntity.getTitle());
        todoDTO.setContent(todoEntity.getContent());
        todoDTO.setStartTime(todoEntity.getStartTime());
        todoDTO.setEndTime(todoEntity.getEndTime());
        if (todoEntity.getTodoCheck() == 0){
            todoDTO.setCheck(false);
        } else {
            todoDTO.setCheck(true);
        }
        todoDTO.setCreatedTime(todoEntity.getCreatedTime());
        return todoDTO;
    }
}
