package com.example.kkaddak.api.dto.member;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountReqDto {
    private String account;

    @Builder
    public AccountReqDto(String account) {
        this.account = account;
    }
}
