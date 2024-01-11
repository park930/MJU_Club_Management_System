package com.example.springsecurity.todo.controller;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.dto.UserDTO;
import com.example.springsecurity.entity.UserEntity;
import com.example.springsecurity.score.dto.ScoreDTO;
import com.example.springsecurity.score.service.ScoreClubService;
import com.example.springsecurity.score.service.ScoreService;
import com.example.springsecurity.service.CustomUserDetailsService;
import com.example.springsecurity.todo.dto.TodoCommentDTO;
import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.dto.TodoPersonalDTO;
import com.example.springsecurity.todo.entity.TodoEntity;
import com.example.springsecurity.todo.service.TodoCommentService;
import com.example.springsecurity.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController
{
    private final ClubService clubService;
    private final TodoService todoService;
    private final TodoCommentService todoCommentService;
    private final ScoreClubService scoreClubService;
    private final CustomUserDetailsService customUserDetailsService;
    private final ScoreService scoreService;

    private TodoPersonalDTO todoPersonalDTO;

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
        ClubDTO clubDTO = clubService.findById(customUserDetailsService.findByUserName(userName).getClubId());
        todoPersonalDTO = todoService.getFilteredTodoList(clubDTO,userName);
        model.addAttribute("incompleteList",todoPersonalDTO.getReceviedIncompleteList());
        model.addAttribute("completeList",todoPersonalDTO.getReceviedCompleteList());
        model.addAttribute("myTodoList",todoPersonalDTO.getMyTodoDTOList());
        model.addAttribute("remainTimeList",todoPersonalDTO.getRemainTimeList());
        model.addAttribute("submitDateList",todoPersonalDTO.getSubmitDateList());
        model.addAttribute("clubId",clubDTO.getId());
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
    public String addClub(@RequestParam(value = "scoreCheckBox", defaultValue = "false") boolean scoreCheckBox,
                          @ModelAttribute ScoreDTO scoreDTO,
                          @ModelAttribute TodoDTO todoDTO,
                          @RequestParam(value = "checkedList") List<Long> checkedList){

        TodoEntity savedTodo = todoService.saveTodo(todoDTO);

        ScoreDTO savedScoreDTO = null;

        if (scoreCheckBox){
            scoreDTO.setTodoId(savedTodo.getId());
            savedScoreDTO = scoreService.saveScoreTable(scoreDTO);
        }

        if (checkedList != null){
            for(Long clubId : checkedList) {
                ClubDTO clubDTO = clubService.findById(clubId);
                todoService.saveTodoClub(ClubEntity.toUpdateClub(clubDTO),savedTodo);
                if (scoreCheckBox){
                    scoreClubService.save(savedScoreDTO,clubDTO);
                }
            }
        }
        return "redirect:/todo/admin";
    }

    @PostMapping("/user/add")
    public String addPersonalTodo(@ModelAttribute TodoDTO todoDTO){
        ClubDTO clubDTO = clubService.findById(customUserDetailsService.findByUserName(todoDTO.getWriter()).getClubId());
        TodoEntity savedTodo = todoService.saveTodo(todoDTO);
        todoService.saveTodoClub(ClubEntity.toUpdateClub(clubDTO),savedTodo);
        return "redirect:/todo/user";
    }

    @GetMapping("/admin/{id}")
    public String detailAdminTodo(@PathVariable Long id,Model model){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        TodoDTO todoDTO = todoService.findById(id);
        List<TodoCommentDTO> completeCommentList = todoService.filterCompleteClub(todoDTO);
        List<ClubDTO> clubDTOList = todoService.getImcompleteClubList(todoDTO,completeCommentList);
        model.addAttribute("todoDTO",todoDTO);
        model.addAttribute("clubDTOList",clubDTOList);
        model.addAttribute("completeCommentList",completeCommentList);
        model.addAttribute("userId",userId);
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
        TodoDTO todoDTO = todoService.findById(id);
        ScoreDTO scoreDTO = scoreService.findByTodoId(todoDTO.getId());
        List<TodoCommentDTO> commentList = todoCommentService.findAll(todoService.findById(id), clubService.findById(clubId));

        model.addAttribute("commentList",commentList);
        model.addAttribute("clubId",clubId);
        model.addAttribute("todo",todoDTO);
        model.addAttribute("scoreDTO",scoreDTO);
        return "receivedTodoDetail";
    }


    @GetMapping("/user/event") //ajax 데이터 전송 URL
    public @ResponseBody List<Map<String, Object>> getEvent(){
        return todoService.getEventList(todoPersonalDTO);
    }


    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        todoService.deleteById(id);

        ScoreDTO scoreDTO = scoreService.findByTodoId(id);
        if (scoreDTO != null){
            scoreService.delete(scoreDTO);
        }


        if (userId.startsWith("admin")){
            return "redirect:/todo/admin";
        } else {
            return "redirect:/todo/user";
        }
    }


}
