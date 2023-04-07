package com.example.kkaddak.api.dto.member;

import com.example.kkaddak.core.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResDto {

    @ApiModelProperty(example = "member 식별 아이디")
    private String memberId;
    @ApiModelProperty(example = "email")
    private String email;
    @ApiModelProperty(example = "프로필 이미지 경로")
    private String profilePath;
    @ApiModelProperty(example = "닉네임")
    private String nickname;
    @ApiModelProperty(example = "유저 타입(회원 / 관리자")
    private String memberType;

    @Builder
    public MemberResDto(Member member) {
        this.memberId = member.getUuid().toString();
        this.email = member.getEmail();
        this.profilePath = member.getProfilePath();
        this.nickname = member.getNickname();
        this.memberType = member.getMemberType().getParam();

    }
}
