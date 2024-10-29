package kr.ac.kopo.webproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "notice")
public class Noreply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;
    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY) // 연관관계 지정
    private Notice notice;
}