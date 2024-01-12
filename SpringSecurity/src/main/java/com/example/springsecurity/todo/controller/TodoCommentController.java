package com.example.springsecurity.todo.controller;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.score.service.ScoreClubService;
import com.example.springsecurity.todo.dto.TodoCommentDTO;
import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.entity.TodoEntity;
import com.example.springsecurity.todo.service.TodoClubService;
import com.example.springsecurity.todo.service.TodoCommentService;
import com.example.springsecurity.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/receivedTodoComment")
public class TodoCommentController {

    private final TodoService todoService;
    private final TodoCommentService todoCommentService;
    private final ClubService clubService;
    private final TodoClubService todoClubService;
    private final ScoreClubService scoreClubService;

    @PostMapping("/save")
    public ResponseEntity saveTodoComment(@ModelAttribute TodoCommentDTO todoCommentDTO){
        TodoDTO todoDTO = todoService.findById(todoCommentDTO.getTodoId());
        ClubDTO clubDTO = clubService.findById(todoCommentDTO.getClubId());
        TodoCommentDTO save = todoCommentService.save(todoCommentDTO, todoDTO, clubDTO);


        if (todoCommentDTO.getResultSubmit()==1){
            todoClubService.updateTodoClub(TodoEntity.toUpdateTodoEntity(todoDTO),ClubEntity.toUpdateClub(clubDTO));
            scoreClubService.saveSubmitType(todoDTO.getId(),clubDTO,save.getCreatedTime());
        }

        if (save != null){
            List<TodoCommentDTO> CommentDTOList = todoCommentService.findAll(todoDTO,clubDTO);
            return new ResponseEntity<>(CommentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 일정이 존재하지 않습니다.",HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/update/{clubId}/{todoId}/{commentId}")
    public String updateCommentP(@PathVariable Long clubId,
                                @PathVariable Long todoId,
                                @PathVariable Long commentId, Model model){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        TodoCommentDTO todoCommentDTO = todoCommentService.findById(commentId);
        model.addAttribute("todoCommentDTO",todoCommentDTO);
        model.addAttribute("clubId",clubId);
        model.addAttribute("todoId",todoId);
        model.addAttribute("commentId",commentId);
        return "updateTodoComment";
    }

    @PostMapping("/update")
    public String updateComment(@ModelAttribute TodoCommentDTO todoCommentDTO){
        System.out.println("todoCommentDTO = " + todoCommentDTO);
        TodoCommentDTO commentDTO = todoCommentService.findById(todoCommentDTO.getId());
        System.out.println("commentDTO = " + commentDTO);
        
        //바뀐 내용 적용
        commentDTO.setContent(todoCommentDTO.getContent());

        TodoCommentDTO updatedComment = todoCommentService.update(commentDTO);
        ClubDTO clubDTO = clubService.findById(todoCommentDTO.getClubId());
        scoreClubService.saveSubmitType(commentDTO.getTodoId(), clubDTO,updatedComment.getUpdatedTime());
        return "redirect:/todo/receivedTodo/"+todoCommentDTO.getClubId()+"/"+todoCommentDTO.getTodoId();
    }
}