package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")//localhost:8080/board/write 에 접속하면 이화면을 모여주겠다는 의미
    public String boardWriteForm(){

        return "boardWrite";
    }

    @PostMapping ("/board/writepro")
    public String boardWritePro(Board board){

        boardService.write(board);

        return "";
    }

    @GetMapping("/board/list")
    public String boardList(Model model ) {

        model.addAttribute("list",boardService.boardList());

        return "boardlist";

    }
    @GetMapping("/board/view")
    public String boardView(){

        return "boardview";
    }
}
