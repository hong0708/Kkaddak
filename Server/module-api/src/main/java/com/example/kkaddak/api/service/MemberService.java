package com.example.kkaddak.api.service;


import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.member.SignupReqDto;
import com.example.kkaddak.core.entity.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    DataResDto<?> kakaoLogin(String accessToken) throws JsonProcessingException;
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    DataResDto<?> signup(SignupReqDto signupReqDto, Member member);
    Boolean checkNicknameDuplicate(String nickname, Member member);
    DataResDto<?> findMemberById(int memberId);
}
