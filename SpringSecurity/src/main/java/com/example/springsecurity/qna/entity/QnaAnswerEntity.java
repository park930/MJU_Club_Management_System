package com.example.springsecurity.qna.entity;

import com.example.springsecurity.board.entity.BaseEntity;
import com.example.springsecurity.qna.dto.QnaAnswerDTO;
import com.example.springsecurity.qna.dto.QnaDTO;
import com.example.springsecurity.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name="qna_answer_table")
@ToString
public class QnaAnswerEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String answerWriter;

    @Column
    private String answerContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private QnaEntity qnaEntity;


    public static QnaAnswerEntity toNewQnaAnswer(QnaAnswerDTO qnaAnswerDTO,QnaEntity qnaEntity) {
        QnaAnswerEntity qnaAnswerEntity = new QnaAnswerEntity();
        qnaAnswerEntity.setAnswerWriter(qnaAnswerDTO.getAnswerWriter());
        qnaAnswerEntity.setAnswerContents(qnaAnswerDTO.getAnswerContents());
        qnaAnswerEntity.setQnaEntity(qnaEntity);
        return qnaAnswerEntity;
    }
}
