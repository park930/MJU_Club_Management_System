package com.example.springsecurity.user.controller;


import com.example.springsecurity.board.dto.BoardDTO;
import com.example.springsecurity.board.dto.FavoriteBoardDTO;
import com.example.springsecurity.board.dto.HeartDTO;
import com.example.springsecurity.board.service.BoardService;
import com.example.springsecurity.board.service.FavoriteService;
import com.example.springsecurity.board.service.HeartService;
import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.dto.ClubFeeDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.service.ClubFeeService;
import com.example.springsecurity.club.service.ClubService;
import com.example.springsecurity.user.dto.CustomUserDetails;
import com.example.springsecurity.score.dto.ClubRatingDTO;
import com.example.springsecurity.score.dto.ScoreClubDTO;
import com.example.springsecurity.score.dto.ScoreDTO;
import com.example.springsecurity.score.service.ScoreClubService;
import com.example.springsecurity.score.service.ScoreService;
import com.example.springsecurity.todo.dto.TodoPersonalDTO;
import com.example.springsecurity.todo.service.TodoService;
import com.example.springsecurity.user.dto.UserDTO;
import com.example.springsecurity.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
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
    private final ClubFeeService clubFeeService;
    private final HeartService heartService;

    @GetMapping("/")
    public String mainP(Model model){

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        if (id.equals("anonymousUser")){
            return "main_anonymous";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TodoPersonalDTO todoPersonalDTO;


        List<ScoreClubDTO> scoreClubDTOList = scoreClubService.findAll();
        List<ScoreDTO> scoreDTOList = scoreService.findAll();
        List<BoardDTO> boardNoticeList = boardService.findNotice();

        List<ClubDTO> clubDTOList = clubService.findAll();
        List<ScoreDTO> updateScoreList = scoreService.getScoreInfo(scoreClubDTOList,scoreDTOList,clubDTOList);
        List<Integer> totalScoreList = scoreService.getTotalScore(updateScoreList);
        List<String> headText = scoreService.setHeadText(updateScoreList);

        ClubRatingDTO clubRatingDTO = scoreService.sortScore(headText,totalScoreList,updateScoreList,clubDTOList);

        Long clubId = 0L;
        int clubScore = 0;
        double percentScore = 0.0;

        if (!id.equals("anonymousUser")){
            //받은 일정 리스트를 가져와야함
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            clubId = customUserDetails.getClubId();
            ClubDTO clubDTO = clubService.findById(clubId);
            List<UserDTO> chairManList = clubService.findChairManList(clubDTO);
            todoPersonalDTO = todoService.getFilteredTodoList(clubDTO,id);
            UserDTO userDTO = customUserDetails.getUserDTO();
            List<FavoriteBoardDTO> favoriteBoardDTOList = favoriteService.findAll(userDTO, clubDTO);

            clubScore = scoreService.getMyClubScore(clubRatingDTO,clubDTO);
            percentScore = clubScore/80.0 * 100;

            List<ClubFeeDTO> clubFeeList = clubFeeService.findAllByClubDTO(clubDTO);
            if (clubFeeList.isEmpty()){
                model.addAttribute("totalDepositAmount",0);
                model.addAttribute("totalSpentAmount",0);
                model.addAttribute("feePercent",0);
            } else {
                ClubFeeDTO lastClubFee = clubFeeList.get(0);
                double feePercent =  (lastClubFee.getTotalMinusFee()) / (1.0*(lastClubFee.getTotalPlusFee())) * 100;
                model.addAttribute("totalDepositAmount",lastClubFee.getTotalPlusFee());
                model.addAttribute("totalSpentAmount",lastClubFee.getTotalMinusFee());
                model.addAttribute("feePercent",feePercent);
            }

            List<BoardDTO> heartBoardList = heartService.findAllByUserId(userDTO.getId());

            model.addAttribute("heartBoardList",heartBoardList);
            model.addAttribute("clubId",clubId);
            model.addAttribute("clubDTO",clubDTO);
            model.addAttribute("chairManList",chairManList);

            model.addAttribute("chairManList",chairManList);
            model.addAttribute("favoriteBoardDTOList",favoriteBoardDTOList);
            model.addAttribute("myTodoList",todoPersonalDTO.getMyTodoDTOList());
            model.addAttribute("receiveTodoList",todoPersonalDTO.getReceviedIncompleteList());
            model.addAttribute("clubScore",clubScore);
        }

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
}
