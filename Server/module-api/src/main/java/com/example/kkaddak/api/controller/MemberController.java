package com.example.kkaddak.api.controller;


import com.example.kkaddak.api.config.jwt.JwtProvider;
import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.member.*;
import com.example.kkaddak.api.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = "유저 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "정상적으로 로그인 됐을 경우"),
            @ApiResponse(code = 400, message = "유효하지 않은 access token 값 입력시 오류"),
            @ApiResponse(code = 401, message = "유효하지 않은 토큰으로 요청할 경우(responseBody에서 분기 처리되지 않습니다.)"),
        })
    @ApiOperation(value = "로그인 API", notes = "해당 컨트롤러의 에러 메세지는 에러 발생 위치가 다양하여 body 내에 statusCode로 분기 처리되지 않습니다. 예외 처리를 하셔야 합니다.")
    @GetMapping("/kakao-login")
    public DataResDto<?> kakaoLogin(@RequestParam String accessToken) throws JsonProcessingException
    {
        return memberService.kakaoLogin(accessToken);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 회원가입 됐을 경우"),
            @ApiResponse(code = 400, message = "파일 타입이 옳지 않거나, 요청 값이 올바르지 않을 경우 응답 메세지"),
            @ApiResponse(code = 401, message = "유효하지 않은 토큰으로 요청할 경우(responseBody에서 분기 처리되지 않습니다.)"),
            @ApiResponse(code = 500, message = "이미지 업로드 간 발생한 서버 에러")
    })
    @ApiOperation(value = "회원가입 API", notes = "응답 메세지에서 statusCode로 에러가 분기되어 응답합니다. 각 필드는 null을 포함할 수 있습니다.")
    @PostMapping(value = "/sign-up", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public DataResDto<?> signUp(
            @AuthenticationPrincipal MemberDetails memberDetails,
            ProfileReqDto profileReqDto)
    {
        return memberService.signup(profileReqDto,  memberDetails.getMember());
    }
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청이 정상적으로 처리됐을 경우"),
            @ApiResponse(code = 401, message = "유효하지 않은 토큰으로 요청할 경우(responseBody에서 분기 처리되지 않습니다.)")
    })
    @GetMapping("/nicknames/{nickname}/exists")
    public DataResDto<?> checkNickname(@AuthenticationPrincipal MemberDetails memberDetails,
                                                 @PathVariable String nickname)
    {
        return DataResDto.builder()
                .data(memberService.checkNicknameDuplicate(nickname, memberDetails.getMember()))
                .statusMessage("요청이 정상적으로 처리되었습니다.")
                .statusCode(200)
                .build();
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 access token을 재발급했을 경우"),
            @ApiResponse(code = 401, message = "refresh token이 만료됐을 경우"),
            @ApiResponse(code = 401, message = "유효하지 않은 토큰으로 요청할 경우(responseBody에서 분기 처리되지 않습니다.)")
    })
    @ApiOperation(value = "accessToken 재발급 API", notes = "refresh token을 header에 담아야 합니다.")
    @GetMapping("/reissue")
    public DataResDto<?> reissue(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) throws JsonProcessingException
    {
        return jwtProvider.reissueAtk(memberDetails.getMember());
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 유저 삭제 됐을 경우"),
            @ApiResponse(code = 404, message = "존재하지 않는 회원일 경우")
    })
    @ApiOperation(value = "유저 회원가입 이탈시 유저 삭제 API")
    @PostMapping("/cancel-sign-up")
    public DataResDto<?> cancelSignUp(
            @AuthenticationPrincipal MemberDetails memberDetails)
    {
        return memberService.findMemberById(memberDetails.getMember().getId());
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "구독 성공시 응답"),
            @ApiResponse(code = 404, message = "존재하지 않는 회원일 경우")
    })
    @ApiOperation(value = "구독 API")
    @PostMapping("/follow/{artistId}")
    public DataResDto<?> followMember(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("artistId") String artistUuid)
    {
        return memberService.followMember(memberDetails.getMember(), artistUuid);
    }
    @ApiResponses({
            @ApiResponse(code = 204, message = "구독 취소 성공시 응답"),
            @ApiResponse(code = 404, message = "존재하지 않는 회원일 경우, 구독한 상태가 아닌 경우")
    })
    @ApiOperation(value = "구독 취소 API")
    @PostMapping("/unfollow/{artistId}")
    public DataResDto<?> unfollowMember(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("artistId") String artistUuid)
    {
        return memberService.unfollowMember(memberDetails.getMember(), artistUuid);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "프로필 조회 성공 시 응답"),
            @ApiResponse(code = 400, message = "입력값이 올바르지 않는 경우 응답"),
            @ApiResponse(code = 404, message = "존재하지 않는 회원일 경우 응답")
    })
    @ApiOperation(value = "프로필 조회 API", notes = "본인 및 아티스트 프로필 조회 시 요청 api입니다.")
    @GetMapping("/profile/{nickname}")
    public DataResDto<?> getProfile(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("nickname") String nickname)
    {

        return memberService.getProfile(memberDetails.getMember(), nickname);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 수정 됐을 경우 응답"),
            @ApiResponse(code = 400, message = "입력값이 올바르지 않는 경우 응답"),
            @ApiResponse(code = 404, message = "존재하지 않는 회원일 경우 응답")

    })
    @ApiOperation(value = "프로필 수정 API")
    @PostMapping("/profile")
    public DataResDto<?> updateProfile(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) EditProfileReqDto editProfileReqDto
            ) throws Exception
    {
        return memberService.updateProfile(memberDetails.getMember(), editProfileReqDto);
    }


    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 로그아웃 됐을 경우 응답"),
            @ApiResponse(code = 404, message = "존재하지 않는 회원일 경우 응답")
    })
    @ApiOperation(value = "로그아웃 API")
    @PostMapping("/logout")
    public DataResDto<?> logout(@RequestBody LogoutReqDto logoutDTO, @AuthenticationPrincipal MemberDetails memberDetails)
    {
        return memberService.logout(memberDetails.getMember(), logoutDTO);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 로그아웃 됐을 경우 응답"),
            @ApiResponse(code = 400, message = "존재하지 않는 회원일 경우 응답"),
            @ApiResponse(code = 404, message = "존재하지 않는 회원일 경우 응답")
    })
    @ApiOperation(value = "지갑 계정 저장 API")
    @PostMapping("/account")
    public DataResDto<?> logout(@RequestBody AccountReqDto accountReqDto, @AuthenticationPrincipal MemberDetails memberDetails)
    {
        return memberService.saveAccount(memberDetails.getMember(), accountReqDto);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 홈 프로필이 조회됐을 경우 응답"),
            @ApiResponse(code = 404, message = "존재하지 않는 회원일 경우 응답")

    })
    @ApiOperation(value = "홈 프로필 API")
    @GetMapping("/my-profile")
    public DataResDto<?> getMyProfile(@AuthenticationPrincipal MemberDetails memberDetails)
    {
        return memberService.getMyProfile(memberDetails.getMember());
    }
}
