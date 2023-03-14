package com.example.kkaddak.api.service;


import com.example.kkaddak.api.dto.member.EscapeResDto;
import com.example.kkaddak.api.dto.member.MemberResDto;
import com.example.kkaddak.api.dto.member.SignupReqDto;
import com.example.kkaddak.api.dto.member.TokenResDto;
import com.example.kkaddak.core.entity.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberService {
    TokenResDto kakaoLogin(String accessToken) throws JsonProcessingException;
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    MemberResDto signup(SignupReqDto signupReqDto, Member member) throws Exception;
    Boolean checkNicknameDuplicate(String nickname, Member member);

    EscapeResDto findMemberById(int memberId);
}
