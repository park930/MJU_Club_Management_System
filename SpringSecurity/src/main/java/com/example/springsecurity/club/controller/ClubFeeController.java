package com.example.springsecurity.club.controller;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.dto.ClubFeeDTO;
import com.example.springsecurity.club.service.ClubFeeService;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.user.service.CustomUserDetailsService;
import com.example.springsecurity.user.service.TempUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clubFee")
public class ClubFeeController
{
    private final ClubService clubService;
    private final ClubFeeService clubFeeService;

    @GetMapping("/{clubId}")
    public String clubFeeMain(@PathVariable Long clubId,Model model){
        List<ClubFeeDTO> clubFeeDTOList = clubFeeService.findAllByClubDTO(clubService.findById(clubId));
        model.addAttribute("clubFeeList",clubFeeDTOList);
        model.addAttribute("clubId",clubId);
        return "clubFeeManagement";
    }

    @GetMapping("/add/{clubId}")
    public String clubFeeAddP(@PathVariable Long clubId, Model model){
        model.addAttribute("clubId",clubId);
        return "clubFeeAdd";
    }

    @PostMapping("/add")
    public String feeSave(@ModelAttribute ClubFeeDTO clubFeeDTO){
        System.out.println("clubFeeDTO = " + clubFeeDTO);
        return "redirect:/clubFee/"+clubFeeDTO.getClubId();
    }
}
