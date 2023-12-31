package com.example.springsecurity.todo.controller;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.entity.TodoEntity;
import com.example.springsecurity.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TodoDTO> todoDTOList = todoService.findAll();
        model.addAttribute("todoList",todoDTOList);
        if (userName.equals("admin")){
            return "adminTodoMain";
        } else {
            return "todoMain";
        }
    }

    @GetMapping("/admin/add")
    public String saveForm(Model model){
        List<ClubDTO> clubDTOList = clubService.findAll();
        model.addAttribute("clubList",clubDTOList);
        return "adminTodoAdd";
    }

//    @PostMapping("/checkChange")
//    public ResponseEntity changeChecked(
//            @RequestParam("todoId") Long todoId
//    ){
//        Long result = todoService.flipChecked(todoService.findById(todoId));
//        if (result != null) {
//            List<TodoDTO> todoDTOList = todoService.findAll();
//            return new ResponseEntity<>(todoDTOList, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("해당 일정이 존재하지 않습니다.",HttpStatus.NOT_FOUND);
//        }
//    }

    @PostMapping("/checkChange")
    public String changeChecked(
            @RequestParam("todoId") Long todoId
    ){
        Long result = todoService.flipChecked(todoService.findById(todoId));
        return "redirect:/todo/";
    }


    @PostMapping("admin/add")
    public String addClub(@ModelAttribute TodoDTO todoDTO, @RequestParam(value = "checkedList") List<Long> checkedList){
        if (checkedList != null){
            TodoEntity savedTodo = todoService.saveTodo(todoDTO);

            for(Long clubId : checkedList) {
                ClubDTO clubDTO = clubService.findById(clubId);
                todoService.saveTodoClub(ClubEntity.toUpdateClub(clubDTO),savedTodo);
            }
        }
        return "redirect:/todo/";
    }

    @GetMapping("/admin/{id}")
    public String detailAdminTodo(@PathVariable Long id,Model model){
        TodoDTO todoDTO = todoService.findById(id);
        List<ClubDTO> clubDTOList = todoService.getClubList(id);
        model.addAttribute("todoDTO",todoDTO);
        model.addAttribute("clubDTOList",clubDTOList);
        return "todoAdminDetail";
    }

}
