package kr.ac.kopo.webproject.repository;

import kr.ac.kopo.webproject.entity.Noreply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Noreply, Long> {

    @Modifying
    @Query("delete from Reply r where r.board.bno = :bno") // 파라미터에서 전달받은 = ':bno'
    void deleteByBno(Long bno);
}
