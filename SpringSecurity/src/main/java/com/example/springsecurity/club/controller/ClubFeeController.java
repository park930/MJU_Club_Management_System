package com.example.springsecurity.club.controller;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.dto.ClubFeeDTO;
import com.example.springsecurity.club.entity.ClubFeeEntity;
import com.example.springsecurity.club.service.ClubFeeService;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.user.dto.CustomUserDetails;
import com.example.springsecurity.user.service.CustomUserDetailsService;
import com.example.springsecurity.user.service.TempUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/")
    public String clubFeeMain(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Long clubId = customUserDetails.getUserDTO().getClubId();
        List<ClubFeeDTO> clubFeeDTOList = clubFeeService.findAllByClubDTO(clubService.findById(clubId));
        List<ClubFeeDTO> feeUserList = clubFeeService.findFeeUser(clubFeeDTOList);
        model.addAttribute("clubFeeList",clubFeeDTOList);
        model.addAttribute("feeUserList",feeUserList);
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
        Long clubId = clubFeeDTO.getClubId();
        ClubDTO clubDTO = clubService.findById(clubId);
        clubFeeService.save(clubFeeDTO,clubDTO);
        return "redirect:/clubFee/"+clubId;
    }

    @GetMapping("/update/{clubFeeId}")
    public String updateFeeP(@PathVariable Long clubFeeId,Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long clubId = customUserDetails.getClubId();

        ClubFeeDTO clubFeeDTO = clubFeeService.findById(clubFeeId);
        clubFeeDTO.setClubId(clubId);
        model.addAttribute("clubFeeDTO",clubFeeDTO);
        return "clubFeeUpdate";
    }

    @GetMapping("/delete/{clubFeeId}")
    public String deleteFee(@PathVariable Long clubFeeId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long clubId = customUserDetails.getClubId();
        clubFeeService.deleteFee(clubFeeId);
        return "redirect:/clubFee/"+clubId;
    }

    @PostMapping("/update")
    public String updateFee(@ModelAttribute ClubFeeDTO clubFeeDTO){
        clubFeeService.updateClubFee(clubFeeDTO);
        return "redirect:/clubFee/"+clubFeeDTO.getClubId();
    }
}
