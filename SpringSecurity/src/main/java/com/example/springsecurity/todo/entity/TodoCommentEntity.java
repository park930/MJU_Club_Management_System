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


    public static TodoCommentEntity toTodoCommentEntity(TodoCommentDTO todoCommentDTO, TodoEntity todoEntity, ClubEntity clubEntity) {
        TodoCommentEntity todoCommentEntity = new TodoCommentEntity();
        todoCommentEntity.setClubEntity(clubEntity);
        todoCommentEntity.setTodoEntity(todoEntity);
        todoCommentEntity.setType(todoCommentDTO.getType());
        todoCommentEntity.setContent(todoCommentDTO.getContent());
        System.out.println("결과물 제출? = " + todoCommentDTO.getResultSubmit());
        todoCommentEntity.setIsSubmit(todoCommentDTO.getResultSubmit());
        return todoCommentEntity;
    }
}
