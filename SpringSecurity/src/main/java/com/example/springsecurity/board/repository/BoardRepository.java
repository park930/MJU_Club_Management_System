package com.example.springsecurity.board.repository;

import com.example.springsecurity.board.dto.BoardDTO;
import com.example.springsecurity.board.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {

    //Entity기준의 쿼리
    @Modifying
    @Query(value="update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);


    Page<BoardEntity> findByBoardTitleContaining(String searchKeyWord, Pageable pageable);

    List<BoardEntity> findAllByTypeOrderByCreatedTimeDesc(String type);


}
