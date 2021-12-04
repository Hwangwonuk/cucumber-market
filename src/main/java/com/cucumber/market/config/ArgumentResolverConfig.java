package com.cucumber.market.config;

import com.cucumber.market.CurrentMemberArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ArgumentResolverConfig implements WebMvcConfigurer {
    // WebMvcConfigurationSupport
    // 메소드 파라미터를 공통으로 처리할 수 있게 해주는 HandlerMethodArgumentResolver가 있는데,
    // 이를 등록할 수 있게 해주는 클래스
    // TODO: SpringfoxConfig와 합쳐서 WebConfig로 만들면 될듯하다.
    private final CurrentMemberArgumentResolver currentMemberArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        resolvers.add(currentMemberArgumentResolver);
    }
}
