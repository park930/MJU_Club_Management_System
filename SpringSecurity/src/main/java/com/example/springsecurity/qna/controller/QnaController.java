package com.example.springsecurity.qna.controller;

import com.example.springsecurity.board.dto.BoardDTO;
import com.example.springsecurity.board.dto.CommentDTO;
import com.example.springsecurity.board.service.BoardService;
import com.example.springsecurity.board.service.CommentService;
import com.example.springsecurity.board.service.HeartService;
import com.example.springsecurity.qna.dto.QnaAnswerDTO;
import com.example.springsecurity.qna.dto.QnaDTO;
import com.example.springsecurity.qna.service.QnaAnswerService;
import com.example.springsecurity.qna.service.QnaService;
import com.example.springsecurity.user.dto.CustomUserDetails;
import com.example.springsecurity.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {
    private final QnaService qnaService;
    private final QnaAnswerService qnaAnswerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/paging")
    public String qnaMainP(@PageableDefault(page=1) Pageable pageable,String searchKeyWord ,Model model){

        Page<QnaDTO> qnaList = null;
        if (searchKeyWord == null || searchKeyWord.isEmpty()) {
            qnaList = qnaService.paging(pageable);
        } else {
            qnaList = qnaService.searchPaging(pageable,searchKeyWord);
        }
        int blockLimit = 4;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit)))-1) * blockLimit + 1;   // 1,5,9,13...
        int endPage = ((startPage+blockLimit-1) < qnaList.getTotalPages())? startPage+blockLimit-1: qnaList.getTotalPages();
        System.out.println("qnaList = " + qnaList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("qnaList",qnaList);
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
        return "redirect:/qna/paging";
    }

    @GetMapping("/check/{qnaId}")
    public String qnaCheckPassword(@PathVariable Long qnaId, Model model){
        model.addAttribute("qnaId",qnaId);
        return "checkQnaSecret";
    }

    @GetMapping("/{qnaId}")
    public String qnaDetail(@PathVariable Long qnaId, Model model){
        QnaDTO qnaDTO = qnaService.findById(qnaId);
        List<QnaAnswerDTO> qnaAnswerDTOList = qnaAnswerService.findAll(qnaId);
        model.addAttribute("qnaAnswerList",qnaAnswerDTOList);
        model.addAttribute("qnaDTO",qnaDTO);
        return "qnaDetail";
    }


    @GetMapping("/admin/{qnaId}")
    public String qnaAnswerAdminP(@PathVariable Long qnaId, Model model){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        QnaDTO qnaDTO = qnaService.findById(qnaId);
        List<QnaAnswerDTO> qnaAnswerDTOList = qnaAnswerService.findAll(qnaId);
        model.addAttribute("qnaDTO",qnaDTO);
        model.addAttribute("qnaAnswerDTOList",qnaAnswerDTOList);
        model.addAttribute("userId",id);
        return "qnaAdminDetail";
    }



    @PostMapping("/checkSecret")
    public String checkSecret(@RequestParam String password, @RequestParam Long qnaId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        if (bCryptPasswordEncoder.matches(password,customUserDetails.getUserDTO().getPassword())){
            return "redirect:/qna/"+qnaId;
        } else {
            return "redirect:/qna/";
        }
    }

}
