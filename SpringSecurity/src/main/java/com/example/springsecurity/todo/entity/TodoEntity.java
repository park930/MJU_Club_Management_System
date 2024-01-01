package com.example.springsecurity.todo.entity;

import com.example.springsecurity.board.entity.BaseEntity;
import com.example.springsecurity.board.entity.BoardEntity;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.todo.dto.TodoDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "todo_table")
@ToString
public class TodoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String writer;


    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private int todoCheck;

    public static TodoEntity toTodoEntity(TodoDTO todoDTO) {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setWriter(todoDTO.getWriter());
        todoEntity.setTitle(todoDTO.getTitle());
        todoEntity.setContent(todoDTO.getContent());
        todoEntity.setStartTime(todoDTO.getStartTime());
        todoEntity.setEndTime(todoDTO.getEndTime());
        todoEntity.setTodoCheck(0); //0은 false취급
        return todoEntity;
    }

    public static TodoEntity toUpdateTodoEntity(TodoDTO todoDTO) {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setId(todoDTO.getId());
        todoEntity.setWriter(todoDTO.getWriter());
        todoEntity.setTitle(todoDTO.getTitle());
        todoEntity.setContent(todoDTO.getContent());
        todoEntity.setStartTime(todoDTO.getStartTime());
        todoEntity.setEndTime(todoDTO.getEndTime());
        if (todoDTO.isCheck()){
            todoEntity.setTodoCheck(1) ; //0은 false취급
        } else {
            todoEntity.setTodoCheck(0);
        }
        return todoEntity;
    }

    public static TodoEntity toFlipChecked(TodoDTO todoDTO) {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setId(todoDTO.getId());
        todoEntity.setWriter(todoDTO.getWriter());
        todoEntity.setTitle(todoDTO.getTitle());
        todoEntity.setContent(todoDTO.getContent());
        todoEntity.setStartTime(todoDTO.getStartTime());
        todoEntity.setEndTime(todoDTO.getEndTime());
        if (todoDTO.isCheck()){
            todoEntity.setTodoCheck(0) ; //0은 false취급
        } else {
            todoEntity.setTodoCheck(1);
        }
        return todoEntity;
    }
}
