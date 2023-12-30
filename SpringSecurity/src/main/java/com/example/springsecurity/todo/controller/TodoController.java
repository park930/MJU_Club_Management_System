package com.example.springsecurity.todo.controller;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController
{
    private final ClubService clubService;
    private final TodoService todoService;

    @GetMapping("/")
    public String todoMain(Model model){
        List<TodoDTO> todoDTOList = todoService.findAll();
        model.addAttribute("todoList",todoDTOList);
        return "adminTodoMain";
    }

    @GetMapping("/admin/add")
    public String saveForm(Model model){
        List<ClubDTO> clubDTOList = clubService.findAll();
        model.addAttribute("clubList",clubDTOList);
        return "adminTodoAdd";
    }

//    @PostMapping("admin/add")
//    public String addClub(@ModelAttribute TodoDTO todoDTO, @RequestParam(value = "checkedList") List<Long> checkedList){
//        if (checkedList != null){
//            //추출된 동아리로 리스트 다룰 필요 있음
//        }
//        clubService.save(clubDTO);
//        return "redirect:/club/";
//    }


}
