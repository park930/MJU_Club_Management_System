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
@RequestMapping("/clubMember")
public class ClubMemberController {

    private final TempUserService tempUserService;
    private final ClubService clubService;
    private final CustomUserDetailsService customUserDetailsService;



    @GetMapping("/")
    public String clubMemberP(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        if (customUserDetails.getUserName().startsWith("admin")){
            List<UserDTO> adminList = customUserDetailsService.findAllAdmin();
            model.addAttribute("adminList",adminList);
            return "adminManage";
        }


        Long clubId = customUserDetails.getClubId();
        int userId = customUserDetails.getUserId();

        List<TempUserDTO> tempUserDTOList = tempUserService.findAllByClubId(clubId);
        ClubDTO clubDTO = clubService.findById(clubId);
        List<UserDTO> userDTOList = customUserDetailsService.findAllByClubDTO(clubDTO);
        UserDTO chairMan = customUserDetailsService.findChairMan(userDTOList);
        int clubRight = customUserDetailsService.getClubRight(userDTOList,userId);

        model.addAttribute("clubDTO",clubDTO);
        model.addAttribute("clubUserList",userDTOList);
        model.addAttribute("tempUserList",tempUserDTOList);
        model.addAttribute("chairMan",chairMan);
        model.addAttribute("clubRight",clubRight);
        model.addAttribute("userId",userId);

        return "clubMember";
    }

    @GetMapping("/manage/{userId}")
    public String manageP(@PathVariable int userId, Model model){
        UserDTO userDTO = customUserDetailsService.findByUserId(userId);
        UserDTO clubChairMan = customUserDetailsService.findClubChairMan(userDTO.getClubId());
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("chairMan",clubChairMan);
        return "clubMemberManage";
    }

    @PostMapping("/manage/update")
    public String updatePosition(@RequestParam int userId, @RequestParam String detailPosition, @RequestParam String position){
        customUserDetailsService.updatePosition(userId,detailPosition,position);
        return "redirect:/clubMember/";
    }

    @GetMapping("/delete/{userId}")
    public String deleteMember(@PathVariable int userId){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Long clubId = customUserDetailsService.deleteMember(userId);
        if (id.startsWith("admin")){
            return "redirect:/club/admin/"+clubId;
        } else {
            return "redirect:/clubMember/";
        }
    }


}
