package com.example.springsecurity.club.controller;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.user.dto.CustomUserDetails;
import com.example.springsecurity.user.dto.TempUserDTO;
import com.example.springsecurity.user.dto.UserDTO;
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
@RequestMapping("/club")
public class ClubController
{
    private final ClubService clubService;
    private final TempUserService tempUserService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/")
    public String clubForm(Model model){
        List<ClubDTO> clubList = clubService.findAll();
        model.addAttribute("clubList",clubList);
        return "clubManagement";
    }

    @GetMapping("/add")
    public String saveForm(){
        return "clubAdd";
    }

    @PostMapping("/add")
    public String addClub(@ModelAttribute ClubDTO clubDTO){
        clubService.save(clubDTO);
        return "redirect:/club/";
    }

    @GetMapping("/delete/{id}")
    public String deleteClub(@PathVariable Long id){
        clubService.deleteClub(id);
        return "redirect:/club/";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        ClubDTO clubDTO = clubService.findById(id);
        model.addAttribute("club",clubDTO);
        return "clubUpdate";
    }

    @PostMapping("/update")
    public String updateClub(@ModelAttribute ClubDTO clubDTO){
        clubService.updateClub(clubDTO);
        return "redirect:/club/";
    }

    @GetMapping("/member")
    public String clubMemberP(Model model){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long clubId = customUserDetails.getClubId();

        List<TempUserDTO> tempUserDTOList = tempUserService.findAllByClubId(clubId);
        ClubDTO clubDTO = clubService.findById(clubId);
        List<UserDTO> userDTOList = customUserDetailsService.findAllByClubDTO(clubDTO);

        model.addAttribute("clubDTO",clubDTO);
        model.addAttribute("clubUserList",userDTOList);
        model.addAttribute("tempUserList",tempUserDTOList);


        return "clubMember";
    }

}
