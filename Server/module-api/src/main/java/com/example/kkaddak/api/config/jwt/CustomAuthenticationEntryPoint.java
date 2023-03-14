package com.example.kkaddak.api.config.jwt;

import com.example.kkaddak.api.dto.JwtErrorMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/** 스프링 시큐리티에서 인증되지 않은 사용자가 보호된 자원에 액세스하려고 할 때 호출되는 클래스
 * 로그인 페이지로 리디렉션하거나 인증 오류 메시지를 반환하는 역할
 */

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exceptionMessage = (String) request.getAttribute("exception");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JwtErrorMessageDto message = new JwtErrorMessageDto(exceptionMessage, HttpStatus.UNAUTHORIZED);
        String res = this.convertObjectToJson(message);
        response.getWriter().print(res);
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        return object == null ? null : objectMapper.writeValueAsString(object);
    }
}
