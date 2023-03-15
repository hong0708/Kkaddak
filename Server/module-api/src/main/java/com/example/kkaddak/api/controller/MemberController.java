package com.example.kkaddak.api.controller;


import com.example.kkaddak.api.config.jwt.JwtProvider;
import com.example.kkaddak.api.dto.member.*;
import com.example.kkaddak.api.service.MemberService;
import com.example.kkaddak.core.entity.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@Api(tags = "유저 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 로그인 됐을 경우"),
            @ApiResponse(code = 400, message = "유효하지 않은 access token 값 입력시 오류"),
            @ApiResponse(code = 401, message = "유효하지 않은 토큰으로 요청할 경우(responseBody에서 분기 처리되지 않습니다.)"),
    })
    @ApiOperation(value = "로그인 API", notes = "해당 컨트롤러의 에러 메세지는 에러 발생 위치가 다양하여 body 내에 statusCode로 분기 처리되지 않습니다. 예외 처리를 하셔야 합니다.")
    @GetMapping("/kakao-login")
    public ResponseEntity<TokenResDto> kakaoLogin(@RequestParam String accessToken) throws JsonProcessingException
    {
        TokenResDto token = memberService.kakaoLogin(accessToken);
        token.setStatusCode(200);
        token.setStatusMessage("로그인되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 회원가입 됐을 경우"),
            @ApiResponse(code = 400, message = "파일 타입이 옳지 않거나, 요청 값이 올바르지 않을 경우 응답 메세지"),
            @ApiResponse(code = 401, message = "유효하지 않은 토큰으로 요청할 경우(responseBody에서 분기 처리되지 않습니다.)"),
            @ApiResponse(code = 500, message = "이미지 업로드 간 발생한 서버 에러")
    })
    @ApiOperation(value = "회원가입 API", notes = "응답 메세지에서 statusCode로 에러가 분기되어 응답합니다. 각 필드는 null을 포함할 수 있습니다.")
    @PostMapping(value = "/sign-up", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MemberResDto> signUp(
            @AuthenticationPrincipal MemberDetails memberDetails,
            SignupReqDto signupReqDto)
    {
        String profileImgPath = "";

        MemberResDto memberResDto = memberService.signup(signupReqDto,  memberDetails.getMember());
        memberResDto.setStatusCode(200);
        memberResDto.setStatusMessage("고객님의 정보가 정상적으로 저장되었습니다.");
        return new ResponseEntity<>(memberResDto, HttpStatus.OK);
    }
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 수정 됐을 경우"),
            @ApiResponse(code = 400, message = "요청 파라미터 오류"),
            @ApiResponse(code = 401, message = "유효하지 않은 토큰으로 요청할 경우(responseBody에서 분기 처리되지 않습니다.)")
    })
    @GetMapping("/nicknames/{nickname}/exists")
    public ResponseEntity<Boolean> checkNickname(@AuthenticationPrincipal MemberDetails memberDetails,
                                                 @PathVariable String nickname)
    {
        return new ResponseEntity<>(memberService.checkNicknameDuplicate(nickname, memberDetails.getMember()), HttpStatus.OK);
    }
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 발급됐을 경우"),
            @ApiResponse(code = 400, message = "JWT 인증 오류")
    })
    @ApiOperation(value = "accessToken 재발급 API", notes = "refresh token을 header에 담아야 합니다.")
    @GetMapping("/reissue")
    public ResponseEntity<TokenResDto> reissue(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) throws JsonProcessingException
    {
        return new ResponseEntity<>(jwtProvider.reissueAtk(memberDetails.getMember()), HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 유저 삭제 됐을 경우"),
            @ApiResponse(code = 400, message = "존재하지 않는 회원일 경우")
    })
    @ApiOperation(value = "유저 회원가입 이탈시 유저 삭제 API")
    @PostMapping("/cancel-sign-up")
    public ResponseEntity<EscapeResDto> cancelSignUp(@AuthenticationPrincipal MemberDetails memberDetails)
    {
        EscapeResDto escapeResDto = memberService.findMemberById(memberDetails.getMember().getId());
        return new ResponseEntity<>(escapeResDto, HttpStatus.OK);
    }


}
