package com.example.springsecurity.rental.entity;

import com.example.springsecurity.board.entity.BaseEntity;
import com.example.springsecurity.rental.dto.RenterDTO;
import com.example.springsecurity.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Fetch;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "renter_table")
@ToString
public class RenterEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "renterEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RentalRenterEntity> rentalRenterEntityList = new ArrayList<>();


    public static RenterEntity toRenterEntity(RenterDTO renterDTO, UserEntity userEntity) {
        RenterEntity renterEntity = new RenterEntity();
        renterEntity.setStartDate(renterDTO.getStartDate());
        renterEntity.setEndDate(renterDTO.getEndDate());
        renterEntity.setUserEntity(userEntity);
        return renterEntity;
    }

    public static RenterEntity toUpdateRenterEntity(RenterDTO renterDTO,UserEntity userEntity) {
        RenterEntity renterEntity = new RenterEntity();
        renterEntity.setId(renterDTO.getId());
        renterEntity.setStartDate(renterDTO.getStartDate());
        renterEntity.setEndDate(renterDTO.getEndDate());
        renterEntity.setUserEntity(userEntity);
        return renterEntity;
    }
}
