package com.example.kkaddak.api.controller;


import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.SongReqDto;
import com.example.kkaddak.api.dto.SongResDto;
import com.example.kkaddak.api.dto.member.MemberDetails;
import com.example.kkaddak.api.service.SongService;
import com.example.kkaddak.core.entity.Song;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Api(tags = "음악 관련 API")
@RestController
@RequestMapping("/api/v2/song")
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "음악 상세 조회가 성공했을 때 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터, 타입 및 입력값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "음악을 상세 조회하여 객체 형태로 반환하는 API")
    @GetMapping("/{id}")
    public DataResDto<?> getSong(@PathVariable(name = "id") Integer songId) {
        return songService.getSong(songId);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "음악 전체 조회가 성공했을 때 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "음악을 전체 조회하여 리스트 형태로 반환하는 API")
    @GetMapping("/list/all")
    public DataResDto<?> getAllSong() {
        return songService.getAllSong();
    }


    @ApiResponses({
            @ApiResponse(code = 200, message = "음악 최신 순 조회가 성공했을 때 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "음악을 최신 순으로 조회하여 리스트 형태로 반환하는 API")
    @GetMapping("/list/latest")
    public DataResDto<?> getLatestSong() {
        return songService.getLatestSong();
    }

    @ApiResponses({
            @ApiResponse(code = 201, message = "음악 업로드가 성공했을 때 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터 이미지 파일 확장자, 타입 및 입력값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "음악을 생성 및 음악 객체 반환하는 API")
    @PostMapping("/api/v1/song/upload")
    public DataResDto<?> uploadSong(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody SongReqDto songReqDto) throws IOException {
        return songService.uploadSong(songReqDto, memberDetails.getMember());
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "음악 좋아요가 성공했을 때 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터 이미지 파일 확장자, 타입 및 입력값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "음악 좋아요 좋아요 취소 및 음악 객체 반환하는 API")
    @GetMapping("/api/v1/song/like/{songId}")
    public DataResDto<?> likeSong(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable(name = "songId") Integer songId) {
        return songService.clickLikeBtn(memberDetails.getMember(), songId);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "음악 좋아요 리스트 조회가 성공했을 때 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "음악 좋아요 리스트를 반환하는 API")
    @GetMapping("/api/v1/song/like/list")
    public DataResDto<?> getLikeList(@AuthenticationPrincipal MemberDetails memberDetails) {
        return songService.getLikeList(memberDetails.getMember());
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "나의 플레이 리스트에 추가가 성공했을 때 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터 이미지 파일 확장자, 타입 및 입력값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "나의 플레이 리스트에 추가하는 API")
    @GetMapping("/api/v1/song/myPlay/{songId}")
    public DataResDto<?> AddMyPlayListSong(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable(name = "songId") Integer songId) {
        return songService.addMyPlayList(memberDetails.getMember(), songId);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "나의 플레이 리스트에 제거가 성공했을 때 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터 이미지 파일 확장자, 타입 및 입력값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
            @ApiResponse(code = 406, message = "나의 플레이 리스트에서 음악이 존재하지 않을 때 응답"),
    })
    @ApiOperation(value = "나의 플레이 리스트에서 제거하는 API")
    @GetMapping("/api/v1/song/myPlay/{songId}/delete")
    public DataResDto<?> DeleteMyPlayListSong(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable(name = "songId") Integer songId) {
        return songService.deleteMyPlayList(memberDetails.getMember(), songId);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "나의 플레이 리스트 조회가 성공했을 때 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "나의 플레이 리스트를 최신 순으로 조회하여 리스트 형태로 반환하는 API")
    @GetMapping("/api/v1/song/myPlay/list")
    public DataResDto<?> getPlayList(@AuthenticationPrincipal MemberDetails memberDetails) {
        return songService.getPlayList(memberDetails.getMember());
    }


    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
