package kr.ac.kopo.webproject.service;

import kr.ac.kopo.webproject.dto.BoardDTO;
import kr.ac.kopo.webproject.dto.NoticeDTO;
import kr.ac.kopo.webproject.dto.PageRequestDTO;
import kr.ac.kopo.webproject.dto.PageResultDTO;
import kr.ac.kopo.webproject.entity.Member;
import kr.ac.kopo.webproject.entity.Notice;

public interface NoticeService {
    Long register(NoticeDTO dto);

    PageResultDTO<NoticeDTO, Object[]> getList(PageRequestDTO requestDTO);

    NoticeDTO get(Long nno);

    Boolean modify(NoticeDTO noticeDTO);

    void removeWithReplies(Long nno);

    Notice incrementVisitorCount(Long nno);

    default NoticeDTO noticeEntityToDto(Notice notice, Member member, Long replyCount) {
        NoticeDTO noticeDTO = NoticeDTO.builder()
                .nno(notice.getNno())
                .title(notice.getTitle())
                .content(notice.getContent())
                .regDate(notice.getRegDate())
                .modDate(notice.getModDate())
                .noticeComment(notice.getNoticeComment())
                .topic(notice.getTopic())
                .writerEmail(member.getEmail())
                .writerName(member.getUsername())
                .writerRole(member.getRole())
                .writerPassword(member.getPassword())
                .replyCount(replyCount.intValue())
                .visitorCount(notice.getVisitorCount())
                .build();

        return noticeDTO;
    }

    default Notice noticeDtoToEntity(NoticeDTO dto) {
        Member member = Member.builder()
                .email(dto.getWriterEmail())
                .username(dto.getWriterName())
                .role(dto.getWriterRole())
                .password(dto.getWriterPassword())
                .build();

        Notice notice = Notice.builder()
                .nno(dto.getNno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .topic(dto.getTopic())
                .noticeComment(dto.getNoticeComment())
                .build();

        return notice;
    }
}
