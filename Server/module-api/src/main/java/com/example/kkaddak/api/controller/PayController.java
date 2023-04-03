package com.example.kkaddak.api.controller;


import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.member.MemberDetails;
import com.example.kkaddak.api.dto.pay.ExchangeReqDto;
import com.example.kkaddak.api.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "결제 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v4/pay")
public class PayController {
    private final PayService payService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "결제 완료 후 코인 요청 응답"),
            @ApiResponse(code = 400, message = "boot pay 결제 과정에서 에러가 발생했거나 결제 완료 상태 이외의 receipt_id로 요청했을 경우, orderId가 비었을 경우 응답"),
            @ApiResponse(code = 401, message = "결제자와 요청자의 계정 주소가 다른 경우 응답"),
            @ApiResponse(code = 500, message = "boot Pay 관리자 권한이 없거나, 블록체인 트랜잭션 요청 과정에서 에러 발생 시 응답")
    })
    @ApiOperation(value = "결제 후 코인 요청 API")
    @PostMapping("/exchange")
    public DataResDto<?> exchangeCashToCoin(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody ExchangeReqDto exchangeReqDto) throws Exception {
        return payService.exchangeCashToCoin(memberDetails.getMember(), exchangeReqDto);
    }
}
