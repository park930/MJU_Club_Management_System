package com.example.springsecurity.qna.controller;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qnaAnswer")
public class QnaAnswerController {
    private final QnaAnswerService qnaAnswerService;
    private final QnaService qnaService;

    @PostMapping("/save")
    public ResponseEntity answerSave(@ModelAttribute QnaAnswerDTO qnaAnswerDTO){
        System.out.println("qnaAnswerDTO = " + qnaAnswerDTO);
        Long save = qnaAnswerService.save(qnaAnswerDTO);
        if (save != null){
            //해당 qna의 answer을 1로 바꿔야함
            qnaService.setAnswer(qnaAnswerDTO.getQnaId());

            List<QnaAnswerDTO> qnaAnswerDTOList = qnaAnswerService.findAll(qnaAnswerDTO.getQnaId());
            System.out.println("qnaAnswerDTOList = " + qnaAnswerDTOList);
            return new ResponseEntity<>(qnaAnswerDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시물이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update")
    public String updateAnswer(@ModelAttribute QnaAnswerDTO qnaAnswerDTO,
                               @RequestParam Long qnaId){
        System.out.println("qnaAnswerDTO = " + qnaAnswerDTO);
        qnaAnswerService.updateAnswer(qnaAnswerDTO);
        return "redirect:/qna/admin/"+qnaId;
    }


}
