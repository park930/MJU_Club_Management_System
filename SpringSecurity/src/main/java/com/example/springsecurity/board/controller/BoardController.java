package com.example.springsecurity.board.controller;

import com.example.springsecurity.board.dto.BoardDTO;
import com.example.springsecurity.board.dto.CommentDTO;
import com.example.springsecurity.board.dto.FavoriteBoardDTO;
import com.example.springsecurity.board.entity.FavoriteBoardEntity;
import com.example.springsecurity.board.service.BoardService;
import com.example.springsecurity.board.service.CommentService;
import com.example.springsecurity.board.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/board")
public class BoardController {


    private final BoardService boardService;
    private final CommentService commentService;
    private final HeartService heartService;



    @GetMapping("/save")
    public String saveForm(Model model){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("userId",id);
        return "boardSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO){
        boardService.save(boardDTO);
        return "redirect:/board/paging";
    }

    @GetMapping("/list")
    public String findAll(Model model){
        List<BoardDTO> boardList = boardService.findAll();
        System.out.println("boardList = " + boardList);
        model.addAttribute("boardList",boardList);
        return "boardList";
    }


    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        boardService.updateHits(id);
        BoardDTO boardDTO =  boardService.findById(id);
        Long heartCount = heartService.heartCount(id);
        //댓글 목록 가져오기
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList",commentDTOList);
        model.addAttribute("board",boardDTO);
        model.addAttribute("userId",userId);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("heartCount",heartCount);
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
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        model.addAttribute("userId",id);
        return "boardDetail";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board/list";
    }



    @GetMapping("/paging")
    public String paging(@PageableDefault(page=1) Pageable pageable, Model model, String searchKeyWord){

        Page<BoardDTO> boardList = null;

        if (searchKeyWord != null){
            boardList = boardService.searchPaging(searchKeyWord,pageable);
        } else {
            boardList = boardService.paging(pageable);
        }

        //하단에 3개의 페이지 번호만 보여주기 위해
        int blockLimit=3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        model.addAttribute("boardList",boardList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return "boardPaging";
    }
}
