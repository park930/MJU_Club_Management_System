package com.example.springsecurity.controller;


import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.dto.CustomUserDetails;
import com.example.springsecurity.entity.UserEntity;
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

    @GetMapping("/")
    public String mainP(Model model){

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
                
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TodoPersonalDTO todoPersonalDTO;

        Long clubId = 0L;

        if (!id.equals("anonymousUser")){
            //받은 일정 리스트를 가져와야함
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            ClubEntity clubEntity = customUserDetails.getClubEntity();
            clubId = clubEntity.getId();
            ClubDTO clubDTO = clubService.findById(clubId);
            todoPersonalDTO = todoService.getFilteredTodoList(clubEntity,id);
            model.addAttribute("clubId",clubId);
            model.addAttribute("clubDTO",clubDTO);
            model.addAttribute("myTodoList",todoPersonalDTO.getMyTodoDTOList());
            model.addAttribute("receiveTodoList",todoPersonalDTO.getReceviedIncompleteList());

        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        model.addAttribute("id",id);
        model.addAttribute("role",role);
        return "main";
    }
}
