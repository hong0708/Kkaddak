package com.example.kkaddak.api.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final String uploadImagePath;
    public WebMvcConfig(@Value("${custom.path.upload-images}") String uploadImagePath) {
        this.uploadImagePath = uploadImagePath;
    }

    /** 정적 리소스를 처리
     * addResourceHandlers 메소드를 사용하여 URL 패턴과 파일 경로를 매핑
     * 해당 URL로 요청이 들어오면 파일 경로에서 파일을 찾아서 응답으로 반환
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        List<String> imageFolders = Arrays.asList("member", "song");
        for(String imageFolder : imageFolders) {
            registry.addResourceHandler("/images/" + imageFolder + "/**") // 각 이미지 폴더마다 resourceHandler를 등록
                    .addResourceLocations("file:///" + uploadImagePath + "/" + imageFolder + "/") // 실제 파일 경로를 설정
                    .setCachePeriod(60 * 10 * 6)  // 캐싱 시간을 설정
                    .resourceChain(true) // 리소스 체인을 활성화하여 파일 경로에 있는 파일을 찾지 못하면 다음 리졸버에서 파일을 탐색
                    .addResolver(new PathResourceResolver()); // URL 경로와 파일 경로를 매핑
        }
    }

}
