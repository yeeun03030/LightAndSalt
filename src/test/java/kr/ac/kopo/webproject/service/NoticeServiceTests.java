package kr.ac.kopo.webproject.service;

import kr.ac.kopo.webproject.dto.MemberDTO;
import kr.ac.kopo.webproject.dto.NoticeDTO;
import kr.ac.kopo.webproject.entity.Member;
import kr.ac.kopo.webproject.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class NoticeServiceTests {
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testRegister() {

        NoticeDTO noticeDTO = NoticeDTO.builder()
                .title("Welcome to 'Light and Salt'222")
                .content("test")
                .noticeComment(1)
                .topic("News")
                .writerEmail("manager1@manager.kr")
                .build();

        noticeService.register(noticeDTO);
    }
}
