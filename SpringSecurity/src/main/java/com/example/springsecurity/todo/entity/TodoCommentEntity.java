package com.example.springsecurity.todo.entity;

import com.example.springsecurity.board.entity.BaseEntity;
import com.example.springsecurity.club.entity.ClubEntity;
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
}
