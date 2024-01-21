package com.example.springsecurity.rental.controller;

import com.example.springsecurity.rental.dto.RentalDTO;
import com.example.springsecurity.rental.dto.RenterDTO;
import com.example.springsecurity.rental.entity.RentalRenterEntity;
import com.example.springsecurity.rental.service.RentalRenterService;
import com.example.springsecurity.rental.service.RentalService;
import com.example.springsecurity.rental.service.RenterService;
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
    private final RenterService renterService;
    private final RentalRenterService rentalRenterService;

    @GetMapping("/")
    public String rentalMain(Model model){
        List<RentalDTO> rentalDTOList = rentalRenterService.updateRemain(rentalService.findAll());
        model.addAttribute("rentalList",rentalDTOList);
        //해당 매핑이 될때매다 rentalRepository의 물품들 잔여수량 갱신 필요 --> RentalRenterRepository 이용해서
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
        List<RenterDTO> renterDTOList = rentalRenterService.findAllByRenterList(rentalDTO);
        model.addAttribute("renterList",renterDTOList);
        model.addAttribute("rentalDTO",rentalDTO);
        return "rentalDetail";
    }


}
