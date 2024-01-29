package com.example.springsecurity.todo.entity;

import com.example.springsecurity.board.entity.BaseEntity;
import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.todo.dto.TodoCommentDTO;
import com.example.springsecurity.todo.dto.TodoDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "todo_comment_table")
@ToString
public class TodoCommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private ClubEntity clubEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    private TodoEntity todoEntity;

    @Column
    private String type;

    @Column
    private int isSubmit;

    @Column
    private String content;

    @Column
    private int fileAttached;

    @OneToMany(mappedBy = "todoCommentEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TodoCommentFileEntity> todoCommentFileEntityList = new ArrayList<>();


    public static TodoCommentEntity toSaveTodoCommentEntity(TodoCommentDTO todoCommentDTO, TodoEntity todoEntity, ClubEntity clubEntity) {
        TodoCommentEntity todoCommentEntity = new TodoCommentEntity();
        todoCommentEntity.setClubEntity(clubEntity);
        todoCommentEntity.setTodoEntity(todoEntity);
        todoCommentEntity.setType(todoCommentDTO.getType());
        todoCommentEntity.setContent(todoCommentDTO.getContent());
        todoCommentEntity.setIsSubmit(todoCommentDTO.getResultSubmit());
        todoCommentEntity.setFileAttached(0);   //파일 없음
        return todoCommentEntity;
    }

    public static TodoCommentEntity toSaveFileTodoCommentEntity(TodoCommentDTO todoCommentDTO, TodoEntity todoEntity, ClubEntity clubEntity) {
        TodoCommentEntity todoCommentEntity = new TodoCommentEntity();
        todoCommentEntity.setClubEntity(clubEntity);
        todoCommentEntity.setTodoEntity(todoEntity);
        todoCommentEntity.setType(todoCommentDTO.getType());
        todoCommentEntity.setContent(todoCommentDTO.getContent());
        todoCommentEntity.setIsSubmit(todoCommentDTO.getResultSubmit());
        todoCommentEntity.setFileAttached(1);   //파일 있음
        return todoCommentEntity;
    }

    public static TodoCommentEntity toUpdateTodoCommentEntity(TodoCommentDTO commentDTO, TodoEntity todoEntity, ClubEntity clubEntity) {
        TodoCommentEntity todoCommentEntity = new TodoCommentEntity();
        todoCommentEntity.setId(commentDTO.getId());
        todoCommentEntity.setContent(commentDTO.getContent());
        todoCommentEntity.setType(commentDTO.getType());
        todoCommentEntity.setIsSubmit(commentDTO.getResultSubmit());
        todoCommentEntity.setClubEntity(clubEntity);
        todoCommentEntity.setTodoEntity(todoEntity);
        return todoCommentEntity;
    }

}
