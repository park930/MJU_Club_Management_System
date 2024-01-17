package com.example.springsecurity.club.entity;

import com.example.springsecurity.board.entity.BaseEntity;
import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.dto.ClubFeeDTO;
import com.example.springsecurity.score.entity.ScoreClubEntity;
import com.example.springsecurity.todo.entity.TodoClubEntity;
import com.example.springsecurity.todo.entity.TodoCommentEntity;
import com.example.springsecurity.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "club_fee_table")
@ToString
public class ClubFeeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String type;

    @Column
    private String userName;

    @Column
    private int amount;

    @Column
    private LocalDateTime date;

    @Column
    private String purpose;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private ClubEntity clubEntity;


    public static ClubFeeEntity toNewClubFeeEntity(ClubFeeDTO clubFeeDTO,ClubDTO clubDTO) {
        ClubFeeEntity clubFeeEntity = new ClubFeeEntity();
        clubFeeEntity.setType(clubFeeDTO.getType());
        clubFeeEntity.setUserName(clubFeeDTO.getUserName());
        clubFeeEntity.setAmount(clubFeeDTO.getAmount());
        clubFeeEntity.setDate(clubFeeDTO.getDate());
        clubFeeEntity.setPurpose(clubFeeDTO.getPurpose());
        clubFeeEntity.setClubEntity(ClubEntity.toUpdateClub(clubDTO));
        return clubFeeEntity;
    }

    public static ClubFeeEntity toUpdateClubFeeEntity(ClubFeeDTO clubFeeDTO,ClubEntity clubEntity) {
        ClubFeeEntity clubFeeEntity = new ClubFeeEntity();
        clubFeeEntity.setId(clubFeeDTO.getId());
        clubFeeEntity.setType(clubFeeDTO.getType());
        clubFeeEntity.setUserName(clubFeeDTO.getUserName());
        clubFeeEntity.setAmount(clubFeeDTO.getAmount());
        clubFeeEntity.setDate(clubFeeDTO.getDate());
        clubFeeEntity.setPurpose(clubFeeDTO.getPurpose());
        clubFeeEntity.setClubEntity(clubEntity);
        return clubFeeEntity;
    }
}
