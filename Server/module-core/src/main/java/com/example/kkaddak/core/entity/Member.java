package com.example.kkaddak.core.entity;

import com.example.kkaddak.core.utils.MemberType;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "char(36)")
    @Type(type = "uuid-char")
    private UUID uuid;
    private String email;
    private String nickname;
    private String profilePath;
    @Enumerated(value = EnumType.STRING)
    private MemberType memberType;

    @Builder
    public Member(String email, String nickname, String profilePath, String memberType) {
        this.uuid = UUID.randomUUID();
        this.email = email;
        this.nickname = nickname;
        this.profilePath = profilePath;
        this.memberType = MemberType.fromParam(memberType);
    }

    public void setMemberDetail(String nickname, String profilePath) {
        this.nickname = nickname;
        this.profilePath = profilePath;
    }
}