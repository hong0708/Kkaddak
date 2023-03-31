package com.example.kkaddak.api.controller;


import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.market.CloseMarketReqDto;
import com.example.kkaddak.api.dto.member.MemberDetails;
import com.example.kkaddak.core.exception.NoContentException;
import com.example.kkaddak.api.service.MarketService;
import com.example.kkaddak.core.dto.MarketConditionReqDto;
import com.example.kkaddak.core.dto.MarketReqDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.web3j.abi.datatypes.Int;


@Api(tags = "마켓 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v3/market")
public class MarketController {
    private final MarketService marketService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "정상적으로 마켓 등록이 성공한 응답"),
            @ApiResponse(code = 400, message = "입력 데이터 부적합(파라미터 이미지 파일 확장자, 타입 및 입력값 부적절 시 응답"),
            @ApiResponse(code = 500, message = "서버 에러에 따른 응답")
    })
    @ApiOperation(value = "마켓 생성 API", notes = "")
    @PostMapping("/create")
    public DataResDto<?> createMarket(
            @AuthenticationPrincipal MemberDetails memberDetails,
            MarketReqDto marketReqDto)
    {
        return marketService.createMarket(marketReqDto, memberDetails.getMember());
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "정상적으로 마켓 조건 조회가 성공한 경우 응답"),
            @ApiResponse(code = 204, message = "조회된 아이템이 없을 경우 응답"),
            @ApiResponse(code = 400, message = "입력 타입 또는 값이 적적하지 않을 경우 응답"),
            @ApiResponse(code = 500, message = "서버 에러에 따른 응답")
    })
    @ApiOperation(value = "마켓 조건 조회 API",
            notes = "최초 조회시 lastId 값을 -1으로 전송해주세요. 이 외 경우, 응답받은 마켓 중 가장 작은 auctionId를 입력하시면 됩니다.\n" +
            "전체 목록 조회 시 onlySelling = false, selling 목록 조회 시 true 입니다.")
    @GetMapping("/condition")
    public DataResDto<?> getMarketByCondition(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("limit") int limit,
            @RequestParam("lastId") int lastId,
            @RequestParam("onlySelling") boolean onlySelling) throws NoContentException {
        MarketConditionReqDto conditionReqDto = MarketConditionReqDto.builder()
                .limit(limit).lastId(lastId).onlySelling(onlySelling)
                .build();
        return marketService.getMarketAllByCondition(conditionReqDto, memberDetails.getMember());
    }
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "정상적으로 마켓 등록이 성공한 응답"),
            @ApiResponse(code = 204, message = "조회된 아이템이 없을 경우 응답"),
            @ApiResponse(code = 400, message = "입력 타입 또는 값이 적적하지 않을 경우 응답"),
            @ApiResponse(code = 500, message = "서버 에러에 따른 응답")
    })
    @ApiOperation(value = "마켓 북마크 목록 API",
            notes = "최초 조회시 lastId 값을 -1으로 전송해주세요. 이 외 경우, 응답받은 마켓 중 가장 작은 auctionId를 입력하시면 됩니다.\n" +
                    "전체 목록 조회 시 onlySelling = false, selling 목록 조회 시 true 입니다.\n" +
                    "")
    @GetMapping("/my-like")
    public DataResDto<?> getMarketByMyLike(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("limit") int limit,
            @RequestParam("lastId") int lastId,
            @RequestParam("onlySelling") boolean onlySelling) throws NoContentException {
        MarketConditionReqDto conditionReqDto = MarketConditionReqDto.builder()
                .limit(limit).lastId(lastId).onlySelling(onlySelling)
                .build();
        return marketService.getMarketAllByMyLike(conditionReqDto, memberDetails.getMember());
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "마켓 북마크 성공시 응답"),
            @ApiResponse(code = 404, message = "존재하지 않는 회원일 경우")
    })
    @ApiOperation(value = "마켓 북마크 API")
    @PostMapping("/like/{marketId}")
    public DataResDto<?> followMember(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("marketId") Integer marketId)
    {
        return marketService.likeMarket(memberDetails.getMember(), marketId);
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "마켓 북마크 취소 성공시 응답"),
            @ApiResponse(code = 404, message = "존재하지 않는 회원일 경우, 북마크 상태가 아닌 경우")
    })
    @ApiOperation(value = "마켓 북마크 취소 API")
    @PostMapping("/unlike/{marketId}")
    public DataResDto<?> unfollowMember(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("marketId") Integer marketId)
    {
        return marketService.unlikeMarket(memberDetails.getMember(), marketId);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "마켓 상세 정보 조회 성공시 응답"),
            @ApiResponse(code = 404, message = "존재하지 않는 회원일 경우, 북마크 상태가 아닌 경우")
    })
    @ApiOperation(value = "마켓 상세 정보 조회 API")
    @GetMapping("/detail/{marketId}")
    public DataResDto<?> getMarketDetail(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("marketId") Integer marketId) throws Exception {
        return marketService.getMarketDetail(memberDetails.getMember(), marketId);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "마켓 종료 성공시 응답"),
            @ApiResponse(code = 404, message = "존재하지 않는 회원일 경우, 북마크 상태가 아닌 경우")
    })
    @ApiOperation(value = "판매 종료 API")
    @PostMapping("/close")
    public DataResDto<?> closeMarket(
            @AuthenticationPrincipal MemberDetails memberDetails,
            CloseMarketReqDto closeMarketReqDto) {
        return marketService.closeMarket(memberDetails.getMember(), closeMarketReqDto);
    }

}
