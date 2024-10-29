//package kr.ac.kopo.webproject.service;
//
//import kr.ac.kopo.webproject.dto.NoreplyDTO;
//import kr.ac.kopo.webproject.entity.Noreply;
//import kr.ac.kopo.webproject.entity.Notice;
//
//import java.util.List;
//
//public interface NoreplyService {
//    // 댓글 등록 메소드
//    Long register(NoreplyDTO noreplyDTO);
//
//    // 댓글 수정 메소드
//    void modify(NoreplyDTO noreplyDTO);
//
//    // 댓글 삭제 메소드
//    void remove(Long rno);
//
//    // 댓글 목록 반환
//    List<NoreplyDTO> getList(Long nno);
//
//    default Noreply dtoToEntity(NoreplyDTO noreplyDTO) {
//        Notice notice = Notice.builder()
//                .nno(noreplyDTO.getNno())
//                .build();
//
//        Noreply noreply = Noreply.builder()
//                .rno(noreplyDTO.getRno())
//                .text(noreplyDTO.getText())
//                .replyer(noreplyDTO.getReplyer())
//                .notice(notice)
//                .build();
//
//        return noreply;
//    }
//
//    default NoreplyDTO entityToDto(Noreply noreply) {
//        NoreplyDTO noreplyDTO = NoreplyDTO.builder()
//                .rno(noreply.getRno())
//                .text(noreply.getText())
//                .replyer(noreply.getReplyer())
//                .regDate(noreply.getRegDate())
//                .modDate(noreply.getModDate())
//                .nno(noreply.getNotice().getNno())
//                .build();
//
//        return noreplyDTO;
//    }
//
//}
