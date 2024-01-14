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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        Long clubId = customUserDetails.getClubId();
        int userId = customUserDetails.getUserId();

        List<TempUserDTO> tempUserDTOList = tempUserService.findAllByClubId(clubId);
        ClubDTO clubDTO = clubService.findById(clubId);
        List<UserDTO> userDTOList = customUserDetailsService.findAllByClubDTO(clubDTO);
        UserDTO chairMan = customUserDetailsService.findChairMan(userDTOList);

        model.addAttribute("clubDTO",clubDTO);
        model.addAttribute("clubUserList",userDTOList);
        model.addAttribute("tempUserList",tempUserDTOList);
        model.addAttribute("chairMan",chairMan);
        model.addAttribute("userId",userId);

        return "clubMember";
    }

    @GetMapping("clubMember/manage/{clubId}/{userId}")
    public String manageP(@PathVariable Long clubId, @PathVariable int userId, Model model){
        UserDTO userDTO = customUserDetailsService.findByUserId(userId);
        model.addAttribute("userDTO", userDTO);
        return "clubMemberManage";
    }
}
