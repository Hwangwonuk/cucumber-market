package com.cucumber.market.controller;

import com.cucumber.market.annotation.CurrentMember;
import com.cucumber.market.dto.*;
import com.cucumber.market.service.MemberService;
import com.cucumber.market.service.SessionSignInService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*
@RestController
 - @Controller에 @ResponseBody가 추가된 어노테이션
   @RequestMapping 메소드를 처리할 때 @ResponseBody가 기본적으로 붙으면서 처리됨

@RequestMapping
 - 요청 URL을 처리할 클래스나 메소드에 연결시키는 어노테이션

@Valid
 - 클래스에 정의된 제약조건을 바탕으로 지정된 아규먼트에 대한 유효성 검사 실시
   build.gradle 의존관계 추가가 필요
   @Validated 는 스프링 전용 검증 애노테이션이고, @Valid 는 자바 표준 검증 애노테이션이다. 둘중
   아무거나 사용해도 동일하게 작동하지만, @Validated 는 내부에 groups 라는 기능을 포함하고 있다.
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    // TODO: 2021-12-01 HandlerMethodArgumentResolver를 사용해서 특정 Request 객체에 대해 처리 (예 : 패스워드 암호화)

    private final MemberService memberService;

    private final SessionSignInService sessionSignInService;
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
    public ResponseEntity<MemberDTO> findMemberInfo(@CurrentMember MemberMyInfoRequest request) {
        memberService.findMemberIdCount(request.getMember_id());
        return new ResponseEntity<>(memberService.findMemberInfo(request), HttpStatus.OK);
    }

    // 회원정보 수정(이름, 비밀번호, 전화번호, 주소)
    @PatchMapping("/myInfo")
    public ResponseEntity<MemberUpdateInfoResponse> updateMemberInfo(@Valid @RequestBody MemberUpdateInfoRequest request) {
        memberService.findMemberIdCount(request.getMember_id());
        memberService.isMatchIdAndPassword(request.getMember_id(), request.getOldPassword());
        return new ResponseEntity<>(memberService.updateMemberInfo(request), HttpStatus.FOUND);
    }

    // 회원탈퇴(비활성화)
    @PatchMapping("/inactivate")
    public ResponseEntity<MemberInactivateResponse> inactivateMember(@Valid @RequestBody MemberIdPasswordRequest request) {
        memberService.findMemberIdCount(request.getMember_id());
        memberService.isMatchIdAndPassword(request.getMember_id(), request.getPassword());
        return new ResponseEntity<>(memberService.inactivateMember(request), HttpStatus.FOUND);
    }

    // 로그인
    @PostMapping("/signIn")
    public ResponseEntity<MemberSignInResponse> signInMember(@Valid @RequestBody MemberIdPasswordRequest request) {
        memberService.findMemberIdCount(request.getMember_id());
        memberService.isMatchIdAndPassword(request.getMember_id(), request.getPassword());
        MemberSignInResponse response = memberService.signInMember(request);

        if(sessionSignInService.getCurrentMemberId().equals(response.getMember_id())) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            sessionSignInService.signInMember(request.getMember_id());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    // 로그아웃
    @GetMapping("/signOut")
    public ResponseEntity<MemberSignOutResponse> signOutMember() {
        return new ResponseEntity<>(sessionSignInService.signOutMember(), HttpStatus.OK);
    }

}
