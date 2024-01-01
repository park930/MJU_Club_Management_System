package com.example.springsecurity.controller;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.dto.JoinDTO;
import com.example.springsecurity.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;
    private final ClubService clubService;

    @GetMapping("/join")
    public String joinP(Model model){
        List<ClubDTO> clubDTOList = clubService.findAll();
        List<ClubDTO> clubList = joinService.filterClubList(clubDTOList);
        System.out.println("필터한 결과 = " + clubList);
        model.addAttribute("clubList",clubList);
        return "join";
    }

    @GetMapping("/adminJoin")
    public String joinAdminP(){
        return "joinAdmin";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO){
        System.out.println("joinDTO = " + joinDTO.getUsername());
        joinService.joinProcess(joinDTO);
        return "redirect:/login";
    }


}
