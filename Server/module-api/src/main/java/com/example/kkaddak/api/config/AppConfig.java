package com.example.kkaddak.api.config;


import com.example.kkaddak.api.config.jwt.CustomAuthenticationEntryPoint;
import com.example.kkaddak.api.config.jwt.JwtProvider;
import com.example.kkaddak.api.service.MemberService;
import com.example.kkaddak.core.repository.RedisDao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final JwtProvider jwtProvider;
    private final MemberService memberService;
    private final RedisDao redisDao;


    // security 설정 활성화
    private static final String[] ALLOWED_ENDPOINT = {
            "/v2/api-docs/**",
            "/swagger.json",
            "/webjars/**",
            "/swagger/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/images/**/**",
            "/api/v1/members/kakao-login",
            "/h2-console"
    };

    /** SecurityFilterChain : Spring Security 필터 체인에서 가장 첫 번째 필터
     * @param http : filterchain을 거치는 요청 http
     * @return http에 authorizedRequests 설정 추가 후 리런. 특정 url 접근 허용
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 필터체인 구성
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(ALLOWED_ENDPOINT).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider, memberService, redisDao),
                            UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
