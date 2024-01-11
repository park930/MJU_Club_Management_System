package com.example.springsecurity.controller;


import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.dto.CustomUserDetails;
import com.example.springsecurity.entity.UserEntity;
import com.example.springsecurity.score.dto.ClubRatingDTO;
import com.example.springsecurity.score.dto.ScoreClubDTO;
import com.example.springsecurity.score.dto.ScoreDTO;
import com.example.springsecurity.score.service.ScoreClubService;
import com.example.springsecurity.score.service.ScoreService;
import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.dto.TodoPersonalDTO;
import com.example.springsecurity.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final TodoService todoService;
    private final ClubService clubService;
    private final ScoreClubService scoreClubService;
    private final ScoreService scoreService;

    @GetMapping("/")
    public String mainP(Model model){

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("id = " + id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TodoPersonalDTO todoPersonalDTO;


        List<ScoreClubDTO> scoreClubDTOList = scoreClubService.findAll();
        List<ScoreDTO> scoreDTOList = scoreService.findAll();

        List<ClubDTO> clubDTOList = clubService.findAll();
        List<ScoreDTO> updateScoreList = scoreService.getScoreInfo(scoreClubDTOList,scoreDTOList,clubDTOList);
        List<Integer> totalScoreList = scoreService.getTotalScore(updateScoreList);
        List<String> headText = scoreService.setHeadText(updateScoreList);

        ClubRatingDTO clubRatingDTO = scoreService.sortScore(headText,totalScoreList,updateScoreList,clubDTOList);
        System.out.println("clubRatingDTO = " + clubRatingDTO);



        Long clubId = 0L;
        int clubScore = 0;
        double percentScore = 0.0;

        if (!id.equals("anonymousUser")){
            //받은 일정 리스트를 가져와야함
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            clubId = customUserDetails.getClubId();
            ClubDTO clubDTO = clubService.findById(clubId);
            todoPersonalDTO = todoService.getFilteredTodoList(clubDTO,id);

            clubScore = scoreService.getMyClubScore(clubRatingDTO,clubDTO);
            percentScore = clubScore/80.0 * 100;

            model.addAttribute("clubId",clubId);
            model.addAttribute("clubDTO",clubDTO);
            model.addAttribute("myTodoList",todoPersonalDTO.getMyTodoDTOList());
            model.addAttribute("receiveTodoList",todoPersonalDTO.getReceviedIncompleteList());
            model.addAttribute("clubScore",clubScore);
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        model.addAttribute("id",id);
        model.addAttribute("role",role);
        model.addAttribute("clubScore",clubScore);
        model.addAttribute("percentScore",percentScore);
        return "main";
    }
}
