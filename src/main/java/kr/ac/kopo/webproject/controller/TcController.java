package kr.ac.kopo.webproject.controller;

import kr.ac.kopo.webproject.dto.MemberDTO;
import kr.ac.kopo.webproject.dto.NoticeDTO;
import kr.ac.kopo.webproject.dto.PageRequestDTO;
import kr.ac.kopo.webproject.entity.Member;
import kr.ac.kopo.webproject.entity.Notice;
import kr.ac.kopo.webproject.service.MemberUserDetailsService;
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
    private final MemberUserDetailsService memberUserDetailsService;

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

        Member email = memberUserDetailsService.register(dto);

        redirectAttributes.addFlashAttribute("msg", email);

        return "redirect:/tc/tcmain";
    }



//    @GetMapping("/light_and_salt")
//    public void light_and_salt() {
//    }
}
