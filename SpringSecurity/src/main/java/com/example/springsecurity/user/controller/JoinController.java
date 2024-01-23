package com.example.springsecurity.user.controller;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.user.dto.JoinDTO;
import com.example.springsecurity.user.dto.UserDTO;
import com.example.springsecurity.user.service.JoinService;
import lombok.RequiredArgsConstructor;
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
        model.addAttribute("clubList",clubList);
        return "join";
    }
    @GetMapping("/tempJoin")
    public String tempJoinP(Model model){
        List<ClubDTO> clubDTOList = clubService.findAll();
        model.addAttribute("clubList",clubDTOList);
        return "tempJoin";
    }

    @GetMapping("/adminJoin")
    public String joinAdminP(){
        return "joinAdmin";
    }

    @PostMapping("/joinProc")
    public String joinProcess(UserDTO userDTO){
        joinService.joinProcess(userDTO,"Executives");
        return "redirect:/login";
    }

    @PostMapping("/joinAdminProc")
    public String joinAdminProcess(UserDTO userDTO){
        joinService.joinAdminProcess(userDTO);
        return "redirect:/login";
    }


    @PostMapping("/tempJoinProc")
    public String tempJoinProcess(UserDTO userDTO){
        joinService.joinProcess(userDTO,"normal");
        return "redirect:/login";
    }

}
