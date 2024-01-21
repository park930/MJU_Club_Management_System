package com.example.springsecurity.rental.controller;

import com.example.springsecurity.rental.dto.RentalDTO;
import com.example.springsecurity.rental.dto.RenterDTO;
import com.example.springsecurity.rental.service.RentalRenterService;
import com.example.springsecurity.rental.service.RentalService;
import com.example.springsecurity.rental.service.RenterService;
import com.example.springsecurity.user.dto.CustomUserDetails;
import com.example.springsecurity.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rental")
public class RenterController {

    private final RentalService rentalService;
    private final RenterService renterService;
    private final RentalRenterService rentalRenterService;

    @GetMapping("/offer/{id}")
    public String offerForm(@PathVariable Long id, Model model){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        RentalDTO rentalDTO = rentalService.findById(id);
        model.addAttribute("rentalDTO",rentalDTO);
        model.addAttribute("userName",userName);
        return "rentalOffer";
    }

    @PostMapping("/offer/{id}")
    public String saveOffer(@PathVariable Long id, @ModelAttribute RenterDTO renterDTO, Model model){
        RenterDTO savedRenterDTO = renterService.saveOffer(renterDTO);
        RentalDTO rentalDTO = rentalService.findById(id);
        rentalRenterService.save(rentalDTO,savedRenterDTO);
        model.addAttribute("rentalDTO",rentalDTO);
        return "redirect:/rental/";
    }


}
