package com.example.kkaddak.api.config.jwt;

import com.example.kkaddak.api.dto.member.Subject;
import com.example.kkaddak.api.dto.member.TokenResDto;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.repository.RedisDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final ObjectMapper objectMapper;
    private final RedisDao redisDao;

    @Value("${spring.jwt.key}")
    private String key;
    @Value("${spring.jwt.live.atk}")
    private Long atkLive;
    @Value("${spring.jwt.live.rtk}")
    private Long rtkLive;

    @PostConstruct // 스프링 빈이 생성된 후 자동으로 초기화 메서드 실행
    protected void init() {
        // 키값을 암호화하여 저장
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    public TokenResDto createTokenByLogin(Member member,boolean isExist) throws JsonProcessingException {
        // access token 생성
        String atk = createToken(Subject.atk(member), atkLive);
        String rtk = createToken(Subject.rtk(member), rtkLive);
        // Redis에 refresh token 관리
        redisDao.setValues(member.getEmail(), rtk, Duration.ofMillis(rtkLive));
        return TokenResDto.builder()
                .statusCode(200)
                .statusMessage("access & refersh token 발급되었습니다.")
                .accessToken(atk)
                .refreshToken(rtk)
                .isExist(isExist)
                .build();
    }
    // 토큰은 발행 유저 정보, 발행 시간, 유효 시간, 그리고 해싱 알고리즘과 키를 설정
    private String createToken(Subject subject, Long tokenLive) throws JsonProcessingException {
        String subjectString = objectMapper.writeValueAsString(subject);
        Claims claims = Jwts.claims()
                .setSubject(subjectString);
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + tokenLive))
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();
    }

    public Subject extractSubjectFromAtk(String atk) throws JsonProcessingException {
        // Jwt 파서에 key 설정 후 토큰 파싱 후 Claim 객체 가져옴
        String subjectStr = Jwts.parser()
                .setSigningKey(key.getBytes())
                .parseClaimsJws(atk).getBody()
                .getSubject();
        return objectMapper.readValue(subjectStr, Subject.class);
    }

    public TokenResDto reissueAtk(Member member) throws JsonProcessingException {
        String rtkInRedis = redisDao.getValues(member.getEmail());
        if (Objects.isNull(rtkInRedis))
            return TokenResDto.builder()
                    .statusCode(401)
                    .statusMessage("refresh token이 만료되었습니다.")
                    .isExist(false)
                    .build();

        Subject atkSubject = Subject.atk(member);
        String atk = createToken(atkSubject, atkLive);
        return TokenResDto.builder()
                .statusCode(200)
                .statusMessage("access token 재발급되었습니다.")
                .accessToken(atk)
                .refreshToken(null)
                .isExist(true).build();
    }

}
