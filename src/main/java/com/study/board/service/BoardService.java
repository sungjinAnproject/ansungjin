package com.study.board.service;


import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;


    //글작성
    public void write(Board board, MultipartFile file) throws Exception {

        //프로젝트 경로를 projectPath에 담아줌
        String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files";

        //식별자 => 랜덤이름을 지정해주는 기능
        UUID uuid = UUID.randomUUID();
        //랜덤으로 이름지어주는 식별자에다가 _이 붙고 파일이름이 붙음
        String fileName = uuid + "_" + file.getOriginalFilename();
        //projectPath이 경로 "name"에 파일 이름이 담김
        File saveFile = new File(projectPath, fileName);
        //projectPath 경로를 지정하고 File 클래스를 이용해서 빈껍떼기를 생성하는데 경로지정하고 파일이름을 지정해줌
        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/"+fileName);

        boardRepository.save(board);

    }

    //게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    //특정 게스글 불러오기
    public Board boardView(Integer id){
        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제
    public void boardDelete(Integer id){

        boardRepository.deleteById(id);
    }
}
