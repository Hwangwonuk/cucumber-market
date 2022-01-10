package com.cucumber.market.controller;

import com.cucumber.market.dto.member.MemberInfo;
import com.cucumber.market.resolver.CurrentMemberArgumentResolver;
import com.cucumber.market.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    CurrentMemberArgumentResolver currentMemberArgumentResolver;

    @Test
    public void getMemberInfoTest() {
        MemberInfo memberInfo = MemberInfo
                .builder()
                .member_id("tester")
                .address("tester address")
                .name("tester")
                .phone("01011111111")
                .build();

        when(memberService.getMemberInfo(any(String.class))).thenReturn(memberInfo);

        //mockMvc.perform(get("/members")

    }
}
