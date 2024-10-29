package kr.ac.kopo.webproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 (auto-increment)
    private Long nno;

    private String topic;
    private String title;
    private String content;
    private Integer noticeComment;

    @Builder.Default // 기본값 설정
    private Integer visitorCount = 0;

    @ManyToOne(fetch = FetchType.LAZY) // M:1 설정
    private Member writer; // Member(entity table명) writer => 실제 이름 writer_email (_email은 Member 테이블 내에 있는 email을 foreign key 설정)

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void setVisitorCount(int i) {
        this.visitorCount = i;
    }
}