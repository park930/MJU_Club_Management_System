package com.example.springsecurity.board.controller;

import java.util.List;
import com.example.springsecurity.board.dto.FavoriteBoardDTO;
import com.example.springsecurity.board.service.BoardService;
import com.example.springsecurity.board.service.FavoriteService;
import com.example.springsecurity.entity.UserEntity;
import com.example.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserRepository userRepository;

    @PostMapping("/save")
    public ResponseEntity favorite(@ModelAttribute FavoriteBoardDTO favoriteBoardDTO){
        String result = "즐겨찾기가 "+favoriteService.favoriteSave(favoriteBoardDTO)+"되었습니다.";

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/list")
    public String favoriteList(Model model){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findByUsername(userName);
        List<FavoriteBoardDTO> favoriteBoardDTOList = favoriteService.findAll(userEntity, userName);
        model.addAttribute("favoriteBoardDTOList",favoriteBoardDTOList);
        return "favoriteList";
    }
}
