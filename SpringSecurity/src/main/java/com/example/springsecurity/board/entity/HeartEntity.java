package com.example.springsecurity.board.entity;

import com.example.springsecurity.board.dto.HeartDTO;
import com.example.springsecurity.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Heart_table")
public class HeartEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public static HeartEntity toHeartDTO(HeartDTO heartDTO, BoardEntity boardEntity, UserEntity userEntity) {
        HeartEntity heartEntity = new HeartEntity();
        heartEntity.setBoardEntity(boardEntity);
        heartEntity.setUserEntity(userEntity);
        heartEntity.setId(heartDTO.getId());
        return heartEntity;
    }
}
