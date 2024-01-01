package com.example.springsecurity.todo.entity;

import com.example.springsecurity.club.entity.ClubEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "todo_club_table")
@ToString
public class TodoClubEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    private TodoEntity todoEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private ClubEntity clubEntity;

    public static TodoClubEntity newTodoClub(ClubEntity clubEntity, TodoEntity savedTodo) {
        TodoClubEntity todoClubEntity = new TodoClubEntity();
        todoClubEntity.setClubEntity(clubEntity);
        todoClubEntity.setTodoEntity(savedTodo);
        return todoClubEntity;
    }
}
