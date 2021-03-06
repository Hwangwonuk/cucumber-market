package com.cucumber.market.controller;

import com.cucumber.market.dto.member.*;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberController.class)
public class MemberControllerTest {
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
                        .name("??????")
                        .phone("01012341234")
                        .address("??????")
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
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(memberInfo)));
    }

    @Test
    public void updateMemberInfoTest() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("member_id", "id123");
        input.put("oldPassword", "oldPassword123");
        input.put("newPassword", "newPassword123");
        input.put("name", "??????123");
        input.put("phone", "01023412345");
        input.put("address", "??????123");

        MemberUpdateInfoResponse memberUpdateInfoResponse = MemberUpdateInfoResponse
                .builder()
                .redirectUrl("www.cucumber-market.com/myInfo")
                .build();

        when(memberService.updateMemberInfo(any(MemberUpdateInfoRequest.class), any(CurrentMemberInfo.class))).thenReturn(memberUpdateInfoResponse);

        mockMvc.perform(patch("/members/myInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("member_id", "id123")
                        .param("isAdmin", "true")
                        .content(objectMapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(memberUpdateInfoResponse)));

        verify(memberService).checkMatchIdAndPassword(any(String.class), any(String.class));
    }

    @Test
    public void inactivateMemberTest() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("member_id", "id123");
        input.put("password", "password123");

        MemberSignOutResponse memberSignOutResponse = MemberSignOutResponse.builder()
                .redirectUrl("www.cucumber-market.com")
                .build();

        when(sessionSignInService.signOutMember()).thenReturn(memberSignOutResponse);

        mockMvc.perform(patch("/members/inactivate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("member_id", "id123")
                        .param("isAdmin", "true")
                        .content(objectMapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(memberSignOutResponse)));

        verify(memberService).checkMatchIdAndPassword(any(String.class), any(String.class));
        verify(memberService).checkActivityMember(any(String.class));
        verify(memberService).inactivateMember(any(MemberIdPasswordRequest.class), any(CurrentMemberInfo.class));
    }

    @Test
    public void signInMemberTest() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("member_id", "id123");
        input.put("password", "password123");

        MemberSignInResponse memberSignInResponse = MemberSignInResponse.builder()
                .redirectUrl("www.cucumber-market.com")
                .build();

        when(sessionSignInService.signInMember(any(CurrentMemberInfo.class))).thenReturn(memberSignInResponse);

        mockMvc.perform(post("/members/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(memberService).checkExistMemberId(any(String.class));
        verify(memberService).checkMatchIdAndPassword(any(String.class), any(String.class));
        verify(memberService).checkActivityMember(any(String.class));
        verify(memberService).getCurrentMemberInfo(any(String.class));
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
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(memberSignOutResponse)));
    }

    @Test
    public void findAllMemberByPagination() throws Exception {
        List<Member> members = new ArrayList<Member>();
        Member member = Member.builder()
                .member_id("id123")
                .name("??????")
                .phone("01021342134")
                .address("??????")
                .isactive("y")
                .isadmin("y")
                .createtime("2022-01-17")
                .updatetime("2022-01-17")
                .build();
        members.add(member);

        when(memberService.findAllMemberByPagination(any(int.class), any(int.class))).thenReturn(members);

        mockMvc.perform(get("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("pageNum", "1")
                        .param("contentNum", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(members)));
    }
}
