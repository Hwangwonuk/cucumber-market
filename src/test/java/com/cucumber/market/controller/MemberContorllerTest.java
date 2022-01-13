package com.cucumber.market.controller;

import com.cucumber.market.dto.category.BigCategoryUpdateRequest;
import com.cucumber.market.dto.category.CategoryResponse;
import com.cucumber.market.dto.member.MemberInfo;
import com.cucumber.market.dto.member.MemberSignOutResponse;
import com.cucumber.market.dto.member.MemberSignUpRequest;
import com.cucumber.market.dto.member.MemberSignUpResponse;
import com.cucumber.market.resolver.CurrentMemberArgumentResolver;
import com.cucumber.market.service.MemberService;
import com.cucumber.market.service.SessionSignInService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberController.class)
public class MemberContorllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @MockBean
    SessionSignInService sessionSignInService;

    @MockBean
    CurrentMemberArgumentResolver currentMemberArgumentResolver;

    @Test
    public void signUpMemberTest() throws Exception {
        MemberSignUpResponse memberSignUpResponse = MemberSignUpResponse.builder()
                .redirectUrl("www.cucumber-market.com")
                .build();

        when(memberService.signUpMember(any(MemberSignUpRequest.class))).thenReturn(memberSignUpResponse);

        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.builder()
                        .member_id("id123")
                        .password("asdf")
                        .name("이름")
                        .phone("01012341234")
                        .address("주소")
                        .build();

        mockMvc.perform(post("/members/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberSignUpRequest)))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(memberSignUpResponse)));

        verify(memberService).checkDuplicateMemberId(any(String.class));
    }

    @Test
    public void getMemberInfoTest() throws Exception {
        MemberInfo memberInfo = MemberInfo
                .builder()
                .member_id("tester")
                .address("tester address")
                .name("tester")
                .phone("01011111111")
                .build();

        when(memberService.getMemberInfo(any(String.class))).thenReturn(memberInfo);

        mockMvc.perform(get("/members/myInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("member_id", "123")
                        .param("isAdmin", "true"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void signOutMemberTest() throws Exception {

        MemberSignOutResponse memberSignOutResponse = MemberSignOutResponse.builder()
                .redirectUrl("www.cucumber-market.com")
                .build();

        when(sessionSignInService.signOutMember()).thenReturn(memberSignOutResponse);

        mockMvc.perform(get("/members/signOut")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
