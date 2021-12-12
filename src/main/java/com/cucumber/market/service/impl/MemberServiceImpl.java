package com.cucumber.market.service.impl;

import com.cucumber.market.dto.member.*;
import com.cucumber.market.exception.*;
import com.cucumber.market.mapper.MemberMapper;
import com.cucumber.market.service.MemberService;
import com.cucumber.market.util.SHA256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    // Response에서 넘겨줄 url 주소(application.properties 설정파일)
    @Value("${cucumber.myInfo.url}")
    private String myInfoUrl;

    @Value("${cucumber.login.url}")
    private String loginUrl;

    /**
     * 회원가입 메소드
     *
     * @param memberSignUpRequest 가입시 저장할 회원정보
     */
    @Override
    public MemberSignUpResponse signUpMember(MemberSignUpRequest memberSignUpRequest) {
        String encryptedPassword = SHA256Util.encryptSHA256(memberSignUpRequest.getPassword());

        MemberSignUpRequest encryptedParam = MemberSignUpRequest.builder()
                .member_id(memberSignUpRequest.getMember_id())
                .password(encryptedPassword)
                .name(memberSignUpRequest.getName())
                .phone(memberSignUpRequest.getPhone())
                .address(memberSignUpRequest.getAddress())
                .build();

        memberMapper.signUpMember(encryptedParam);

        return MemberSignUpResponse.builder()
                .redirectUrl(loginUrl)
                .build();
    }

    /**
     * 회원 아이디 중복 검사
     *
     * @param member_id 중복 검사할 회원 아이디
     */
    @Override
    public void checkDuplicateMemberId(String member_id) {
        if (memberMapper.checkDuplicateMemberId(member_id) == 1) {
            throw new DataIntegrityViolationException("중복된 아이디입니다.");
        }
    }

    /**
     * 회원조회(회원정보) 메소드
     *
     * @param member_id 현재 로그인한 회원의 아이디
     */
    @Override
    public MemberInfo getMemberInfo(String member_id) {
        return memberMapper.getMemberInfo(member_id);
    }

    /**
     * 현재회원 정보조회 메소드(아이디, 관리자여부)
     *
     * @param member_id 세션에 저장된 아이디
     */
    @Override
    public CurrentMemberInfo getCurrentMemberInfo(String member_id) {
        return memberMapper.getCurrentMemberInfo(member_id);
    }

    /**
     * 전체회원 정보조회 메소드
     *
     * @param pageNum    데이터를 출력할 페이지
     * @param contentNum 한 페이지에 출력할 데이터 수
     */
    @Override
    public List<Member> findAllMemberByPagination(int pageNum, int contentNum) {
        // pageNum은 1부터 시작하는 것을 가정
        if (pageNum <= 0)
            throw new PageNotNaturalNumberException("페이지는 1 이상 이어야 합니다.");

        int offset = (pageNum - 1) * contentNum;
        // offset으로 DB에서 몇번째 데이터만큼 건너뛸것인지 결정
        // contentNum으로 한 페이지에 몇개의 데이터를 보여줄지 결정
        // 만약 특정 페이지에 보여줄 데이터가 부족하다면 부족한대로 Response에 넘겨줌
        // 예) DB에 데이터 10개 있을때
        // contentNum이 4, offset이 8 (즉, pageNum이 3) -> 보여줄 데이터는 2개
        // contentNum이 5, offset이 10 (즉, pageNum이 3) -> 보여줄 데이터는 0개

        return memberMapper.findAllMemberByPagination(contentNum, offset);
    }

    /**
     * 관리자 등록 - 기존회원 관리자로 승격 메소드
     *
     * @param member_id 관리자로 승격할 회원 아이디
     */
    @Override
    public void registerAdmin(String member_id) {
        memberMapper.registerAdmin(member_id);
    }

    @Override
    public void checkAlreadyAdmin(String member_id) {
        if (memberMapper.checkAlreadyAdmin(member_id) == 1) {
            throw new AlreadyAdminException("입력한 회원은 이미 관리자 입니다.");
        }
    }

    /**
     * 회원아이디 존재여부 검사 메소드
     *
     * @param member_id 중복 검사할 회원 아이디
     */
    @Override
    public void checkExistMemberId(String member_id) {
        if (memberMapper.checkDuplicateMemberId(member_id) == 0) {
            throw new NotExistMemberException("입력한 아이디의 회원이 존재하지 않습니다.");
        }
    }

    /**
     * 아이디 비밀번호 일치여부 검사 메소드
     *
     * @param member_id 입력한 아이디
     * @param password  입력한 패스워드
     */

    @Override
    public void checkMatchIdAndPassword(String member_id, String password) {
        String encryptedPassword = SHA256Util.encryptSHA256(password);
        if (memberMapper.checkMatchIdAndPassword(member_id, encryptedPassword) == 0) {
            throw new PasswordMismatchException("입력한 아이디와 비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * 회원 탈퇴여부 검사 메소드
     *
     * @param member_id 입력한 아이디
     */
    @Override
    public void checkActivityMember(String member_id) {
        if (memberMapper.checkActivityMember(member_id) == 0) {
            throw new AlreadyInActiveMemberException("탈퇴한 계정의 아이디입니다.");
        }
    }

    /**
     * 회원정보 수정 메소드
     *
     * @param memberUpdateInfoRequest 정보 수정시 필요한 회원정보
     */
    @Override
    public MemberUpdateInfoResponse updateMemberInfo(MemberUpdateInfoRequest memberUpdateInfoRequest, CurrentMemberInfo currentMemberInfo) {
        String encryptedNewPassword = SHA256Util.encryptSHA256(memberUpdateInfoRequest.getNewPassword());
        MemberUpdateInfoRequest updatedMemberInfo = MemberUpdateInfoRequest.builder()
                .member_id(currentMemberInfo.getMember_id())
                .newPassword(encryptedNewPassword)
                .name(memberUpdateInfoRequest.getName())
                .address(memberUpdateInfoRequest.getAddress())
                .phone(memberUpdateInfoRequest.getPhone())
                .build();

        memberMapper.updateMemberInfo(updatedMemberInfo);

        return MemberUpdateInfoResponse.builder()
                .redirectUrl(myInfoUrl)
                .build();
    }

    /**
     * 회원 비활성화(탈퇴) 메소드
     *
     * @param memberIdPasswordRequest 비활성화시 필요한 회원정보
     */
    @Override
    public void inactivateMember(MemberIdPasswordRequest memberIdPasswordRequest, CurrentMemberInfo currentMemberInfo) {

        String encryptedPassword = SHA256Util.encryptSHA256(memberIdPasswordRequest.getPassword());
        memberMapper.inactivateMember(
                MemberIdPasswordRequest.builder()
                        .member_id(currentMemberInfo.getMember_id())
                        .password(encryptedPassword)
                        .build()
        );
    }
}

