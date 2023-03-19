package com.example.kkaddak.api.controller;


import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.member.MemberDetails;
import com.example.kkaddak.api.service.AuctionService;
import com.example.kkaddak.core.dto.AuctionReqDto;
import com.example.kkaddak.core.entity.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Api(tags = "경매 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v3/auction")
public class AuctionController {
    private final AuctionService auctionService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "정상적으로 경매 등록이 성공한 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터 이미지 파일 확장자, 타입 및 입력값 부적절 시 응답"),
            @ApiResponse(code = 500, message = "서버 에러에 따른 응답")
    })
    @ApiOperation(value = "경매 생성 API", notes = "")
    @PostMapping("/create")
    public DataResDto<?> createAuction(
            @AuthenticationPrincipal MemberDetails memberDetails,
            AuctionReqDto auctionReqDto)
    {
        return auctionService.createAuction(auctionReqDto, memberDetails.getMember());
    }
}
