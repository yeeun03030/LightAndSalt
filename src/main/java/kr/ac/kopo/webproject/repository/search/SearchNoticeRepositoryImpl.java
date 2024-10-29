package kr.ac.kopo.webproject.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import kr.ac.kopo.webproject.entity.Notice;
import kr.ac.kopo.webproject.entity.QMember;
import kr.ac.kopo.webproject.entity.QNoreply;
import kr.ac.kopo.webproject.entity.QNotice;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchNoticeRepositoryImpl extends QuerydslRepositorySupport implements SearchNoticeRepository {
    public SearchNoticeRepositoryImpl() { // QuearydslRepositorySupport를 상속받을 때, 기본 생성자를 추가하지 않으면 오류가 생김
        super(Notice.class);
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

        JPQLQuery<Tuple> tuple = jpqlQuery.select(notice, member, reply.count());

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

        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Notice.class, "notice");

            tuple.orderBy(new OrderSpecifier<Comparable>(direction, orderByExpression.get(prop)));
        });

        tuple.groupBy(notice, member);

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        log.info("========================");
        for (Object arr : result) {
            System.out.println(arr);
        }
        log.info("========================");

        long count = tuple.fetchCount();
        log.info("실행된 행 개수 -> COUNT: " + count);

        return new PageImpl<Object[]>(result.stream().map(t -> t.toArray())
                .collect(Collectors.toList()), pageable, count);
    }
}
