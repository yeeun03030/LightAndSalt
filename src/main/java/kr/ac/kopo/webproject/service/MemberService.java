package kr.ac.kopo.webproject.service;

import kr.ac.kopo.webproject.dto.MemberDTO;
import kr.ac.kopo.webproject.entity.Member;

public interface MemberService {
    Member register(MemberDTO dto);

    default MemberDTO entityToDto(Member member) {
        MemberDTO memberDTO = MemberDTO.builder()
                .email(member.getEmail())
                .username(member.getUsername())
                .role(member.getRole())
                .password(member.getPassword())
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .regDate(member.getRegDate())
                .modDate(member.getModDate())
                .build();

        return memberDTO;
    }

    default Member dtoToEntity(MemberDTO dto) {
        Member member = Member.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .role(dto.getRole())
                .address(dto.getAddress())
                .password(dto.getPassword())
                .phoneNumber(dto.getPhoneNumber())
                .build();

        return member;
    }
}
