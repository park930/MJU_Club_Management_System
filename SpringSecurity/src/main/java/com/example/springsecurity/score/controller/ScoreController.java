package com.example.springsecurity.score.controller;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.score.dto.ScoreClubDTO;
import com.example.springsecurity.score.dto.ScoreDTO;
import com.example.springsecurity.score.service.ScoreClubService;
import com.example.springsecurity.score.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/score")
public class ScoreController
{
    private final ScoreService scoreService;
    private final ClubService clubService;
    private final ScoreClubService scoreClubService;

    @GetMapping("/")
    public String scoreForm(Model model){
        List<ClubDTO> clubDTOList = clubService.findAll();
        List<ScoreClubDTO> scoreClubDTOList = scoreClubService.findAll();
        List<ScoreDTO> scoreDTOList = scoreService.findAll();
        List<ScoreDTO> updateScoreList = scoreService.getScoreInfo(scoreClubDTOList,scoreDTOList,clubDTOList);
        List<String> headText = scoreService.setHeadText(updateScoreList);
        System.out.println("updateScoreList = " + updateScoreList);
        model.addAttribute("clubList",clubDTOList);
        model.addAttribute("scoreList",updateScoreList);
        model.addAttribute("headText",headText);

        return "scoreMain";
    }


}
