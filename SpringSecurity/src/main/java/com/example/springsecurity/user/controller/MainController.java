package com.example.springsecurity.user.controller;


import com.example.springsecurity.board.dto.BoardDTO;
import com.example.springsecurity.board.dto.FavoriteBoardDTO;
import com.example.springsecurity.board.service.BoardService;
import com.example.springsecurity.board.service.FavoriteService;
import com.example.springsecurity.board.service.HeartService;
import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.dto.ClubFeeDTO;
import com.example.springsecurity.club.service.ClubFeeService;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.qna.dto.QnaDTO;
import com.example.springsecurity.qna.service.QnaService;
import com.example.springsecurity.rental.dto.RentalDTO;
import com.example.springsecurity.rental.service.RentalService;
import com.example.springsecurity.rental.service.RenterService;
import com.example.springsecurity.user.dto.CustomUserDetails;
import com.example.springsecurity.score.dto.ClubRatingDTO;
import com.example.springsecurity.score.dto.ScoreDTO;
import com.example.springsecurity.score.service.ScoreClubService;
import com.example.springsecurity.score.service.ScoreService;
import com.example.springsecurity.todo.dto.TodoPersonalDTO;
import com.example.springsecurity.todo.service.TodoService;
import com.example.springsecurity.user.dto.UserDTO;
import com.example.springsecurity.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final TodoService todoService;
    private final ClubService clubService;
    private final ScoreClubService scoreClubService;
    private final ScoreService scoreService;
    private final FavoriteService favoriteService;
    private final BoardService boardService;
    private final RenterService renterService;
    private final QnaService qnaService;
    private final ClubFeeService clubFeeService;
    private final RentalService rentalService;
    private final HeartService heartService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/")
    public String mainP(Model model){

        // 비회원인 경우
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        if (id.equals("anonymousUser")){
            return "main_anonymous";
        }

        if (id.startsWith("admin")){
            return "redirect:/adminMain";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TodoPersonalDTO todoPersonalDTO = null;

        List<BoardDTO> boardNoticeList = boardService.findNotice();

        List<ClubDTO> clubDTOList = clubService.findAll();
        List<ScoreDTO> updateScoreList = scoreService.getScoreInfo(scoreClubService.findAll(),scoreService.findAll(),clubDTOList);
        List<Integer> totalScoreList = scoreService.getTotalScore(updateScoreList);
        List<String> headText = scoreService.setHeadText(updateScoreList);

        ClubRatingDTO clubRatingDTO = scoreService.sortScore(headText,totalScoreList,updateScoreList,clubDTOList);

        Long clubId = 0L;
        int clubScore = 0;
        double percentScore = 0.0;

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        clubId = customUserDetails.getClubId();
        ClubDTO clubDTO = clubService.findById(clubId);
        List<UserDTO> chairManList = clubService.findChairManList(clubDTO);
        todoPersonalDTO = todoService.getFilteredTodoList(clubDTO, id);
        UserDTO userDTO = customUserDetails.getUserDTO();
        List<FavoriteBoardDTO> favoriteBoardDTOList = favoriteService.findAll(userDTO, clubDTO);

        clubScore = scoreService.getMyClubScore(clubRatingDTO, clubDTO);
        percentScore = Double.parseDouble(String.format("%.2f", clubScore / 80.0 * 100));

        List<ClubFeeDTO> clubFeeList = clubFeeService.findAllByClubDTO(clubDTO);
        if (clubFeeList.isEmpty()) {
            model.addAttribute("totalDepositAmount", 0);
            model.addAttribute("totalSpentAmount", 0);
            model.addAttribute("feePercent", 0);
        } else {
            ClubFeeDTO lastClubFee = clubFeeList.get(0);
            double feePercent = Double.parseDouble(String.format("%.2f", (lastClubFee.getTotalMinusFee()) / (1.0 * (lastClubFee.getTotalPlusFee())) * 100));
            model.addAttribute("totalDepositAmount", lastClubFee.getTotalPlusFee());
            model.addAttribute("totalSpentAmount", lastClubFee.getTotalMinusFee());
            model.addAttribute("feePercent", feePercent);
        }

        List<BoardDTO> heartBoardList = heartService.findAllByUserId(userDTO.getId());
        List<RentalDTO> myRentalDTOList = renterService.findAllByUserName(id);
        List<QnaDTO> myQnaList = qnaService.findAllByUserName(id);
        List<UserDTO> adminCharManList = customUserDetailsService.findAllAdminChairMan();

        model.addAttribute("myQnaList", myQnaList);
        model.addAttribute("heartBoardList", heartBoardList);
        model.addAttribute("myRentalList", myRentalDTOList);
        model.addAttribute("clubId", clubId);
        model.addAttribute("clubDTO", clubDTO);
        model.addAttribute("chairManList", chairManList);
        model.addAttribute("adminChairManList",adminCharManList);

        model.addAttribute("chairManList", chairManList);
        model.addAttribute("favoriteBoardDTOList", favoriteBoardDTOList);
        model.addAttribute("myTodoList", todoPersonalDTO.getMyTodoDTOList());

        model.addAttribute("receiveTodoList", todoPersonalDTO.getReceviedIncompleteList());
        model.addAttribute("clubScore", clubScore);


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        model.addAttribute("id",id);
        model.addAttribute("role",role);
        model.addAttribute("clubScore",clubScore);
        model.addAttribute("percentScore",percentScore);
        model.addAttribute("noticeList",boardNoticeList);
        return "main";
    }

    @GetMapping("/adminMain")
    public String adminMain(Model model) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO userDTO = customUserDetailsService.findByUserName(id);
        List<ScoreDTO> scoreDTOList = scoreService.findAll();
        List<QnaDTO> noAnswerList = qnaService.findNoAnswerAll();
        List<BoardDTO> boardNoticeList = boardService.findNotice();
        List<ClubDTO> clubDTOList = customUserDetailsService.setClubMemberList(clubService.findAll());
        List<RentalDTO> rentalDTOList = rentalService.findAllRenterCount();

        model.addAttribute("userDTO",userDTO);
        model.addAttribute("scoreList",scoreDTOList);
        model.addAttribute("noticeList",boardNoticeList);
        model.addAttribute("clubList",clubDTOList);
        model.addAttribute("noAnswerList",noAnswerList);
        model.addAttribute("rentalList",rentalDTOList);
        return "admin";
    }
}
