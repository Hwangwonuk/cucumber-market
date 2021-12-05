package com.cucumber.market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
public class SpringfoxConfig implements WebMvcConfigurer {

    @Bean
    public Docket api() { // Docket : Springfox 설정의 핵심이 되는 Bean
        return new Docket(DocumentationType.OAS_30) // 문서화 타입은 OAS_30 (openApi 3.0)을 사용
                .useDefaultResponseMessages(false) // 기본 제공되는 HttpStatus 200, 401, 403, 404 메시지를 표시 안함
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cucumber.market.controller")) // API 명세(Specification)가 작성되어 있는 패키지를 지정
                .paths(PathSelectors.any()) // apis() 에 있는 API 중 특정 path 를 선택
                .build()
                .apiInfo(apiInfo()); // Springfox 문서화 UI로 노출할 정보
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /*
       API 문서화 및 테스트를 가능케 해주는 Springfox
       return new ApiInfoBuilder()
               .title("Api Documentation 제목")
               .description("Api Documentation 설명")
               .version("1.5.14")
               .license("Apache License Version 2.0")
               .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
               .build();
       아레와 같이 문서에 대한 문서명, 추가설명, 버전, license 명, license url 등을 설정 하여 API에 대한 정보를 나타낼 수 있음
   */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Cucumber Market API Documentation")
                .description("Cucumber Market의 서버 API 문서")
                .version("0.0.1")
                .build();
    }

    // 기본적으로는 아래 URI로 Springfox UI 접속 가능
    // http://IP주소:포트번호/swagger-ui/index.html
}
