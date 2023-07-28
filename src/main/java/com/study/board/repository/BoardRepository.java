package com.study.board.repository;

import com.study.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository <Board, Integer>{

    //findBy(컬럼이름 : Title)Containing 키워드가 포함된 모든 데이터 검색
    //예) '한코딩'을 검색하고 싶을때 '한'만 입력해도 됨
    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);

}
