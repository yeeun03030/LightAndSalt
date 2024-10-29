package kr.ac.kopo.webproject.dto;

import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String email;
    private String password;
    private String username;
    private String phoneNumber;
    private String address;
    private Integer role;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
