package com.example.springsecurity.board.service;

import com.example.springsecurity.board.dto.BoardDTO;
import com.example.springsecurity.board.dto.FavoriteBoardDTO;
import com.example.springsecurity.board.entity.BoardEntity;
import com.example.springsecurity.board.entity.FavoriteBoardEntity;
import com.example.springsecurity.board.repository.BoardRepository;
import com.example.springsecurity.board.repository.FavoriteRepository;
import com.example.springsecurity.user.entity.UserEntity;
import com.example.springsecurity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    public void save(BoardDTO boardDTO) {
       boardRepository.save(BoardEntity.toBoardEntity(boardDTO));
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity boardEntity : boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }

        return boardDTOList;
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);

        if (optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {


        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardDTO.getId());

        if (optionalBoardEntity.isPresent()) {
            BoardEntity existingBoardEntity = optionalBoardEntity.get();
            existingBoardEntity.setBoardWriter(boardDTO.getBoardWriter());
            existingBoardEntity.setBoardPass(boardDTO.getBoardPass());
            existingBoardEntity.setBoardTitle(boardDTO.getBoardTitle());
            existingBoardEntity.setBoardContents(boardDTO.getBoardContents());
            boardRepository.save(existingBoardEntity);
            BoardDTO updatedBoardDTO = findById(boardDTO.getId());
            return updatedBoardDTO;
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        //실제 DB에서는 1페이지는 0으로 해야함
        int page = pageable.getPageNumber()-1;
        int pageLimit = 3;      //한페이지에 몇개씩 볼건지
        //한페이지당 3개씩 보여주고, 정렬은 id기준이며 내림차순으로 한다.
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));

        //board는 entity객체임 --> foreach문과 비슷
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getType(),board.getId(),board.getBoardWriter(), board.getBoardTitle(),board.getBoardHits(),board.getCreatedTime()));
        return boardDTOS;

    }

    public Page<BoardDTO> searchPaging(String searchKeyWord, Pageable pageable) {
        int page = pageable.getPageNumber()-1;
        int pageLimit = 3;      //한페이지에 몇개씩 볼건지
        Page<BoardEntity> boardEntities = boardRepository.findByBoardTitleContaining(searchKeyWord,PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        System.out.println("찾은 것들 = " + boardEntities);
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getType(),board.getId(),board.getBoardWriter(), board.getBoardTitle(),board.getBoardHits(),board.getCreatedTime()));
        return boardDTOS;
    }

    public String favorite(FavoriteBoardDTO favoriteBoardDTO) {
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

    public List<BoardDTO> findNotice() {
        List<BoardEntity> boardEntityList = boardRepository.findAllByTypeOrderByCreatedTimeDesc("notice");
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity boardEntity : boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }
}
