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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private ClubEntity clubEntity;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private boolean check;

}
