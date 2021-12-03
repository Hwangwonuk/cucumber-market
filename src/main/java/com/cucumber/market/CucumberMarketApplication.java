package com.cucumber.market;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class CucumberMarketApplication extends WebMvcConfigurationSupport {
	// WebMvcConfigurationSupport
	// 메소드 파라미터를 공통으로 처리할 수 있게 해주는 HandlerMethodArgumentResolver가 있는데,
	// 이를 등록할 수 있게 해주는 클래스

	private final CurrentMemberArgumentResolver currentMemberArgumentResolver;

	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		super.addArgumentResolvers(argumentResolvers);
		argumentResolvers.add(currentMemberArgumentResolver);
	}

	public static void main(String[] args) {
		SpringApplication.run(CucumberMarketApplication.class, args);
	}

}
