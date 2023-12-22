package com.example.springsecurity.board.service;

import com.example.springsecurity.board.dto.FavoriteBoardDTO;
import com.example.springsecurity.board.entity.BoardEntity;
import com.example.springsecurity.board.entity.FavoriteBoardEntity;
import com.example.springsecurity.board.repository.BoardRepository;
import com.example.springsecurity.board.repository.FavoriteRepository;
import com.example.springsecurity.entity.UserEntity;
import com.example.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;

    public String favoriteSave(FavoriteBoardDTO favoriteBoardDTO) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(favoriteBoardDTO.getBoardId());
        UserEntity userEntity = userRepository.findByUsername(favoriteBoardDTO.getUserId());

        if (optionalBoardEntity.isPresent() && userEntity!=null){
            BoardEntity boardEntity = optionalBoardEntity.get();
            FavoriteBoardEntity favoriteBoardEntity = FavoriteBoardEntity.toFavoriteEntity(favoriteBoardDTO,boardEntity,userEntity);
            favoriteRepository.save(favoriteBoardEntity);
            return "등록";
        } else {
            return "등록 실패";
        }

    }

    public List<FavoriteBoardDTO> findAll(UserEntity userEntity, String userName) {
        List<FavoriteBoardEntity> favoriteList = favoriteRepository.findAllByUserEntityOrderByIdDesc(userEntity);
        List<FavoriteBoardDTO> favoriteBoardDTOList = new ArrayList<>();
        for(FavoriteBoardEntity favoriteBoardEntity : favoriteList){
            favoriteBoardDTOList.add(FavoriteBoardDTO.toFavoriteDTO(favoriteBoardEntity,userName));
        }
        return favoriteBoardDTOList;
    }
}
