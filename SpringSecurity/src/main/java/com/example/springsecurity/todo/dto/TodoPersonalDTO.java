package com.example.springsecurity.todo.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TodoPersonalDTO {
    private List<TodoDTO> totalTodoDTOList;
    private List<TodoDTO> receviedCompleteList;
    private List<TodoDTO> receviedIncompleteList;
    private List<TodoDTO> myTodoDTOList;
    private List<String> remainTimeList;
    private List<LocalDateTime> submitDateList;
}
