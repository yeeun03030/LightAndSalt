package kr.ac.kopo.webproject.repository;

import kr.ac.kopo.webproject.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembersTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@kopo.ac.kr")
                    .password("1234")
                    .username("user" + i)
                    .phoneNumber("010-0000-00" + i * 10)
                    .address("서울특별시 용산구")
                    .role(0)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void insertManagersTest() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Member member = Member.builder()
                    .email("manager" + i + "@manager.kr")
                    .password("password")
                    .username("sys" + i)
                    .phoneNumber("010-0000-0000")
                    .address("")
                    .role(1)
                    .build();

            memberRepository.save(member);
        });
    }
}
