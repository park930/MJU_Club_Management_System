package com.example.springsecurity.score.controller;

import com.example.springsecurity.score.dto.ScoreDTO;
import com.example.springsecurity.score.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/score")
public class ScoreController
{
    private final ScoreService scoreService;

    @GetMapping("/")
    public String scoreForm(Model model){
        return "scoreMain";
    }


}
