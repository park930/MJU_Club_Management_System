package com.example.springsecurity.todo.dto;

import com.example.springsecurity.todo.entity.TodoCommentEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TodoCommentDTO {
    private Long id;
    private Long clubId;
    private String clubName;
    private Long todoId;
    private String type;
    private String content;
    private int resultSubmit;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;



    private MultipartFile resultFile;
    private String originalFileName;
    private String storedFileName;
    private int fileAttached;



    public static TodoCommentDTO toTodoCommentDTO(TodoCommentEntity todoCommentEntity) {
        TodoCommentDTO todoCommentDTO = new TodoCommentDTO();
        todoCommentDTO.setId(todoCommentEntity.getId());
        todoCommentDTO.setClubId(todoCommentEntity.getClubEntity().getId());
        todoCommentDTO.setTodoId(todoCommentEntity.getTodoEntity().getId());
        todoCommentDTO.setType(todoCommentEntity.getType());
        todoCommentDTO.setContent(todoCommentEntity.getContent());
        todoCommentDTO.setResultSubmit(todoCommentEntity.getIsSubmit());
        todoCommentDTO.setCreatedTime(todoCommentEntity.getCreatedTime());
        todoCommentDTO.setUpdatedTime(todoCommentEntity.getUpdatedTime());
        if (todoCommentEntity.getFileAttached() == 0){
            todoCommentDTO.setFileAttached(todoCommentEntity.getFileAttached());
        } else {
            todoCommentDTO.setFileAttached(todoCommentEntity.getFileAttached());
            todoCommentDTO.setOriginalFileName(todoCommentEntity.getTodoCommentFileEntityList().get(0).getOriginalFileName());
            todoCommentDTO.setStoredFileName(todoCommentEntity.getTodoCommentFileEntityList().get(0).getStoredFileName());
        }
        return todoCommentDTO;
    }
}
