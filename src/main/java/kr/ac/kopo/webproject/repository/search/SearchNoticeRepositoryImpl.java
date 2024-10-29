package kr.ac.kopo.webproject.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import kr.ac.kopo.webproject.entity.Notice;
import kr.ac.kopo.webproject.entity.QMember;
import kr.ac.kopo.webproject.entity.QNoreply;
import kr.ac.kopo.webproject.entity.QNotice;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class SearchNoticeRepositoryImpl extends QuerydslRepositorySupport implements SearchNoticeRepository {
    public SearchNoticeRepositoryImpl() { // QuearydslRepositorySupport를 상속받을 때, 기본 생성자를 추가하지 않으면 오류가 생김
        super(Notice.class);
    }

    @Override
    public Notice search1() {
        log.info("search1() 메소드 호출됨");
        QNotice notice = QNotice.notice;
        QNoreply reply = QNoreply.noreply;
        QMember member = QMember.member;

        JPQLQuery<Notice> jpqlQuery = from(notice);

        jpqlQuery.leftJoin(member).on(notice.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.notice.eq(notice));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(notice, member.email, reply.count());
        tuple.groupBy(notice, member, reply);
//        jpqlQuery.leftJoin(reply).on(reply.board.eq(board)).groupBy(board, reply);
//        jpqlQuery.select(board).where(board.bno.eq(1L));


        log.info("========================");
        log.info(tuple);
        log.info("========================");

        // JPQL 실행방법
        List<Tuple> result = tuple.fetch();

        log.info("========================");
        for (Object arr : result) {
            System.out.println(arr);
        }
        log.info("========================");

//        log.info(result);
//        log.info("========================");

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("searchPage() 메소드 호출");

        QNotice notice = QNotice.notice;
        QNoreply reply = QNoreply.noreply;
        QMember member = QMember.member;

        JPQLQuery<Notice> jpqlQuery = from(notice);

        jpqlQuery.leftJoin(member).on(notice.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.notice.eq(notice));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(notice, member.email, reply.count());
        tuple.groupBy(notice, member, reply);

        BooleanBuilder booleanBuilder = new BooleanBuilder(); // 조건별로 타입에 따라 or 나 and 연산을 하여 관련 식을 만들기 편함
        BooleanExpression expression = notice.nno.gt(0L);

        booleanBuilder.and(expression);

        if (type != null) {
            String[] typeArr = type.split("");

            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {
                switch (t) {
                    case "t":
                        conditionBuilder.or(notice.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(notice.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }
        tuple.where(booleanBuilder);
        tuple.groupBy(notice, member);
        List<Tuple> result = tuple.fetch();

        log.info("========================");
        for (Object arr : result) {
            System.out.println(arr);
        }
        log.info("========================");

        return null;
    }
}
