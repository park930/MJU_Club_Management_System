package com.example.springsecurity.club.repository;

import com.example.springsecurity.club.entity.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<ClubEntity, Long> {


    ClubEntity findByClubName (String clubName);

}
