package kr.ac.kopo.webproject.repository;

import kr.ac.kopo.webproject.entity.Notice;
import kr.ac.kopo.webproject.repository.search.SearchNoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long>, SearchNoticeRepository {
    @Query("select n, w from Notice n left join n.writer w where n.nno=:nno")
    Object getNoticeWithWriter(@Param("nno") Long nno);

    @Query("select n, r from Notice n left join Noreply r ON r.notice = n where n.nno=:nno")
    List<Object[]> getNoticeWithNoreply(@Param("nno") Long nno);

    @Query(value = "SELECT n, w, count(r) from Notice n " +
            "left join n.writer w " +
            "left join Noreply r on r.notice = n " +
            "group by n, w",
            countQuery = "select count(n) from Notice n"
    )
    Page<Object[]> getNoticeWithNoreplyCount(Pageable pageable);

    @Query("select n, w, count(r) " +
            "from Notice n left join n.writer w " +
            "left outer join Noreply r on r.notice=n " +
            "where n.nno=:nno " +
            "group by n, w")
    Object getNoticeByNno(@Param("nno") Long nno);
}
