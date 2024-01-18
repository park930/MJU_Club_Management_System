package com.example.springsecurity.board.dto;

import com.example.springsecurity.board.entity.HeartEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class HeartDTO {
    private Long id;
    private String userId;
    private Long boardId;
    private LocalDateTime favoriteCreatedTime;

}
