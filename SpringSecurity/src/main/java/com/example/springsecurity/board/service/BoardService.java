package com.example.springsecurity.board.service;

import com.example.springsecurity.board.dto.BoardDTO;
import com.example.springsecurity.board.entity.BaseEntity;
import com.example.springsecurity.board.entity.BoardEntity;
import com.example.springsecurity.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
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

}
