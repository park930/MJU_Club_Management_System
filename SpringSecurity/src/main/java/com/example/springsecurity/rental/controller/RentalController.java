package com.example.springsecurity.rental.controller;

import com.example.springsecurity.rental.dto.RentalDTO;
import com.example.springsecurity.rental.dto.RenterDTO;
import com.example.springsecurity.rental.service.RentalService;
import com.example.springsecurity.rental.service.RenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rental")
public class RentalController {

    private final RentalService rentalService;
    private final RenterService renterService;

    @GetMapping("/")
    public String rentalMain(Model model){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        List<RentalDTO> rentalDTOList = renterService.updateRemain(rentalService.findAll());
        List<RentalDTO> myRentalDTOList = renterService.findAllByUserName(id);

        model.addAttribute("rentalList",rentalDTOList);
        model.addAttribute("userId",id);
        model.addAttribute("myRentalList",myRentalDTOList);
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

    @GetMapping("/{id}")
    public String renterDetail(@PathVariable Long id, Model model){
        RentalDTO rentalDTO = rentalService.findById(id);
        List<RenterDTO> renterDTOList = renterService.findAllByRental(rentalDTO);
        model.addAttribute("renterList",renterDTOList);
        model.addAttribute("rentalDTO",rentalDTO);
        return "rentalDetail";
    }

}
