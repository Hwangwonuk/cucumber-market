package com.cucumber.market.controller;

import com.cucumber.market.controller.dto.*;
import com.cucumber.market.model.member.MemberDTO;
import com.cucumber.market.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Slf4j
 * @RestController
 * @RequiredArgsConstructor
 * @RequestMapping("/members")
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    // TODO: 2021-12-01 HandlerMethodArgumentResolver를 사용해서 특정 Request 객체에 대해 처리 (예 : 패스워드 암호화)

    private final MemberService memberService;
    /**
     * @ModelAttribute 는 필드 단위로 정교하게 바인딩이 적용된다. 특정 필드가 바인딩 되지 않아도 나머지
     * 필드는 정상 바인딩 되고, Validator를 사용한 검증도 적용할 수 있다.
     *
     * @RequestBody 는 HttpMessageConverter 단계에서 JSON 데이터를 객체로 변경하지 못하면 이후
     * 단계 자체가 진행되지 않고 예외가 발생한다. 컨트롤러도 호출되지 않고, Validator도 적용할 수 없다.
     * HttpMessageConverter 단계에서 실패하면 예외가 발생한다.
     */

    // 사용자

    // 회원가입
    @PostMapping("/signUp")
    public ResponseEntity<MemberSignUpResponse> signUpMember(@Valid @RequestBody MemberSignUpRequest request) {
        memberService.isDuplicateMemberId(request.getMember_id());
        return new ResponseEntity<>(memberService.signUpMember(request), HttpStatus.FOUND);
    }

    // 회원조회(회원정보)
    @GetMapping("/myInfo")
    public ResponseEntity<MemberDTO> findMemberInfo(@Valid MemberIdPasswordRequest request) {
        return new ResponseEntity<>(memberService.findMemberInfo(request), HttpStatus.OK);
    }

    // 회원정보 수정(이름, 비밀번호, 전화번호, 주소)
    @PatchMapping("/myInfo")
    public ResponseEntity<MemberUpdateInfoResponse> updateMemberInfo(@Valid @RequestBody MemberUpdateInfoRequest request) {
        return new ResponseEntity<>(memberService.updateMemberInfo(request), HttpStatus.FOUND);
    }

    // 회원탈퇴(비활성화)
    @PatchMapping("/inactivate")
    public ResponseEntity<MemberInactivateResponse> inactivateMember(@Valid @RequestBody MemberIdPasswordRequest request) {
        return new ResponseEntity<>(memberService.inactivateMember(request), HttpStatus.FOUND);
    }

}
