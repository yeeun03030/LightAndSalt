package kr.ac.kopo.webproject.repository.search;

import kr.ac.kopo.webproject.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchNoticeRepository {

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
