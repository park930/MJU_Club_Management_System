package com.example.springsecurity.qna.controller;

import com.example.springsecurity.board.dto.BoardDTO;
import com.example.springsecurity.board.dto.CommentDTO;
import com.example.springsecurity.board.service.BoardService;
import com.example.springsecurity.board.service.CommentService;
import com.example.springsecurity.board.service.HeartService;
import com.example.springsecurity.qna.dto.QnaDTO;
import com.example.springsecurity.qna.service.QnaService;
import com.example.springsecurity.user.dto.CustomUserDetails;
import com.example.springsecurity.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {
    private final QnaService qnaService;

    @GetMapping("/")
    public String qnaMainP(Model model){
        List<QnaDTO> qnaDTOList = qnaService.findAll();
        model.addAttribute("qnaList",qnaDTOList);
        return "qnaMain";
    }

    @GetMapping("/save")
    public String saveP(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        UserDTO userDTO = customUserDetails.getUserDTO();

        model.addAttribute("userId",userDTO.getId());
        model.addAttribute("userName",userDTO.getUsername());
        return "qnaAdd";
    }

    @PostMapping("/save")
    public String saveProc(@ModelAttribute QnaDTO qnaDTO){
        System.out.println("qnaDTO = " + qnaDTO);
        qnaService.save(qnaDTO);
        return "redirect:/qna/";
    }

    @GetMapping("/{qnaId}")
    public String qnaDetail(@PathVariable Long qnaId, Model model){
        QnaDTO qnaDTO = qnaService.findById(qnaId);
        System.out.println("qnaDTO = " + qnaDTO);
        model.addAttribute("qnaDTO",qnaDTO);
        return "qnaDetail";
    }

}
