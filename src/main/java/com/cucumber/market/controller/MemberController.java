package com.cucumber.market.controller;

import com.cucumber.market.annotation.CheckAdmin;
import com.cucumber.market.annotation.CheckSignIn;
import com.cucumber.market.annotation.CurrentMember;
import com.cucumber.market.dto.member.*;
import com.cucumber.market.service.MemberService;
import com.cucumber.market.service.SessionSignInService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

@RequestBody
HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

@ResponseBody
 - 모든 메서드에 @ResponseBody 적용
 - 메시지 바디 정보 직접 반환(view 조회X)
 - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    /*
    * 의존성 주입(dependency injection)
      가장 유용한 방법은 생성자 주입 방법이다.
      final 로 선언함으로써  컴파일 단계에서 명확하게 의존관계를 확인할 수 있다.
      또한, 생성자에서만 값을 세팅할 수 있다는 장점이 있다.

      수정자 주입을 포함해서 나머지 주입방식은 모두 생성자 이후에 호출되므로, 필드에 final 키워드를 사용할 수 없다.
      생성자 주입을 항상 사용하고, 가끔 옵션이 필요한 경우에만 수정자 주입을 사용하도록 하자.
    * */
    private final MemberService memberService;

    private final SessionSignInService sessionSignInService;
    /*
     * JSON 결과를 파싱해서 사용할 수 있는 자바 객체로 변환하려면 Jackson, Gson 같은 JSON 변환
     * 라이브러리를 추가해서 사용해야 한다. 스프링 부트로 Spring MVC를 선택하면 기본으로
     * Jackson 라이브러리( ObjectMapper )를 함께 제공한다
     *
     * @RequestBody 를 사용해서 HTTP 메시지에서 데이터를 꺼내고 messageBody에 저장한다.
     * 문자로 된 JSON 데이터인 messageBody 를 objectMapper 를 통해서 자바 객체로 변환한다
     *
     * @RequestBody 객체 파라미터
     * @RequestBody 에 직접 만든 객체를 지정할 수 있다.
     * HttpEntity , @RequestBody 를 사용하면 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가
     * 원하는 문자나 객체 등으로 변환해준다.
     * HTTP 메시지 컨버터는 문자 뿐만 아니라 JSON도 객체로 변환해준다.
     *
     * @RequestBody는 생략 불가능
     * 스프링은 @ModelAttribute , @RequestParam 해당 생략시 다음과 같은 규칙을 적용한다.
     * String , int , Integer 같은 단순 타입 = @RequestParam
     * 나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외)
     * 따라서 @RequestBody 를 생략하면 @ModelAttribute 가 적용되어버린다.
     * 따라서 생략하면 HTTP 메시지 바디가 아니라 요청 파라미터를 처리하게 된다.
     *
     * @ModelAttribute 는 필드 단위로 정교하게 바인딩이 적용된다. 특정 필드가 바인딩 되지 않아도 나머지
     * 필드는 정상 바인딩 되고, Validator를 사용한 검증도 적용할 수 있다.
     *
     * @RequestBody 는 HttpMessageConverter 단계에서 JSON 데이터를 객체로 변경하지 못하면 이후
     * 단계 자체가 진행되지 않고 예외가 발생한다. 컨트롤러도 호출되지 않고, Validator도 적용할 수 없다.
     * HttpMessageConverter 단계에서 실패하면 예외가 발생한다.
     *
     * HttpEntity를 사용해도 된다
    * */

    // 사용자

    // 회원가입
    @PostMapping("/signUp")
    public ResponseEntity<MemberSignUpResponse> signUpMember(@Valid @RequestBody MemberSignUpRequest request) {
        memberService.checkDuplicateMemberId(request.getMember_id());

        return new ResponseEntity<>(memberService.signUpMember(request), HttpStatus.FOUND);
    }

    // 회원조회(회원정보 - 마이페이지)
    @GetMapping("/myInfo")
    @CheckSignIn
    public ResponseEntity<MemberInfo> getMemberInfo(@CurrentMember CurrentMemberInfo currentMemberInfo) {
        return new ResponseEntity<>(memberService.getMemberInfo(currentMemberInfo.getMember_id()), HttpStatus.OK);
    }

    // 회원정보 수정(이름, 비밀번호, 전화번호, 주소)
    @PatchMapping("/myInfo")
    @CheckSignIn
    public ResponseEntity<MemberUpdateInfoResponse> updateMemberInfo(@Valid @RequestBody MemberUpdateInfoRequest request,
                                                                     @CurrentMember CurrentMemberInfo currentMemberInfo) {
        memberService.checkMatchIdAndPassword(currentMemberInfo.getMember_id(), request.getOldPassword());

        return new ResponseEntity<>(memberService.updateMemberInfo(request, currentMemberInfo), HttpStatus.FOUND); // 회원정보 수정 + 리다이렉트
    }

    // 회원탈퇴(비활성화)
    @PatchMapping("/inactivate")
    @CheckSignIn
    public ResponseEntity<MemberSignOutResponse> inactivateMember(@Valid @RequestBody MemberIdPasswordRequest request,
                                                                     @CurrentMember CurrentMemberInfo currentMemberInfo) {
        memberService.checkMatchIdAndPassword(currentMemberInfo.getMember_id(), request.getPassword()); // 입력한 비밀번호와 현재 접속아이디 일치여부 검사
        memberService.checkActivityMember(currentMemberInfo.getMember_id()); // 회원탈퇴여부 검사
        memberService.inactivateMember(request, currentMemberInfo); // 회원탈퇴

        return new ResponseEntity<>(sessionSignInService.signOutMember(), HttpStatus.FOUND); // 로그아웃 + 리다이렉트
    }

    // 로그인
    @PostMapping("/signIn")
    public ResponseEntity<MemberSignInResponse> signInMember(@Valid @RequestBody MemberIdPasswordRequest request) {
        memberService.checkExistMemberId(request.getMember_id()); // 아이디 존재여부 검사
        memberService.checkMatchIdAndPassword(request.getMember_id(), request.getPassword()); // 아이디 비밀번호 일치여부 검사
        memberService.checkActivityMember(request.getMember_id());// 회원 탈퇴상태 여부검사
        CurrentMemberInfo currentMemberInfo = memberService.getCurrentMemberInfo(request.getMember_id());// 회원 아이디, 관리자여부 가져오기

        return new ResponseEntity<>(sessionSignInService.signInMember(currentMemberInfo), HttpStatus.OK); // 로그인(세션 저장)
    }

    // 로그아웃
    @GetMapping("/signOut")
    public ResponseEntity<MemberSignOutResponse> signOutMember() {
        return new ResponseEntity<>(sessionSignInService.signOutMember(), HttpStatus.OK);
    }

    // 관리자

    // 전체회원 조회 (페이징 적용)
    @GetMapping
    @CheckAdmin
    @CheckSignIn
    public ResponseEntity<List<Member>> findAllMemberByPagination(@RequestParam(defaultValue = "1") int pageNum,
                                                                  @RequestParam(defaultValue = "10") int contentNum) {
        return new ResponseEntity<>(memberService.findAllMemberByPagination(pageNum, contentNum), HttpStatus.OK);
    }

    // 관리자 등록 - 기존회원 관리자로 승격
    @PostMapping("/admin")
    @CheckAdmin
    @CheckSignIn
    public ResponseEntity<Void> registerAdmin(@Valid @RequestBody RegisterAdminRequest request) {
        memberService.checkExistMemberId(request.getMember_id());
        memberService.checkAlreadyAdmin(request.getMember_id());
        memberService.registerAdmin(request.getMember_id());
        return ResponseEntity.ok().build();
    }
}
