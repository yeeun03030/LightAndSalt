package kr.ac.kopo.webproject.service;

import jakarta.persistence.EntityNotFoundException;
import kr.ac.kopo.webproject.dto.NoticeDTO;
import kr.ac.kopo.webproject.dto.PageRequestDTO;
import kr.ac.kopo.webproject.dto.PageResultDTO;
import kr.ac.kopo.webproject.entity.Board;
import kr.ac.kopo.webproject.entity.Member;
import kr.ac.kopo.webproject.entity.Notice;
import kr.ac.kopo.webproject.repository.MemberRepository;
import kr.ac.kopo.webproject.repository.NoreplyRepository;
import kr.ac.kopo.webproject.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository repository;
    private final NoreplyRepository replyRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long register(NoticeDTO dto) {
        Notice notice = noticeDtoToEntity(dto);
        Optional<Member> member = memberRepository.findById(notice.getWriter().getEmail());

        if (member.get().getRole() == 1) {
            repository.save(notice);

            return notice.getNno();
        }

        return -1L;
    }

    @Override
    public PageResultDTO<NoticeDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Function<Object[], NoticeDTO> fn = (en ->
                noticeEntityToDto((Notice) en[0], (Member)en[1], (Long)en[2])); // <처리될 매개변수의 전달될 값, 반환될 자료형>

//        Page<Object[]> result = repository.getNoticeWithNoreplyCount(
//                requestDTO.getPageable(Sort.by("nno").descending())
//        );

        Page<Object[]> result = repository.searchPage(
                requestDTO.getType(),
                requestDTO.getKeyword(),
                requestDTO.getPageable(Sort.by("nno").descending()));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public NoticeDTO get(Long nno) {
        Object result = repository.getNoticeByNno(nno);

        Object[] arr = (Object[]) result;
        NoticeDTO noticeDTO = noticeEntityToDto((Notice) arr[0], (Member) arr[1], (Long) arr[2]);

        return noticeDTO;
    }

    @Transactional
    @Override
    public Boolean modify(NoticeDTO noticeDTO) {
        Notice notice = repository.getReferenceById(noticeDTO.getNno()); // 지역 로딩??

        Notice result = noticeDtoToEntity(noticeDTO);
        log.info("notice: " + notice.getWriter().getPassword());
        log.info("result: " + result.getWriter().getPassword());

        if (notice.getWriter().getPassword().equals(result.getWriter().getPassword())) {
            notice.changeTitle(noticeDTO.getTitle());
            notice.changeContent(noticeDTO.getContent());

            repository.save(notice);

            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Transactional
    @Override
    public void removeWithReplies(Long nno) {
        replyRepository.deleteByNno(nno);
        repository.deleteById(nno);
    }

    @Override
    public Notice incrementVisitorCount(Long nno) {
        Notice notice = repository.findById(nno)
                .orElseThrow(() -> new EntityNotFoundException("Nno Post not found"));

        int currentCount = (notice.getVisitorCount() != null) ? notice.getVisitorCount() : 0;
        notice.setVisitorCount(currentCount + 1);

//        notice.setVisitorCount(notice.getVisitorCount() + 1);
        Notice result = repository.save(notice);

        return result;
    }
}
