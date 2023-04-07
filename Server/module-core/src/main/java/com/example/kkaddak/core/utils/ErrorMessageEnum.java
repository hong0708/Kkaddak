package com.example.kkaddak.core.utils;


import lombok.Getter;

@Getter
public enum ErrorMessageEnum {
    USER_NOT_EXIST("존재하지 않는 회원입니다"),
    MARKET_NOT_EXIST("존재하지 않는 마켓 아이템입니다."),
    LIKEMARKET_NOT_EXIST("북마크하지 않은 마켓입니다."),
    NO_MORE_MARKET("마켓 목록이 존재하지 않습니다."),
    NO_MORE_FOLLOWER("팔로워가 존재하지 않습니다."),
    NO_MORE_FOLLOWING("팔로잉이 존재하지 않습니다."),
    FOLLOW_NOT_EXIST("팔로우하고 있지 않는 유저입니다."),
    SONG_NOT_EXIST("음악이 존재하지 않습니다"),
    S3_UPLOAD_ERROR("파일 업로드중 오류가 발생했습니다."),
    UNAUTHORIZED_BOOTPAY("부트 페이 서버 접근 권한이 없습니다."),
    ABNORMAL_PAYMENT("비정상적으로 결제가 이루어졌습니다."),
    ACCOUNT_ADDRESS_REQUIRED("결제 과정에서 계정 주소가 누락되었습니다."),
    UNAUTHORIZED_EXCHANGE_REQUEST("결제자와 요청자의 계정 주소가 다릅니다."),
    EXPIRED_RECEIPT("만료된 결제 정보 입니다.");
    private String message;

    ErrorMessageEnum(String message) {
        this.message = message;
    }
}
