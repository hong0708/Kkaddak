package com.example.kkaddak.api.controller;


import com.example.kkaddak.api.config.jwt.JwtProvider;
import com.example.kkaddak.api.dto.member.*;
import com.example.kkaddak.api.service.MemberService;
import com.example.kkaddak.core.entity.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @GetMapping("/kakao-login")
    public ResponseEntity<TokenResDto> kakaoLogin(@RequestParam String accessToken) throws JsonProcessingException
    {
        TokenResDto token = memberService.kakaoLogin(accessToken);
        token.setStatusCode(200);
        token.setStatusMessage("로그인되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping(value = "/sign-up", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MemberResDto> signUp(
            @AuthenticationPrincipal MemberDetails memberDetails,
            SignupReqDto signupReqDto) throws Exception
    {
        Member member = memberDetails.getMember();
        System.out.println(member.toString());
        String profileImgPath = "";

        MemberResDto memberResDto = memberService.signup(signupReqDto, member);
        memberResDto.setStatusCode(200);
        memberResDto.setStatusMessage("고객님의 정보가 정상적으로 저장되었습니다.");
        return new ResponseEntity<>(memberResDto, HttpStatus.OK);
    }
    @GetMapping("/nicknames/{nickname}/exists")
    public ResponseEntity<Boolean> checkNickname(@AuthenticationPrincipal MemberDetails memberDetails,
                                                 @PathVariable String nickname)
    {
        return new ResponseEntity<>(memberService.checkNicknameDuplicate(nickname, memberDetails.getMember()), HttpStatus.OK);
    }

    @GetMapping("/reissue")
    public ResponseEntity<TokenResDto> reissue(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) throws JsonProcessingException
    {
        return new ResponseEntity<>(jwtProvider.reissueAtk(memberDetails.getMember()), HttpStatus.OK);
    }

    @PostMapping("/cancel-sign-up")
    public ResponseEntity<EscapeResDto> cancelSignUp(@AuthenticationPrincipal MemberDetails memberDetails)
    {
        EscapeResDto escapeResDto = memberService.findMemberById(memberDetails.getMember().getId());
        return new ResponseEntity<>(escapeResDto, HttpStatus.OK);
    }


}
