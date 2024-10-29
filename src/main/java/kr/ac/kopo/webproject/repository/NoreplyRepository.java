package kr.ac.kopo.webproject.repository;

import kr.ac.kopo.webproject.entity.Noreply;
import kr.ac.kopo.webproject.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoreplyRepository extends JpaRepository<Noreply, Long> {

    @Modifying
    @Query("delete from Noreply r where r.notice.nno = :nno") // 파라미터에서 전달받은 = ':bno'
    void deleteByNno(Long nno);


    //    게시물 번호에 해당하는 댓글 목록 반환
//    List<Noreply> getNorepliesByNoticeOrderByRno(Notice notice);

}
