package com.example.springsecurity.board.controller;

import com.example.springsecurity.board.dto.BoardDTO;
import com.example.springsecurity.board.dto.HeartDTO;
import com.example.springsecurity.board.service.BoardService;
import com.example.springsecurity.board.service.HeartService;
import com.example.springsecurity.user.dto.UserDTO;
import com.example.springsecurity.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/heart")
public class HeartController {
    private final HeartService heartService;
    private final BoardService boardService;

    @PostMapping("/save")
    public ResponseEntity saveHeart(@ModelAttribute HeartDTO heartDTO){
        String result = heartService.saveHeart(heartDTO);
        Long heartCount = heartService.heartCount(heartDTO.getBoardId());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("saveResult", result);
        responseMap.put("heartCount", heartCount);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @GetMapping("/{boardId}/{userName}")
    private String cancelHeart(@PathVariable Long boardId, @PathVariable String userName){
        BoardDTO boardDTO = boardService.findById(boardId);
        heartService.deleteHeart(boardDTO,userName);
        return "redirect:/";
    }
}
