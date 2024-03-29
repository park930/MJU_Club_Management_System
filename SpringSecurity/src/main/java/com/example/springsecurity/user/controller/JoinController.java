package com.example.springsecurity.user.controller;

import com.example.springsecurity.board.dto.CommentDTO;
import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.user.dto.JoinDTO;
import com.example.springsecurity.user.dto.UserDTO;
import com.example.springsecurity.user.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String joinProcess(@ModelAttribute UserDTO userDTO){
        joinService.joinProcess(userDTO,"Executives");
        return "redirect:/login";
    }

    @PostMapping("/joinAdminProc")
    public String joinAdminProcess(@ModelAttribute UserDTO userDTO){
        joinService.joinAdminProcess(userDTO);
        return "redirect:/login";
    }


    @PostMapping("/tempJoinProc")
    public String tempJoinProcess(@ModelAttribute UserDTO userDTO){
        System.out.println("모델어트리 userDTO = " + userDTO);
        joinService.joinProcess(userDTO,"normal");
        return "redirect:/login";
    }

    @PostMapping("/idCheck")
    public ResponseEntity idCheckProc(@RequestParam String username){
        int checkId = joinService.checkId(username);
        return new ResponseEntity<>(checkId, HttpStatus.OK);
    }

}
