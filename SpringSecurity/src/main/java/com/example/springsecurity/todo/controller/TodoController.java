package com.example.springsecurity.todo.controller;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.todo.dto.TodoCommentDTO;
import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.entity.TodoCommentEntity;
import com.example.springsecurity.todo.entity.TodoEntity;
import com.example.springsecurity.todo.service.TodoCommentService;
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
    private final TodoCommentService todoCommentService;

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


    @GetMapping("/receivcedTodo/{clubId}/{id}")
    public String receivcedTodoDetail(
            @PathVariable Long clubId,
            @PathVariable Long id,
            Model model){
        System.out.println("전달받은 clubId = " + clubId);
        System.out.println("전달받은 id = " + id);
        TodoDTO todoDTO = todoService.findById(id);
        System.out.println("전달할 todoDTO = " + todoDTO);
        List<TodoCommentDTO> commentList = todoCommentService.findAll(todoService.findById(id), clubService.findById(clubId));

        model.addAttribute("commentList",commentList);
        model.addAttribute("clubId",clubId);
        model.addAttribute("todo",todoDTO);
        return "receivedTodoDetail";
    }

}
