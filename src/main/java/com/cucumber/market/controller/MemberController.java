package com.cucumber.market.controller;

import com.cucumber.market.controller.dto.MemberSignUpResponse;
import com.cucumber.market.controller.dto.MemberSignUpRequest;
import com.cucumber.market.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Value("${cucumber.login.url}")
    private String loginUrl;

    // 회원가입 요청
    @PostMapping("")
    public ResponseEntity<MemberSignUpResponse> signUpMember(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest) {
        memberService.isDuplicateMemberId(memberSignUpRequest.getMember_id());
        memberService.singUpMember(memberSignUpRequest);
        MemberSignUpResponse response = MemberSignUpResponse.builder()
                .redirectUrl(loginUrl)
                .build();
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }
}
