package com.example.springsecurity.rental.controller;

import com.example.springsecurity.rental.dto.RentalDTO;
import com.example.springsecurity.rental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rental")
public class RentalController {

    private final RentalService rentalService;

    @GetMapping("/")
    public String rentalMain(Model model){
        List<RentalDTO> rentalDTOList = rentalService.findAll();
        model.addAttribute("rentalList",rentalDTOList);
        return "rental";
    }

    @GetMapping("/add")
    public String addForm(){
        return "rentalAdd";
    }

    @PostMapping("/add")
    public String saveRental(@ModelAttribute RentalDTO rentalDTO){
        rentalDTO.setRemain(rentalDTO.getTotalCount());
        rentalService.save(rentalDTO);
        return "redirect:/rental/";
    }

}
