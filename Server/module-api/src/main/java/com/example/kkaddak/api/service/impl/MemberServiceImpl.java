package com.example.kkaddak.api.service.impl;

import com.example.kkaddak.api.config.jwt.JwtProvider;
import com.example.kkaddak.api.dto.member.*;
import com.example.kkaddak.api.service.MemberService;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.repository.MemberRepository;
import com.example.kkaddak.core.utils.ErrorMessageEnum;
import com.example.kkaddak.core.utils.ImageUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, UserDetailsService {

    private final MemberRepository  memberRepository;
    private final JwtProvider jwtProvider;
    private final ImageUtil imageUtil;
    public TokenResDto kakaoLogin(String accessToken) throws JsonProcessingException
    {
        // 1. "인가 코드"로 "액세스 토큰" 요청 -> 안드로이드에서 엑세스 토큰을 전송해주기 때문에 생략
//        String accessToken = getAccessToken(code);
        // 2. 토큰으로 카카오 API 호출
        SocialMemberInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
        // 3. 카카오ID로 회원가입 처리
        Map<String, Object> resMap = signupKakaoUserIfNeed(kakaoUserInfo);
        // 4. 강제 로그인 처리
        Authentication authentication = forceLogin((Member)resMap.get("member"));
        // 5. response Header에 JWT 토큰 추가
        TokenResDto token = KakaoMemberAuthenticationInput(authentication, (Boolean)resMap.get("isExist"));

        return token;
    }

    /** 1. "인가코드"로 "액세스 토큰" 요청
     * @param code : 인가 코드
     * HTTP Header, Body에 필요한 정보들을 담기
     * client_id : kakao developers에서 제공된 REST API키
     * redirect_uri : redirect할 callback uri 넣기(http://localhost:8080/user/kakao/callback, 배포시 변경 요)
     * ** 해당 callback url을 kakao developers에 등록해 놓아야 한다
     * @return http 요청을 보내서 돌아온 응답에서 access token parsing
     * @exception : JsonProcessingException, responseBody 파싱 과정에서 발생
     */
    private String getAccessToken(String code) throws JsonProcessingException {

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "CLIENT_ID");
        body.add("redirect_uri", "http://localhost:8087/api/v1/members/kakao-login");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoAccessTokenReq = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoAccessTokenReq,
                String.class
        );

        // Http 응답(JSON) -> 엑세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    /** 2. 토큰으로 카카오 API 호출
     * @param accessToken :  액세스 토큰으로 카카오 API를 호출
     * @return SocialMemberInfoDto : 카카오에서 받은 사용자 이메일이 담긴 DTO
     */
    private SocialMemberInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException
    {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        // responseBody에 있는 정보를 꺼냄
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String email = jsonNode.get("kakao_account").get("email").asText();

        return new SocialMemberInfoDto(email);
    }

    /** 3. 카카오 email로 회원가입 처리
     * @param kakaoMemberInfo : 카카오로부터 받은 user info
     * @return Map<String, Object> : member 정보와 기존에 존재하던 사용자인지 아닌지 여부 정보
     */
    private Map<String, Object> signupKakaoUserIfNeed(SocialMemberInfoDto kakaoMemberInfo)
    {
        Map<String, Object> resMap = new HashMap<>();
        Boolean isExist = true;
        Member member = memberRepository.findMemberByEmail(kakaoMemberInfo.getEmail())
                .orElse(null);

        // DB에 중복된 email 있는지 확인
        if (Objects.equals(member, null)){
            isExist = false;
            member = Member.builder().email(kakaoMemberInfo.getEmail()).memberType("회원").build();
            memberRepository.save(member);
        }

        resMap.put("member", member);
        resMap.put("isExist", isExist);
        return resMap;
    }

    /** 4. 강제 로그인 처리
     * @param kakaoUser : 회원가입처리가 된 사용자 정보
     * @return authentication : 로그인 인증서
     */
    private Authentication forceLogin(Member kakaoUser)
    {
        UserDetails userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    /** 5. response Header에 JWT 토큰 추가
     * @param authentication : 발급된 인증서
     * @param isExist : 기존 사용자 or 신규 사용자 구분 여부
     * @return TokenResDto :
     */
    private TokenResDto KakaoMemberAuthenticationInput(Authentication authentication, Boolean isExist) throws JsonProcessingException
    {
        // response header token 추가
        UserDetailsImpl userDetailsImpl = ((UserDetailsImpl) authentication.getPrincipal());
        String email = userDetailsImpl.getEmail();
        return jwtProvider.createTokenByLogin(memberRepository.findMemberByEmail(email).get(), isExist);
    }

    /** JwtAuthenticationFilter에서 유저 정보를 토큰에 담을 때 사용
     * @param email the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        Member member = memberRepository.findMemberByEmail(email).orElseThrow
                (() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return new MemberDetails(member);
    }

    @Override
    public MemberResDto signup(SignupReqDto info, Member member) throws Exception
    {
        String profilePath = "";
        try {
            profilePath = imageUtil.uploadImage(info.getProfileImg(), "member");
            member.setMemberDetail(info.getNickname(), profilePath);
            return MemberResDto.builder().member(memberRepository.save(member)).build();
        }
        catch(IOException | IllegalStateException | SecurityException | NullPointerException e){
            return MemberResDto.builder().statusCode(500).statusMessage("이미지 저장 과정에서 에러가 발생했습니다.").build();
        }
        catch(IllegalArgumentException e){
            return MemberResDto.builder().statusCode(400).statusMessage("파일 타입이 올바르지 않습니다.").build();
        }
    }

    @Override
    public Boolean checkNicknameDuplicate(String nickname, Member member)
    {
        return memberRepository.findByNicknameAndIdNot(nickname, member.getId()).isPresent();
    }

    @Override
    public EscapeResDto findMemberById(int memberId)
    {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (!Objects.isNull(member) && Objects.isNull(member.getNickname())) {
                memberRepository.deleteById(memberId);
            return EscapeResDto.builder()
                    .statusCode(200)
                    .statusMessage("비정상적으로 회원가입이 진행되었습니다.")
                    .isEscape(true).build();
        }
        else if (Objects.isNull(member))
            return EscapeResDto.builder()
                    .statusCode(400)
                    .statusMessage(ErrorMessageEnum.USER_NOT_EXIST.getMessage())
                    .isEscape(false).build();
        return EscapeResDto.builder()
                .statusCode(200)
                .statusMessage("정상적으로 회원가입이 진행되었습니다.")
                .isEscape(false).build();
    }

}