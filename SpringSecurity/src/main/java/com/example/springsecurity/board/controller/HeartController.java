package com.example.springsecurity.board.controller;

import com.example.springsecurity.board.dto.HeartDTO;
import com.example.springsecurity.board.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/heart")
public class HeartController {
    private final HeartService heartService;

    @PostMapping("/save")
    public ResponseEntity saveHeart(@ModelAttribute HeartDTO heartDTO){
        String result = heartService.saveHeart(heartDTO);
        Long heartCount = heartService.heartCount(heartDTO.getBoardId());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("saveResult", result);
        responseMap.put("heartCount", heartCount);
        System.out.println("heartCount = " + heartCount);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
