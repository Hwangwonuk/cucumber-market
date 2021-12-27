package com.cucumber.market.controller;

import com.cucumber.market.dto.product.FindProductResponse;
import com.cucumber.market.resolver.CurrentMemberArgumentResolver;
import com.cucumber.market.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    @MockBean
    CategoryService categoryService;

    @MockBean
    HopeService hopeService;

    @MockBean
    CommentService commentService;

    @MockBean
    ReplyService replyService;

    @MockBean
    CurrentMemberArgumentResolver currentMemberArgumentResolver;

    @Test
    public void findProductByPaginationTest() throws Exception {
        FindProductResponse findProductResponse = FindProductResponse
                .builder()
                .productIdx("1")
                .bigCategoryName("대분류11")
                .smallCategoryName("소분류11")
                .title("제목")
                .price("12900")
                .member_id("pepsi123")
                .status("a")
                .updateTime("2021-12-14 23:43:02")
                .storeFilename("aa041c42-b42a-47ec-b8d3-5c2a4f6e94d0.JPG")
                .hope("50")
                .build();

        List<FindProductResponse> findProductResponseList = new ArrayList<>();
        findProductResponseList.add(findProductResponse);

        when(productService.findProductByPagination(any(int.class), any(int.class), any(String.class), any(String.class))).thenReturn(findProductResponseList);

        mockMvc.perform(get("/products")
                        .param("pageNum", "1")
                        .param("contentNum", "10")
                        .param("smallCategoryName", "소분류11")
                        .param("title", "제목"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(findProductResponseList)));

        verify(categoryService).checkExistSmallCategoryName(any(String.class));
    }
}
