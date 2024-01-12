package com.example.springsecurity.user.controller;

import com.example.springsecurity.user.dto.TempUserDTO;
import com.example.springsecurity.user.dto.UserDTO;
import com.example.springsecurity.user.service.CustomUserDetailsService;
import com.example.springsecurity.user.service.TempUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class TempUserController {

    private final TempUserService tempUserService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/tempUser/agree/{id}")
    public String tempUserAgree(@PathVariable int id){
        TempUserDTO tempUserDTO = tempUserService.findById(id);
        UserDTO userDTO = UserDTO.toUserDTO(tempUserDTO);
        customUserDetailsService.save(userDTO);
        tempUserService.deleteById(id);
        return "redirect:/club/member";
    }

    @GetMapping("/tempUser/disagree/{id}")
    public String tempUserDisagree(@PathVariable int id){
        tempUserService.deleteById(id);
        return "redirect:/club/member";
    }
}
