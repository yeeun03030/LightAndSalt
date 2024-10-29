package kr.ac.kopo.webproject.service;

import kr.ac.kopo.webproject.dto.MemberDTO;
import kr.ac.kopo.webproject.entity.Member;
import kr.ac.kopo.webproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Member register(MemberDTO dto) {

        Member member = dtoToEntity(dto);

        Member email = memberRepository.save(member);

        return email;
    }
}
