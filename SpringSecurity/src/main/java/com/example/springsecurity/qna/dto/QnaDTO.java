package com.example.springsecurity.qna.dto;

import com.example.springsecurity.board.entity.BoardEntity;
import com.example.springsecurity.qna.entity.QnaEntity;
import com.example.springsecurity.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QnaDTO {
    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private int answer;
    private int secret;
    private int userId;
    private LocalDateTime createdTime;



    private String answerString;

    public static QnaDTO toQnaDTO(QnaEntity qnaEntity) {
        QnaDTO qnaDTO = new QnaDTO();
        qnaDTO.setId(qnaEntity.getId());
        qnaDTO.setBoardWriter(qnaEntity.getBoardWriter());
        qnaDTO.setBoardTitle(qnaEntity.getBoardTitle());
        qnaDTO.setBoardContents(qnaEntity.getBoardContents());
        qnaDTO.setAnswer(qnaEntity.getAnswer());
        qnaDTO.setSecret(qnaEntity.getSecret());
        qnaDTO.setUserId(qnaEntity.getUserEntity().getId());
        qnaDTO.setCreatedTime(qnaEntity.getCreatedTime());
        return qnaDTO;
    }
}
