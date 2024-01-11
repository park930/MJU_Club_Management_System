package com.example.springsecurity.board.service;

import com.example.springsecurity.board.dto.FavoriteBoardDTO;
import com.example.springsecurity.board.entity.BoardEntity;
import com.example.springsecurity.board.entity.FavoriteBoardEntity;
import com.example.springsecurity.board.repository.BoardRepository;
import com.example.springsecurity.board.repository.FavoriteRepository;
import com.example.springsecurity.user.entity.UserEntity;
import com.example.springsecurity.user.repository.UserRepository;
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
        //게시판 Id를 통해, 추출한 BoardEntity
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(favoriteBoardDTO.getBoardId());
        //UserId를 통해, 추출한 UserEntity
        UserEntity userEntity = userRepository.findByUsername(favoriteBoardDTO.getUserId());

        //게시글이 존재하고, 유저 또한 null이 아니면
        if (optionalBoardEntity.isPresent() && userEntity!=null){
            BoardEntity boardEntity = optionalBoardEntity.get();
            FavoriteBoardEntity newFavoriteEntity = FavoriteBoardEntity.toFavoriteEntity(favoriteBoardDTO,boardEntity,userEntity);

            FavoriteBoardEntity findFavorite = favoriteRepository.findByBoardEntityAndUserEntity(boardEntity,userEntity);

            //만약 해당 즐겨찾기가 이미 존재하면 삭제, 아니면 저장
            if ( findFavorite!= null){
                favoriteRepository.delete(findFavorite);
                return "해제";
            }
            else {
                favoriteRepository.save(newFavoriteEntity);
                return "등록";
            }
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
