package com.example.springsecurity.score.service;

import com.example.springsecurity.score.dto.ScoreDTO;
import com.example.springsecurity.score.entity.ScoreEntity;
import com.example.springsecurity.score.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository clubRepository;


}
