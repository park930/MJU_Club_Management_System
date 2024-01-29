package com.example.springsecurity.todo.entity;

import com.example.springsecurity.board.entity.BaseEntity;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.todo.dto.TodoCommentDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "todo_comment_file_table")
@ToString
public class TodoCommentFileEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todoComment_id")
    private TodoCommentEntity todoCommentEntity;


    public static TodoCommentFileEntity toTodoCommentFileEntity(TodoCommentEntity todoCommentEntity,String originalFileName, String storedFileName){
        TodoCommentFileEntity todoCommentFileEntity = new TodoCommentFileEntity();
        todoCommentFileEntity.setTodoCommentEntity(todoCommentEntity);
        todoCommentFileEntity.setOriginalFileName(originalFileName);
        todoCommentFileEntity.setStoredFileName(storedFileName);
        return todoCommentFileEntity;
    }

}
