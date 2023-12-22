package com.example.springsecurity.board.dto;

import com.example.springsecurity.board.entity.BoardEntity;
import com.example.springsecurity.board.entity.FavoriteBoardEntity;
import com.example.springsecurity.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class FavoriteBoardDTO {
    private Long id;
    private String userId;
    private Long boardId;
    private String boardTitle;
    private String boardWriter;
    private LocalDateTime favoriteCreatedTime;

    public static FavoriteBoardDTO toFavoriteDTO(FavoriteBoardEntity favoriteBoardEntity, String userName) {
        FavoriteBoardDTO favoriteBoardDTO = new FavoriteBoardDTO();
        System.out.println("변환할 때의 favoriteBoardEntity = " + favoriteBoardEntity);
        BoardEntity boardEntity = favoriteBoardEntity.getBoardEntity();

        favoriteBoardDTO.setId(favoriteBoardEntity.getId());
        favoriteBoardDTO.setBoardTitle(favoriteBoardEntity.getBoardTitle());
        favoriteBoardDTO.setUserId(userName);
        favoriteBoardDTO.setBoardId(boardEntity.getId());
        favoriteBoardDTO.setBoardWriter(boardEntity.getBoardWriter());
        favoriteBoardDTO.setFavoriteCreatedTime(favoriteBoardEntity.getCreatedTime());
        return favoriteBoardDTO;
    }
}
