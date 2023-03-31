package com.example.kkaddak.api.dto.member;

import com.example.kkaddak.core.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomeProfileResDto {
    private String memberId;
    private String nickname;
    private String profilepath;
    private String nftThumbnailUrl;
    private int mySongs;

    @Builder
    public HomeProfileResDto(Member member, int mySongs) {
        this.memberId = member.getUuid().toString();
        this.nickname = member.getNickname();
        this.profilepath = member.getProfilePath();
        this.nftThumbnailUrl = member.getNftImagePath();
        this.mySongs = mySongs;
    }
}
