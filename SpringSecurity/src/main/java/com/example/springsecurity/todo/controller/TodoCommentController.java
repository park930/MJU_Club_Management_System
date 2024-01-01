package com.example.springsecurity.todo.controller;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.todo.dto.TodoCommentDTO;
import com.example.springsecurity.todo.dto.TodoDTO;
import com.example.springsecurity.todo.service.TodoCommentService;
import com.example.springsecurity.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/receivedTodoComment")
public class TodoCommentController {

    private final TodoService todoService;
    private final TodoCommentService todoCommentService;
    private final ClubService clubService;

    @PostMapping("/save")
    public ResponseEntity saveTodoComment(@ModelAttribute TodoCommentDTO todoCommentDTO, Model model){
        System.out.println("초기 결과물 제출 여부는? = " + todoCommentDTO.getResultSubmit());
        TodoDTO todoDTO = todoService.findById(todoCommentDTO.getTodoId());
        ClubDTO clubDTO = clubService.findById(todoCommentDTO.getClubId());
        Long result = todoCommentService.save(todoCommentDTO,todoDTO,clubDTO);
        if (result != null){
            List<TodoCommentDTO> CommentDTOList = todoCommentService.findAll(todoDTO,clubDTO);
            return new ResponseEntity<>(CommentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 일정이 존재하지 않습니다.",HttpStatus.NOT_FOUND);
        }

    }
}
