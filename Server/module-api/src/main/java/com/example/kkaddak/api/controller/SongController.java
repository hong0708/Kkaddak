package com.example.kkaddak.api.controller;


import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.SongReqDto;
import com.example.kkaddak.api.dto.SongResDto;
import com.example.kkaddak.api.dto.member.MemberDetails;
import com.example.kkaddak.api.service.impl.SongServiceImpl;
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
@RequestMapping
@RequiredArgsConstructor
public class SongController {
    private final SongServiceImpl songService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "음악 상세 조회가 성공했을 때 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터, 타입 및 입력값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "음악을 상세 조회하여 객체 형태로 반환하는 API")
    @GetMapping("/api/v1/song/{id}")
    public DataResDto<?> getSong(@PathVariable(name = "id") Integer songId) {
        return songService.getSong(songId);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "음악 전체 조회가 성공했을 때 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "음악을 전체 조회하여 리스트 형태로 반환하는 API")
    @GetMapping("/api/v1/song/list/all")
    public DataResDto<?> getAllSong() {
        return songService.getAllSong();
    }


    @ApiResponses({
            @ApiResponse(code = 200, message = "음악 최신 순 조회가 성공했을 때 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "음악을 최신 순으로 조회하여 리스트 형태로 반환하는 API")
    @GetMapping("/api/v1/song/list/latest")
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
    public DataResDto<?> uploadSong(@RequestBody SongReqDto songReqDto, @AuthenticationPrincipal MemberDetails memberDetails) throws IOException {
        return songService.uploadSong(songReqDto, memberDetails.getMember().getUuid());
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "음악 좋아요가 성공했을 때 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터 이미지 파일 확장자, 타입 및 입력값 부적절 시 응답"),
            @ApiResponse(code = 401, message = "accessToken 부적합 시 응답"),
    })
    @ApiOperation(value = "음악를 생성 및 음악 객체 반환하는 API")
    @GetMapping("/pai/v1/song/like/{id}/{songId}")
    public ResponseEntity<String> likeSong() {
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
