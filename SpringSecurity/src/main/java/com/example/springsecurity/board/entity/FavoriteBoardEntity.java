package com.example.springsecurity.board.entity;

import com.example.springsecurity.board.dto.FavoriteBoardDTO;
import com.example.springsecurity.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="favorite_board_table")
public class FavoriteBoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column
    private String boardTitle;


    public static FavoriteBoardEntity toFavoriteEntity(FavoriteBoardDTO favoriteBoardDTO, BoardEntity boardEntity, UserEntity userEntity) {
        FavoriteBoardEntity favoriteBoardEntity = new FavoriteBoardEntity();
        favoriteBoardEntity.setBoardEntity(boardEntity);
        favoriteBoardEntity.setUserEntity(userEntity);
        favoriteBoardEntity.setBoardTitle(favoriteBoardDTO.getBoardTitle());
        return favoriteBoardEntity;
    }
}
