package com.example.springsecurity.todo.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TodoClubDTO {
    private Long id;
    private Long todoId;
    private Long clubId;
}
