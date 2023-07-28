package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;




@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")//localhost:8080/board/write 에 접속하면 이화면을 모여주겠다는 의미
    public String boardWriteForm(){

        return "boardWrite";
    }

    @PostMapping ("/board/writepro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws  Exception{

        boardService.write(board, file);




        model.addAttribute("message", "글작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")                                       // page : 첫페이지 :size 페이지 크기 sort :정렬 direction : 정렬순서
    public String boardList(Model model, @PageableDefault(page = 0, size =10,sort = "id", direction= Sort.Direction.DESC) Pageable pageable, String searchKeyword) {

        Page<Board> list = null;

        if(searchKeyword == null){
            list = boardService.boardList(pageable);
        }else{
            list = boardService.boardSearchList(searchKeyword,pageable);
        }

//        Page<Board> list = boardService.boardList(pageable);
        //pageable.getPageNumber() pageable이 가지고 있는 페이지수는 0부터 시작하므로 +1해줘야함
        int nowPage   = pageable.getPageNumber()+1;
        //Math.max메서드는 nowPage -4 값과 1 값을 비교해서 높은 값을 보여줌
        int startPage = Math.max(nowPage -4,1);
        //Math.min메서드는 nowPage +5 값과 list.getTotalPages()을 비교해 더낮은 값을 보여줌
        int endPage   = Math.min(nowPage +5,list.getTotalPages());

//        model.addAttribute("list",boardService.boardList(pageable));
        model.addAttribute("list",list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";

    }
    @GetMapping("/board/view")//localhost:8080/board/view?id=1
    public String boardView(Model model, Integer id){

        model.addAttribute("board",boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("board/delete")
    public String boardDelete(Integer id, Model model){

        boardService.boardDelete(id);

        model.addAttribute("message", "삭제 완료하였습니다.");
        model.addAttribute("searchUrl", "/board/list");

//        return "redirect:/board/list";
        return "message";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){

        model.addAttribute("board",boardService.boardView(id));

        return  "boardmodify";
    }

    @PostMapping("/board/update/{id}")
        public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model,MultipartFile file)throws  Exception{

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        model.addAttribute("message", "수정 완료하였습니다.");
        model.addAttribute("searchUrl", "/board/list");

        boardService.write(boardTemp,file);

//        return  "redirect:/board/list";
        return "message";
    }

}
