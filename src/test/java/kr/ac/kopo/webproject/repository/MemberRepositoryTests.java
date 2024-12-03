package kr.ac.kopo.webproject.repository;
//
//import kr.ac.kopo.webproject.entity.Member;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.stream.IntStream;
//
//@SpringBootTest
//public class MemberRepositoryTests {
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Test
//    public void insertMembersTest() {
//        IntStream.rangeClosed(1, 10).forEach(i -> {
//            Member member = Member.builder()
//                    .email("user" + i + "@kopo.ac.kr")
//                    .password("1234")
//                    .username("user" + i)
//                    .phoneNumber("010-0000-00" + i * 10)
//                    .address("서울특별시 용산구")
//                    .role(0)
//                    .build();
//
//            memberRepository.save(member);
//        });
//    }
//
//    @Test
//    public void insertManagersTest() {
//        IntStream.rangeClosed(1, 5).forEach(i -> {
//            Member member = Member.builder()
//                    .email("manager" + i + "@manager.kr")
//                    .password("password")
//                    .username("sys" + i)
//                    .phoneNumber("010-0000-0000")
//                    .address("")
//                    .role(1)
//                    .build();
//
//            memberRepository.save(member);
//        });
//    }
//}


import kr.ac.kopo.webproject.entity.Member;
import kr.ac.kopo.webproject.entity.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies() {
    //        user1-user80: USER
    //        user810user90: USER, MANAGER
    //        user91-user100: USER, MANAGER, ADMIN

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@kopo.ac.kr")
                    .username("사용자" + i)
                    .password(passwordEncoder.encode("1234"))
                    .fromSocial(false)
                    .address("서울특별시 용산구")
                    .build();

            member.addMemberRole(MemberRole.USER);

            if (i > 80) {
                member.addMemberRole(MemberRole.MANAGER);
            }

            if (i > 90) {
                member.addMemberRole(MemberRole.ADMIN);
            }

            repository.save(member);
        });
    }

    @Test
    public void testRead() {
        Optional<Member> result = repository.findByEmail("user99@kopo.ac.kr", false);
        Member member = result.get();
        System.out.println("☆★☆★☆" + member);
    }
}