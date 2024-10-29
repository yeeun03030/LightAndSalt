package kr.ac.kopo.webproject.repository;

import kr.ac.kopo.webproject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
