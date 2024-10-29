package kr.ac.kopo.webproject.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDTO {
    private Long nno;
    private String title;
    private String content;
    private String writerEmail; // 작성자의 이메일 (id)
    private String writerName; // 작성자의 이름
    private Integer writerRole;
    private String writerPassword;
    private String topic;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private Integer noticeComment;
    private Integer visitorCount;
    private int replyCount; // 해당 게시글의 댓글 수
}