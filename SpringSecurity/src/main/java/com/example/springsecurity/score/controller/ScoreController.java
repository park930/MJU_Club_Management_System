package com.example.springsecurity.score.controller;

import com.example.springsecurity.board.dto.CommentDTO;
import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.score.dto.ClubRatingDTO;
import com.example.springsecurity.score.dto.ScoreClubDTO;
import com.example.springsecurity.score.dto.ScoreDTO;
import com.example.springsecurity.score.service.ScoreClubService;
import com.example.springsecurity.score.service.ScoreService;
import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.service.TodoService;
import com.example.springsecurity.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final TodoService todoService;
    private final ScoreClubService scoreClubService;

    @GetMapping("/")
    public String scoreForm(Model model){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long clubId = customUserDetails.getClubId();


        List<ScoreClubDTO> scoreClubDTOList = scoreClubService.findAll();
        List<ScoreDTO> scoreDTOList = scoreService.findAll();

        List<ClubDTO> clubDTOList = clubService.findAll();
        List<ScoreDTO> updateScoreList = scoreService.getScoreInfo(scoreClubDTOList,scoreDTOList,clubDTOList);
        List<Integer> totalScoreList = scoreService.getTotalScore(updateScoreList);
        List<String> headText = scoreService.setHeadText(updateScoreList);

        ClubRatingDTO clubRatingDTO = scoreService.sortScore(headText,totalScoreList,updateScoreList,clubDTOList);
        model.addAttribute("clubRatingDTO",clubRatingDTO);
        model.addAttribute("clubId",clubId);
        model.addAttribute("userId",id);

        return "scoreMain";
    }

    @GetMapping("/update/{scoreId}")
    public String updateP(@PathVariable Long scoreId,Model model){
        ScoreDTO scoreDTO = scoreService.findById(scoreId);
        model.addAttribute("scoreDTO",scoreDTO);
        return "scoreUpdate";
    }

    @PostMapping("/update")
    public String updateScore(@ModelAttribute ScoreDTO scoreDTO){
        Long scoreId = scoreService.update(scoreDTO);
        scoreClubService.refreshScore(scoreDTO);
        return "redirect:/todo/admin/"+scoreDTO.getTodoId();
    }


    @GetMapping("/plusScoreAdd")
    public String plusScoreAddForm(Model model){
        List<ScoreDTO> scoreDTOList = scoreService.findAll();
        model.addAttribute("scoreList",scoreDTOList);
        return "plusScoreForm";
    }

    @PostMapping("/plusScoreDetails")
    public ResponseEntity save(@RequestParam Long scoreId){
        List<ScoreClubDTO> scoreClubDTOList = scoreClubService.findAllByScoreDTO(scoreService.findById(scoreId));
        List<ClubDTO> clubDTOList = scoreClubService.filterClubList(scoreClubDTOList);
        if (clubDTOList != null){
            //작성 성공 시, 댓글 목록들을 가져와서 리턴
            return new ResponseEntity<>(clubDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당되는 동아리는 존재하지 않습니다.",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/plusScoreAdd/proc")
    public String plusScoreSave(@RequestParam("scoreTitle") Long scoreId,
                                @RequestParam("clubId") Long clubId,
                                @RequestParam("plusScoreId") int plusScore){
        ClubDTO clubDTO = clubService.findById(clubId);
        ScoreDTO scoreDTO = scoreService.findById(scoreId);
        scoreClubService.updatePlusScore(clubDTO,scoreDTO,plusScore);
        return "redirect:/score/";
    }

}
