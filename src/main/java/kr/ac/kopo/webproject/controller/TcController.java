package kr.ac.kopo.webproject.controller;

import jakarta.persistence.EntityNotFoundException;
import kr.ac.kopo.webproject.dto.BoardDTO;
import kr.ac.kopo.webproject.dto.MemberDTO;
import kr.ac.kopo.webproject.dto.NoticeDTO;
import kr.ac.kopo.webproject.dto.PageRequestDTO;
import kr.ac.kopo.webproject.entity.Member;
import kr.ac.kopo.webproject.entity.Notice;
import kr.ac.kopo.webproject.service.BoardService;
import kr.ac.kopo.webproject.service.MemberService;
import kr.ac.kopo.webproject.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.IntStream;

@Controller
@RequestMapping("/tc/")
@RequiredArgsConstructor
@Log4j2
public class TcController {
    private final BoardService boardService;
    private final NoticeService noticeService;
    private final MemberService memberService;

    @GetMapping("/")
    public String main() {
        return "redirect:/tc/tcmain";
    }

    @GetMapping("/tcmain")
    public void tcmain() {

    }

    @GetMapping("/signup")
    public void signup() {
    }

    @PostMapping("/signup")
    public String signup(MemberDTO dto, RedirectAttributes redirectAttributes) {
//        MemberDTO memberDTO = MemberDTO.builder()
//                .email(dto.getEmail())
//                .username(dto.getUsername())
//                .role(dto.getRole())
//                .password(dto.getPassword())
//                .phoneNumber(dto.getPhoneNumber())
//                .address(dto.getAddress())
//                .build();

        Member email = memberService.register(dto);

        redirectAttributes.addFlashAttribute("msg", email);

        return "redirect:/tc/tcmain";
    }

    @GetMapping("/notice")
    public void notice(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", noticeService.getList(pageRequestDTO));
    }

    @GetMapping("/notice_register")
    public void notice_register() {
    }

    @PostMapping("/notice_register")
    public String notice_register(NoticeDTO dto, RedirectAttributes redirectAttributes) {
        Long nno = noticeService.register(dto);

        if (nno == -1)
            redirectAttributes.addFlashAttribute("msg", "게시글 작성에 실패하였습니다.");
        else
            redirectAttributes.addFlashAttribute("msg", nno + "번 게시글을 작성하였습니다.");

        return "redirect:/tc/notice";
    }

    @GetMapping("/notice_read")
    public void notice_read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
        Long nno, Model model) {

        // 방문자 수 증가 메서드 호출
        noticeService.incrementVisitorCount(nno);

        NoticeDTO noticeDTO = noticeService.get(nno);

        model.addAttribute("dto", noticeDTO);
    }

    @GetMapping("/notice_modify")
    public void notice_modify(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
                            Long nno, Model model) {
        NoticeDTO noticeDTO = noticeService.get(nno);

        model.addAttribute("dto", noticeDTO);
    }

    @PostMapping("/notice_modify")
    public String notice_modify(NoticeDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                                RedirectAttributes redirectAttributes) {
        log.info(dto);
        Boolean result = noticeService.modify(dto);
        // Integer result =

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());

        if (result) {
            redirectAttributes.addAttribute("nno", dto.getNno());
            redirectAttributes.addFlashAttribute("msg", "게시글 수정에 성공하였습니다.");

            return "redirect:/tc/notice_read";
        }
        redirectAttributes.addFlashAttribute("msg", "게시글 수정에 실패하였습니다.");

        return "redirect:/tc/notice";
    }

    @PostMapping("/notice_remove")
    public String notice_remove(Long nno, RedirectAttributes redirectAttributes) {
        noticeService.removeWithReplies(nno);

        redirectAttributes.addFlashAttribute("msg", nno);

        return "redirect:/tc/notice";
    }

    @GetMapping("/meditation")
    public void meditation() {
    }

    @GetMapping("/{nno}")
    public ResponseEntity<Notice> getNoticePost(@PathVariable Long nno) {
        Notice notice = noticeService.incrementVisitorCount(nno); // 방문자 수 증가
//        Notice notice = postRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Notice Post not found"));
        return ResponseEntity.ok(notice);
    }

    @GetMapping("/light_and_salt")
    public void light_and_salt() {
    }
}
