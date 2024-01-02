package com.example.springsecurity.todo.controller;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.entity.UserEntity;
import com.example.springsecurity.service.CustomUserDetailsService;
import com.example.springsecurity.todo.dto.TodoCommentDTO;
import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.entity.TodoEntity;
import com.example.springsecurity.todo.service.TodoCommentService;
import com.example.springsecurity.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
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
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/admin")
    public String todoMain(Model model){
        List<TodoDTO> todoDTOList = todoService.findAll();
        model.addAttribute("todoList",todoDTOList);
        
        //admin todo페이지로 넘어갈때, user todo페이지로 넘어갈때 분리해야함
        //model에 넘기는게 각자 다름
        return "adminTodoMain";

    }

    @GetMapping("/user")
    public String todoUserMain(Model model){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        ClubEntity clubEntity = customUserDetailsService.findByUserName(userName).getClubEntity();
        List<TodoDTO> totalTodoDTOList = todoService.findAllByClubEntity(clubEntity);
        List<TodoDTO> receiveTodoList = todoService.filterReceivedTodo(totalTodoDTOList);
        List<TodoDTO> myTodoDTOList = todoService.filterMyTodo(totalTodoDTOList);

        model.addAttribute("todoList",receiveTodoList);
        model.addAttribute("myTodoList",myTodoDTOList);
        return "userTodoMain";
    }

    @GetMapping("/admin/add")
    public String saveAdminForm(Model model){
        List<ClubDTO> clubDTOList = clubService.findAll();
        model.addAttribute("clubList",clubDTOList);
        return "adminTodoAdd";
    }

    @GetMapping("/user/add")
    public String saveUserForm(Model model){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("writer",userName);
        return "userTodoAdd";
    }

    @PostMapping("/checkChange")
    public String changeChecked(
            @RequestParam("todoId") Long todoId,
            @RequestParam("role") String role
    ){
        Long result = todoService.flipChecked(todoService.findById(todoId));
        if (role.equals("user")) {
            return "redirect:/todo/user";
        } else {
            return "redirect:/todo/admin";
        }
    }


    @PostMapping("/admin/add")
    public String addClub(@ModelAttribute TodoDTO todoDTO, @RequestParam(value = "checkedList") List<Long> checkedList){
        if (checkedList != null){
            TodoEntity savedTodo = todoService.saveTodo(todoDTO);

            for(Long clubId : checkedList) {
                ClubDTO clubDTO = clubService.findById(clubId);
                todoService.saveTodoClub(ClubEntity.toUpdateClub(clubDTO),savedTodo);
            }
        }
        return "redirect:/todo/admin";
    }

    @PostMapping("/user/add")
    public String addPersonalTodo(@ModelAttribute TodoDTO todoDTO){
        ClubEntity clubEntity = customUserDetailsService.findByUserName(todoDTO.getWriter()).getClubEntity();
        TodoEntity savedTodo = todoService.saveTodo(todoDTO);
        todoService.saveTodoClub(clubEntity,savedTodo);
        return "redirect:/todo/user";
    }

    @GetMapping("/admin/{id}")
    public String detailAdminTodo(@PathVariable Long id,Model model){
        TodoDTO todoDTO = todoService.findById(id);
        List<TodoCommentDTO> completeCommentList = todoService.filterCompleteClub(todoDTO);
        List<ClubDTO> clubDTOList = todoService.getImcompleteClubList(todoDTO,completeCommentList);
        model.addAttribute("todoDTO",todoDTO);
        model.addAttribute("clubDTOList",clubDTOList);
        model.addAttribute("completeCommentList",completeCommentList);
        return "todoAdminDetail";
    }

    @GetMapping("/user/{id}")
    public String detailUserTodo(@PathVariable Long id,Model model){
        TodoDTO todoDTO = todoService.findById(id);
//        List<TodoCommentDTO> completeCommentList = todoService.filterCompleteClub(todoDTO);
//        List<ClubDTO> clubDTOList = todoService.getImcompleteClubList(todoDTO,completeCommentList);
        model.addAttribute("todoDTO",todoDTO);
//        model.addAttribute("clubDTOList",clubDTOList);
//        model.addAttribute("completeCommentList",completeCommentList);
        return "todoUserDetail";
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
