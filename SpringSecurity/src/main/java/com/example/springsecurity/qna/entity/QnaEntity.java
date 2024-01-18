package com.example.springsecurity.qna.entity;

import com.example.springsecurity.board.dto.BoardDTO;
import com.example.springsecurity.board.entity.*;
import com.example.springsecurity.qna.dto.QnaDTO;
import com.example.springsecurity.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.catalina.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="qna_table")
@ToString
public class QnaEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String boardWriter;

    @Column
    private String boardTitle;

    @Column
    private String boardContents;

    @Column
    private int secret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public static QnaEntity toNewQnaEntity(QnaDTO qnaDTO, UserEntity userEntity) {
        QnaEntity qnaEntity = new QnaEntity();
        qnaEntity.setBoardWriter(qnaDTO.getBoardWriter());
        qnaEntity.setBoardTitle(qnaDTO.getBoardTitle());
        qnaEntity.setBoardContents(qnaDTO.getBoardContents());
        qnaEntity.setSecret(qnaDTO.getSecret());
        qnaEntity.setUserEntity(userEntity);
        return qnaEntity;
    }
}
