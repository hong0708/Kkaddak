package com.example.kkaddak.api.config;


import com.example.kkaddak.api.config.jwt.CustomAuthenticationEntryPoint;
import com.example.kkaddak.api.config.jwt.JwtProvider;
import com.example.kkaddak.api.service.MemberService;
import com.example.kkaddak.api.service.impl.KATTokenContractWrapper;
import com.example.kkaddak.api.service.impl.MusicNFTContractWrapper;
import com.example.kkaddak.core.repository.RedisDao;
import kr.co.bootpay.Bootpay;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final JwtProvider jwtProvider;
    private final MemberService memberService;
    private final RedisDao redisDao;

    @Value("${ethereum.private-key}")
    private String privateKey;

    @Value("${ethereum.rpc-url}")
    private String rpcUrl;

    @Value("${ethereum.contract.nft-address}")
    private String NFTContractAddress;

    @Value("${ethereum.contract.wallet-address}")
    private String tokenContractAddress;

    @Value("${boot-pay.verification.rest-application-id}")
    private String restApplicationId;

    @Value("${boot-pay.verification.private-key}")
    private String bootPayPrivateKey;


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

    @Bean
    public Credentials credentials() {
        return Credentials.create(privateKey);
    }

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(rpcUrl));
    }

    @Bean(name = "ReadOnlyMusicNFTContractWrapper")
    public MusicNFTContractWrapper musicNFTContractWrapper(Web3j web3j){
        ReadonlyTransactionManager transactionManager = new ReadonlyTransactionManager(web3j(), NFTContractAddress);
        return MusicNFTContractWrapper.load(NFTContractAddress, web3j, transactionManager, createContractGasProvider());
    }

    @Bean(name = "KATTokenContractWrapper")
    public KATTokenContractWrapper tokenContractWrapper(Web3j web3j, Credentials credentials){
        return KATTokenContractWrapper.load(tokenContractAddress, web3j, credentials, createContractGasProvider());
    }

    @Bean
    public Bootpay bootpay(){
        return new Bootpay(restApplicationId, bootPayPrivateKey);
    }

    private ContractGasProvider createContractGasProvider(){
        BigInteger gasPrice = BigInteger.valueOf(0L); // 20 Gwei
        BigInteger gasLimit = BigInteger.valueOf(4_300_000); // 가스 한도
        return new StaticGasProvider(gasPrice, gasLimit);
    }
}
