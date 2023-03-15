package com.example.kkaddak.api.config;

import com.fasterxml.classmate.TypeResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableSwagger2
@EnableWebMvc
@RequiredArgsConstructor
public class Swagger2Config implements WebMvcConfigurer {
    private final TypeResolver typeResolver;

    private static final String API_NAME = "Kkaddak API";
    private static final String API_VERSION = "1.0";
    private static final String API_DESCRIPTION = "Kkaddak API 문서";

    // Swagger UI 상단에 보여지는 API 정보를 설정하는 메소드입니다. API 이름, 버전, 설명 등을 설정할 수 있습니다
    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .build();
    }

    /** 스웨거 Docket을 생성하는 메소드
     * Docket : Swagger 문서를 생성하는 데 필요한 정보를 정의하는 빌더 클래스
      */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .consumes(getConsumeContentTypes())
//                .produces(getProduceContentTypes())
                // Spring Security의 @AuthenticationPrincipal 어노테이션이 붙은 파라미터들은 문서화하지 않도록 설정하는 메소드
                .ignoredParameterTypes(AuthenticationPrincipal.class)
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Pageable.class), typeResolver.resolve(Page.class)))
                .apiInfo(swaggerInfo())
                .securityContexts(Arrays.asList(securityContext())) // JWT 인증 헤더를 포함하도록 Swagger에 설정
                .securitySchemes(Arrays.asList(apiKey())) // : JWT 인증 토큰을 Swagger에 적용
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.kkaddak")) // 문서화할 패키지를 설정
                .paths(PathSelectors.any()) // 모든 API 경로를 문서화하도록 설정하는 메소드
                .build()
                .useDefaultResponseMessages(false); // 모든 응답 코드에 대해 Swagger 기본 응답 메시지를 사용하지 않도록 설정하는 메소드
    }

//    private Set<String> getConsumeContentTypes() {
//        Set<String> consumes = new HashSet<>();
//        consumes.add("application/json;charset=UTF-8");
//        consumes.add("application/x-www-form-urlencoded");
//        return consumes;
//    }
//
//    private Set<String> getProduceContentTypes() {
//        Set<String> produces = new HashSet<>();
//        produces.add("application/json;charset=UTF-8");
//        return produces;
//    }

    // swagger에서 jwt 토큰값 넣기위한 설정 -> JWT를 인증 헤더로 포함하도록 ApiKey 를 정의.
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    //전역 AuthorizationScope를 사용하여 JWT SecurityContext를 구성.
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope =
                new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes =
                new AuthorizationScope[1];

        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(
                new SecurityReference("JWT", authorizationScopes));
    }
}
