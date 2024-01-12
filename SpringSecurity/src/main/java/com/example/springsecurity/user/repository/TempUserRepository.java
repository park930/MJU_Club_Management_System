package com.example.springsecurity.user.repository;

import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.user.entity.TempUserEntity;
import com.example.springsecurity.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TempUserRepository extends JpaRepository<TempUserEntity, Integer> {
    List<TempUserEntity> findAllByClubEntity(ClubEntity clubEntity);

}
