//package kr.ac.kopo.webproject.service;
//
//import kr.ac.kopo.webproject.dto.NoreplyDTO;
//import kr.ac.kopo.webproject.entity.Noreply;
//import kr.ac.kopo.webproject.entity.Notice;
//import kr.ac.kopo.webproject.repository.NoreplyRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class NoreplyServiceImpl implements NoreplyService {
//    private final NoreplyRepository noreplyRepository;
//
//    @Override
//    public Long register(NoreplyDTO noreplyDTO) {
//        Noreply noreply = dtoToEntity(noreplyDTO);
//        noreplyRepository.save(noreply);
//
//        return noreply.getRno();
//    }
//
//    @Override
//    public void modify(NoreplyDTO noreplyDTO) {
//        Noreply noreply = dtoToEntity(noreplyDTO);
//        noreplyRepository.save(noreply);
//    }
//
//    @Override
//    public void remove(Long rno) {
//        noreplyRepository.deleteById(rno);
//    }
//
//    @Override
//    public List<NoreplyDTO> getList(Long nno) {
//        List<Noreply> noreplyList = noreplyRepository.getNorepliesByNoticeOrderByRno(Notice.builder()
//                .nno(nno)
//                .build());
//
//        List<NoreplyDTO> noreplyDTOList = noreplyList.stream().map(noreply ->
//                entityToDto(noreply)).collect(Collectors.toList());
//
//        return noreplyDTOList;
//    }
//}
