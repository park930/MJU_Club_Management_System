package com.example.springsecurity.rental.controller;

import com.example.springsecurity.rental.dto.RentalDTO;
import com.example.springsecurity.rental.dto.RenterDTO;
import com.example.springsecurity.rental.service.RentalService;
import com.example.springsecurity.rental.service.RenterService;
import com.example.springsecurity.user.dto.UserDTO;
import com.example.springsecurity.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
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
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/offer/{id}")
    public String offerForm(@PathVariable Long id, Model model){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        RentalDTO rentalDTO = rentalService.findById(id);
        model.addAttribute("rentalDTO",rentalDTO);
        model.addAttribute("userName",userName);
        return "rentalOffer";
    }

    @PostMapping("/offer")
    public String saveOffer(@ModelAttribute RenterDTO renterDTO){
        renterService.saveOffer(renterDTO);
        return "redirect:/rental/";
    }


    @GetMapping("/offer/delete/{renterId}/{rentalId}")
    public String renterDelete(@PathVariable Long renterId, @PathVariable Long rentalId){
        renterService.delete(renterId);
        return "redirect:/rental/"+rentalId;
    }

    @GetMapping("/offer/update/{renterId}/{rentalId}")
    public String renterUpdateP(@PathVariable Long renterId, @PathVariable Long rentalId, Model model){
        RenterDTO renterDTO = renterService.findById(renterId);
        model.addAttribute("renterDTO", renterDTO);
        model.addAttribute("userDTO", renterDTO.getUserDTO());
        model.addAttribute("renterId", renterId);
        model.addAttribute("rentalId", rentalId);
        return "renterUpdate";
    }

    @PostMapping("/offer/update")
    public String renterUpdate(@ModelAttribute RenterDTO renterDTO, @RequestParam Long rentalId){
        System.out.println("renterDTO = " + renterDTO);
        System.out.println("rentalId = " + rentalId);
        renterService.update(renterDTO,rentalId);
        renterService.updateRemain(rentalService.findAll());
        return "redirect:/rental/"+rentalId;
    }


}
