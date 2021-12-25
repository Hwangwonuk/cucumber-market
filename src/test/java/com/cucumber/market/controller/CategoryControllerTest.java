package com.cucumber.market.controller;

import com.cucumber.market.dto.category.*;
import com.cucumber.market.resolver.CurrentMemberArgumentResolver;
import com.cucumber.market.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    CategoryService categoryService;

    @MockBean
    CurrentMemberArgumentResolver currentMemberArgumentResolver;

    MockHttpSession mockHttpSession;

    @BeforeEach
    public void before() {
        mockHttpSession = new MockHttpSession();
    }

    @Test
    public void registerBigCategoryTest() throws Exception {

//        1) 아래 코드로 테스트시 다음 에러 발생

//        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
//        paramMap.add("bigCategoryName", "대분류11");
//        .. (생략)
//        mockMvc.perform(
//                        post("/categories/big")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .params(paramMap) // HTTP POST로 Body를 전달해야하므로 params가 아니라 content를 사용해야함
//                )

//        Resolved [org.springframework.http.converter.HttpMessageNotReadableException:
//        Required request body is missing:
//        public org.springframework.http.ResponseEntity<com.cucumber.market.dto.category.CategoryResponse>
//        com.cucumber.market.controller.CategoryController.registerBigCategory(com.cucumber.market.dto.category.BigCategoryNameRequest)]

//        2) 아래 코드로 테스트시 다음 에러 발생

//        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
//        paramMap.add("bigCategoryName", "대분류11");

//        Resolved [org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error:
//        Cannot deserialize value of type `java.lang.String` from Array value (token `JsonToken.START_ARRAY`);
//        nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException:
//        Cannot deserialize value of type `java.lang.String` from Array value (token `JsonToken.START_ARRAY`)<LF> at [Source: (PushbackInputStream);
//        line: 1, column: 20] (through reference chain: com.cucumber.market.dto.category.BigCategoryNameRequest["bigCategoryName"])]

//        다른 분들이 작성한 예제 코드에는 MultiValueMap과 Map을 주로 사용하고 있었음
//        그래서 Body로 전달할 JSON key-value 값이 한개라 하더라도 MultiValueMap을 사용해도 괜찮은 줄 알았으나
//        내부적으로 Jackson 라이브러리가 단순 JSON (Object {}) 타입이 아니라 JSON ARRAY (Array [{}]) 타입으로 parsing 한다는 것을 알게됨
//        관련 링크 : https://stackoverflow.com/questions/19333106/issue-with-parsing-the-content-from-json-file-with-jackson-message-jsonmappin
//        따라서 JSON을 사용할거라면 Map을 사용하자

        Map<String, String> input = new HashMap<>();
        input.put("bigCategoryName", "대분류11");

        CategoryResponse categoryResponse = CategoryResponse.builder()
                .redirectUrl("www.cucumber-market.com/categories")
                .build();
        when(categoryService.registerBigCategory(any(BigCategoryNameRequest.class))).thenReturn(categoryResponse);

        mockMvc.perform(
                        post("/categories/big")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(input))
                                // Map으로 만든 input을 json형식의 String으로 만들기 위해 objectMapper를 사용
                )
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryResponse)));

        verify(categoryService).checkDuplicateBigCategoryName(any(String.class));
        verify(categoryService).registerBigCategory(any(BigCategoryNameRequest.class));
    }

    @Test
    public void updateBigCategoryTestWithSuccess() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("oldBigCategoryName", "대분류11");
        input.put("newBigCategoryName", "대분류13");

        CategoryResponse categoryResponse = CategoryResponse.builder()
                .redirectUrl("www.cucumber-market.com/categories")
                .build();
        when(categoryService.updateBigCategory(any(BigCategoryUpdateRequest.class))).thenReturn(categoryResponse);

        mockMvc.perform(
                        patch("/categories/big")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryResponse)));

        verify(categoryService).checkExistBigCategoryName(any(String.class));
        verify(categoryService).updateBigCategory(any(BigCategoryUpdateRequest.class));
    }

    @Test
    public void getBigCategoryNamesTest() throws Exception {
        List<BigCategoryNamesResponse> bigCategoryNamesResponseList = new ArrayList<>();
        bigCategoryNamesResponseList.add(BigCategoryNamesResponse.builder().bigCategoryName("대분류11").build());
        bigCategoryNamesResponseList.add(BigCategoryNamesResponse.builder().bigCategoryName("대분류12").build());
        bigCategoryNamesResponseList.add(BigCategoryNamesResponse.builder().bigCategoryName("대분류13").build());

        when(categoryService.getBigCategoryNames()).thenReturn(bigCategoryNamesResponseList);

        mockMvc.perform(get("/categories/big"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bigCategoryNamesResponseList)));

        verify(categoryService).getBigCategoryNames();
    }

    @Test
    public void registerSmallCategoryTest() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("bigCategoryName", "대분류11");
        input.put("smallCategoryName", "소분류11");

        CategoryResponse categoryResponse = CategoryResponse.builder()
                .redirectUrl("www.cucumber-market.com/categories")
                .build();
        when(categoryService.registerSmallCategory(any(SmallCategoryRegisterRequest.class))).thenReturn(categoryResponse);

        mockMvc.perform(
                        post("/categories/small")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(input))
                        // Map으로 만든 input을 json형식의 String으로 만들기 위해 objectMapper를 사용
                )
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryResponse)));

        verify(categoryService).checkExistBigCategoryName(any(String.class));
        verify(categoryService).checkDuplicateSmallCategoryName(any(String.class));
        verify(categoryService).registerSmallCategory(any(SmallCategoryRegisterRequest.class));
    }

    @Test
    public void updateSmallCategoryTestWithSuccess() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("bigCategoryName", "대분류11");
        input.put("oldSmallCategoryName", "소분류11");
        input.put("newSmallCategoryName", "소분류13");

        CategoryResponse categoryResponse = CategoryResponse.builder()
                .redirectUrl("www.cucumber-market.com/categories")
                .build();
        when(categoryService.updateSmallCategory(any(SmallCategoryUpdateRequest.class))).thenReturn(categoryResponse);

        mockMvc.perform(
                        patch("/categories/small")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryResponse)));

        verify(categoryService).checkExistBigCategoryName(any(String.class));
        verify(categoryService).checkExistSmallCategoryName(any(String.class));
        verify(categoryService).checkDuplicateSmallCategoryName(any(String.class));
        verify(categoryService).updateSmallCategory(any(SmallCategoryUpdateRequest.class));
    }
}
