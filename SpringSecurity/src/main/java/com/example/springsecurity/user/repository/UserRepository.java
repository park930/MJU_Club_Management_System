package com.example.springsecurity.user.repository;

import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByUsername(String username);

    UserEntity findByUsername(String username);

    List<UserEntity> findAllByClubEntity(ClubEntity clubEntity);

    List<UserEntity> findAllByClubEntityOrderByUsernameAsc(ClubEntity clubEntity);

    @Query("SELECT u.clubEntity FROM UserEntity u")
    List<ClubEntity> findAllClubEntities();

}
