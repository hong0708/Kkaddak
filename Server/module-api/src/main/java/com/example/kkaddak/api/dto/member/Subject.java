package com.example.kkaddak.api.dto.member;


import com.example.kkaddak.core.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subject {

    private String email;
    private String nickname;
    private String profilePath;
    private String type; // 토큰 타입

    @Builder
    private Subject(String email, String nickname, String profilePath, String type) {
        this.email = email;
        this.nickname = nickname;
        this.profilePath = profilePath;
        this.type = type;
    }
    public static Subject atk(Member member) {
        return Subject.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profilePath(member.getProfilePath())
                .type("ATK")
                .build();
    }
    public static Subject rtk(Member member) {
        return Subject.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profilePath(member.getProfilePath())
                .type("RTK")
                .build();
    }
}
