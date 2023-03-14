package com.example.kkaddak.core.utils;

import com.example.kkaddak.core.exception.IllegalMemberTypeException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum MemberType {

    ADMIN("관리자"),
    MEMBER("회원");

    private static final Map<String, MemberType> paramMap =
            Arrays.stream(MemberType.values())
                    .collect(Collectors.toMap(
                            MemberType::getParam,
                            Function.identity()
                    ));

    private final String param;

    MemberType(String param){
        this.param = param;
    }

    @JsonCreator
    public static MemberType fromParam(String param){
        return Optional.ofNullable(param)
                .map(paramMap::get)
                .orElseThrow(() -> new IllegalMemberTypeException("'관리자' 또는 '회원'을 입력해주세요."));
    }

    @JsonValue
    public String getParam(){
        return this.param;
    }

}
