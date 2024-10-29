package kr.ac.kopo.webproject.service;

import kr.ac.kopo.webproject.dto.BoardDTO;
import kr.ac.kopo.webproject.dto.PageRequestDTO;
import kr.ac.kopo.webproject.dto.PageResultDTO;
import kr.ac.kopo.webproject.entity.Board;
import kr.ac.kopo.webproject.entity.Member;

public interface BoardService {
    Long register(BoardDTO dto);

    // 게시 목록을 반환하는 처리 기능
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    // 특정 게시글을 조회하는 기능 (한 개)
    BoardDTO get(Long bno);

    // Board 삭제 기능
    void removeWithReplies(Long bno);

    // 수정 기능
    void modify(BoardDTO boardDTO);

    default BoardDTO boardEntityToDto(Board board, Member member, Long replyCount) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getUsername())
                .replyCount(replyCount.intValue())
                .build();

        return boardDTO;
    }

    default Board boardDtoToEntity(BoardDTO dto) {
        Member member = Member.builder()
                .email(dto.getWriterEmail())
                .build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .writer(member)
                .content(dto.getContent())
                .build();

        return board;
    }
}
