package com.example.springsecurity.club.repository;

import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.entity.ClubFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubFeeRepository extends JpaRepository<ClubFeeEntity, Long> {
    List<ClubFeeEntity> findAllByClubEntityOrderByDateDesc(ClubEntity clubEntity);
}
