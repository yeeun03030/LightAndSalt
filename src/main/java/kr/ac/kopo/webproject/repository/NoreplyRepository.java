package kr.ac.kopo.webproject.repository;

import kr.ac.kopo.webproject.entity.Noreply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoreplyRepository extends JpaRepository<Noreply, Long> {

    @Modifying
    @Query("delete from Noreply r where r.notice.nno = :nno") // 파라미터에서 전달받은 = ':bno'
    void deleteByNno(Long nno);
}
