package com.example.springsecurity.controller;


import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.dto.CustomUserDetails;
import com.example.springsecurity.entity.UserEntity;
import com.example.springsecurity.todo.dto.TodoDTO;
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

    @GetMapping("/")
    public String mainP(Model model){

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("사용자의 이름 = " + id);
                
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<TodoDTO> myTodoDTOList = null;
        List<TodoDTO> receiveTodoList = null;

        Long clubId = 0L;

        if (!id.equals("anonymousUser")){
            myTodoDTOList = todoService.findAllByWriter(id);
            
            //받은 일정 리스트를 가져와야함
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            ClubEntity clubEntity = customUserDetails.getClubEntity();
            clubId = clubEntity.getId();
            receiveTodoList = todoService.findAllByClubEntity(clubEntity);
        }
        


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        System.out.println("receiveTodoList = " + receiveTodoList);
        System.out.println("myTodoDTOList = " + myTodoDTOList);


        model.addAttribute("id",id);
        model.addAttribute("role",role);
        model.addAttribute("clubId",clubId);
        model.addAttribute("myTodoList",myTodoDTOList);
        model.addAttribute("receiveTodoList",receiveTodoList);

        return "main";
    }
}
