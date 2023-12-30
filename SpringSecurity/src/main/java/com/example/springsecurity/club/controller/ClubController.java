package com.example.springsecurity.club.controller;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/club")
public class ClubController
{
    private final ClubService clubService;

    @GetMapping("/")
    public String clubForm(Model model){
        List<ClubDTO> clubList = clubService.findAll();
        model.addAttribute("clubList",clubList);
        return "clubManagement";
    }

    @GetMapping("/add")
    public String saveForm(){
        return "clubAdd";
    }

    @PostMapping("/add")
    public String addClub(@ModelAttribute ClubDTO clubDTO){
        clubService.save(clubDTO);
        return "redirect:/club/";
    }

    @GetMapping("/delete/{id}")
    public String deleteClub(@PathVariable Long id){
        clubService.deleteClub(id);
        return "redirect:/club/";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        ClubDTO clubDTO = clubService.findById(id);
        model.addAttribute("club",clubDTO);
        return "clubUpdate";
    }

    @PostMapping("/update")
    public String updateClub(@ModelAttribute ClubDTO clubDTO){
        clubService.updateClub(clubDTO);
        return "redirect:/club/";
    }

}
