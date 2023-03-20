package com.example.kkaddak.api.controller;


import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.member.MemberDetails;
import com.example.kkaddak.api.exception.NoContentException;
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "정상적으로 경매 등록이 성공한 응답"),
            @ApiResponse(code = 204, message = "data 필드가 null일 때 응답"),
            @ApiResponse(code = 400, message = "조회 과정에서 발생 가능한 다양한 예외 응답"),
            @ApiResponse(code = 500, message = "서버 에러에 따른 응답")
    })
    @ApiOperation(value = "경매 생성 API", notes = "최초 조회시 lastId 값을 -1(Long)으로 전송해주세요. 이 외 경우, 응답받은 경매들 중 가장 작은 auctionId를 입력하시면 됩니다.")
    @GetMapping("/getpaging")
    public DataResDto<?> getAuctionByPaging(
            @RequestParam("limit") Long limit,
            @RequestParam("lastId") Long lastId) throws Exception {
        return auctionService.getAuctionAllByPaging(limit, lastId);
    }
}
