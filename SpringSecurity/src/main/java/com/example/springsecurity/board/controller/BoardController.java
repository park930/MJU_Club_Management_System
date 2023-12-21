package com.example.springsecurity.board.controller;

import com.example.springsecurity.board.dto.BoardDTO;
import com.example.springsecurity.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {


    private final BoardService boardService;

    @GetMapping("/save")
    public String saveForm(){
        return "boardSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO){
        boardService.save(boardDTO);
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String findAll(Model model){
        List<BoardDTO> boardList = boardService.findAll();
        System.out.println("boardList = " + boardList);
        model.addAttribute("boardList",boardList);
        return "boardList";
    }


    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        boardService.updateHits(id);
        BoardDTO boardDTO =  boardService.findById(id);
        System.out.println("boardDTO.getBoardCreatedTime() = " + boardDTO.getBoardCreatedTime());
        model.addAttribute("board",boardDTO);
        return "boardDetail";
    }


    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate",boardDTO);
        return "BoardUpdate";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "boardDetail";
    }
}
