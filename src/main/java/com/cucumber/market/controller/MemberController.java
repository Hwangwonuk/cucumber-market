package com.cucumber.market.controller;

import com.cucumber.market.controller.dto.MemberSignUp;
import com.cucumber.market.controller.dto.MemberSignUpResponse;
import com.cucumber.market.controller.dto.MemberSignUpRequest;
import com.cucumber.market.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
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
    public ResponseEntity<MemberSignUp> signUpMember(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest) {
        try {
            memberService.isDuplicateMemberId(memberSignUpRequest.getMember_id());
        } catch (DataIntegrityViolationException e) { // PK 중복과 관련된 Exception
            return new ResponseEntity<>(memberSignUpRequest, HttpStatus.CONFLICT);
        }

        memberService.singUpMember(memberSignUpRequest);
        MemberSignUpResponse successResponse = MemberSignUpResponse.builder()
                .redirectUrl(loginUrl)
                .build();
        return new ResponseEntity<>(successResponse, HttpStatus.FOUND);
    }
}
