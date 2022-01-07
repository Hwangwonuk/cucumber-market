package com.cucumber.market.controller;

import com.cucumber.market.dto.category.CategoryResponse;
import com.cucumber.market.dto.category.SmallCategoryUpdateRequest;
import com.cucumber.market.dto.comment.ContentResponse;
import com.cucumber.market.dto.product.*;
import com.cucumber.market.resolver.CurrentMemberArgumentResolver;
import com.cucumber.market.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    public void findDetailProductTest() throws Exception {
        FindDetailProductResponse findDetailProductResponse = FindDetailProductResponse
                .builder()
                .productIdx("1")
                .member_id("pepsi123")
                .title("제목")
                .content("내용")
                .price("12900")
                .deliveryPrice("3000")
                .status("a")
                .updateTime("2021-12-14 23:43:02")
                .images("null")
                .comments("3,4,5")
                .replies("3/6,3/7,4/8,4/9,4/10")
                .build();

        when(productService.findDetailProduct(any(int.class))).thenReturn(findDetailProductResponse);

        mockMvc.perform(get("/products/{productIdx}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(findDetailProductResponse)));

        verify(productService).checkExistProduct(any(int.class));
        verify(productService).checkNotDeleteProduct(any(int.class));
    }

    @Test
    public void soldOutProductTest() throws Exception {
        doNothing().when(productService).soldOutProduct(any(int.class), any(String.class));

        mockMvc.perform(
                        patch("/products/{productIdx}/soldOut", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .param("member_id", "123")
                                .param("isAdmin", "true"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(productService).checkExistProduct(any(int.class));
        verify(productService).checkNotSoldOutProduct(any(int.class));
        verify(productService).checkNotDeleteProduct(any(int.class));
        verify(productService).checkProductWriter(any(int.class), any(String.class));
    }

    @Test
    public void deleteProductTest() throws Exception {
        doNothing().when(productService).deleteProduct(any(int.class), any(String.class));

        mockMvc.perform(
                        patch("/products/{productIdx}/delete", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .param("member_id", "123")
                                .param("isAdmin", "true"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(productService).checkExistProduct(any(int.class));
        verify(productService).checkNotDeleteProduct(any(int.class));
        verify(productService).checkProductWriter(any(int.class), any(String.class));
    }

    @Test
    public void registerHopeTest() throws Exception {
        doNothing().when(hopeService).registerHope(any(int.class), any(String.class));

        mockMvc.perform(post("/products/{productIdx}/hope", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("member_id", "123")
                        .param("isAdmin", "true"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(productService).checkExistProduct(any(int.class));
        verify(productService).checkNotDeleteProduct(any(int.class));
        verify(hopeService).checkDuplicateHope(any(int.class), any(String.class));
    }

    @Test
    public void registerCommentTest() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("content", "댓글내용");

        ProductResponse productResponse = ProductResponse
                .builder()
                .productIdx(1)
                .build();

        when(commentService.registerComment(any(int.class), any(String.class), any(String.class))).thenReturn(productResponse);

        mockMvc.perform(post("/products/{productIdx}/comment", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("member_id", "123")
                        .param("isAdmin", "true")
                        .content(objectMapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productResponse)));

        verify(productService).checkExistProduct(any(int.class));
        verify(productService).checkNotDeleteProduct(any(int.class));
    }

    @Test
    public void getCommentTest() throws Exception {
        ContentResponse contentResponse = ContentResponse
                .builder()
                .member_id("iiii")
                .content("내용111")
                .updateTime("2021-12-14 23:43:02")
                .build();

        when(commentService.getComment(any(int.class))).thenReturn(contentResponse);

        mockMvc.perform(get("/products/{productIdx}/comments/{commentIdx}", 1, 2)
                        .param("member_id", "123")
                        .param("isAdmin", "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(contentResponse)));

        verify(commentService).checkExistComment(any(int.class));
        verify(commentService).checkNotDeleteComment(any(int.class));
        verify(productService).checkProductOrCommentWriter(any(int.class), any(int.class), any(String.class));
        verify(commentService).checkProductIncludeComment(any(int.class), any(int.class));
    }

    @Test
    public void updateCommentTest() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("content", "댓글내용");

        doNothing().when(commentService).updateComment(any(int.class), any(String.class));

        mockMvc.perform(
                        patch("/products/comments/{commentIdx}/update", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(input))
                                .param("member_id", "123")
                                .param("isAdmin", "true"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(commentService).checkExistComment(any(int.class));
        verify(commentService).checkNotDeleteComment(any(int.class));
        verify(commentService).checkCommentWriter(any(int.class), any(String.class));
    }

    @Test
    public void deleteCommentTest() throws Exception {
        doNothing().when(commentService).deleteComment(any(int.class), any(String.class));

        mockMvc.perform(
                        patch("/products/comments/{commentIdx}/delete", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .param("member_id", "123")
                                .param("isAdmin", "true"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(commentService).checkExistComment(any(int.class));
        verify(commentService).checkNotDeleteComment(any(int.class));
        verify(commentService).checkCommentWriter(any(int.class), any(String.class));
    }

}
