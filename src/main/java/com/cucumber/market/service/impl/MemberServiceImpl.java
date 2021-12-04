package com.cucumber.market.service.impl;

import com.cucumber.market.dto.member.*;
import com.cucumber.market.exception.AlreadyInActiveMemberException;
import com.cucumber.market.exception.MemberNotFoundException;
import com.cucumber.market.exception.PasswordMismatchException;
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

        memberMapper.insertMember(encryptedParam);

        return MemberSignUpResponse.builder()
                .redirectUrl(loginUrl)
                .build();
    }

    /**
     * 회원 아이디 중복 검사
     * @param member_id 중복 검사할 회원 아이디
     */
    @Override
    public void isDuplicateMemberId(String member_id) {
        if (memberMapper.findMemberIdCount(member_id) == 1) {
            throw new DataIntegrityViolationException("중복된 아이디입니다.");
        }
    }

    /**
     * 회원조회(회원정보) 메소드
     * @param member_id 현재 로그인한 회원의 아이디
     */
    @Override
    public MemberInfo findMemberInfo(String member_id) {
        return memberMapper.findByMemberId(member_id);
    }

    /**
     * 현재회원 정보조회 메소드(아이디, 관리자여부)
     * @param member_id 세션에 저장된 아이디
     */
    @Override
    public CurrentMemberInfo getCurrentMemberInfo(String member_id) {
        return memberMapper.getCurrentMemberInfo(member_id);
    }

    /**
     * 전체회원 정보조회 메소드
     */

    @Override
    public List<Member> findAllMember() {
        return memberMapper.findAllMember();
    }

    /**
     * 관리자 등록 - 기존회원 관리자로 승격 메소드
     */
    @Override
    public void registerAdmin(String member_id) {
        memberMapper.registerAdmin(member_id);
    }

    /**
     * 회원아이디 존재여부 검사 메소드
     * @param member_id 중복 검사할 회원 아이디
     */
    @Override
    public void findMemberIdCount(String member_id) {
        if (memberMapper.findMemberIdCount(member_id) == 0) {
            throw new MemberNotFoundException("입력한 아이디의 회원이 존재하지 않습니다.");
        }
    }

    /**
     * 아이디 비밀번호 일치여부 검사 메소드
     * @param member_id 입력한 아이디
     * @param password 입력한 패스워드
     */

    @Override
    public void isMatchIdAndPassword(String member_id, String password) {
        String encryptedPassword = SHA256Util.encryptSHA256(password);
        if (memberMapper.isMatchIdAndPassword(member_id, encryptedPassword) == 0) {
            throw new PasswordMismatchException("입력한 아이디와 비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * 회원 탈퇴여부 검사 메소드
     * @param member_id 입력한 아이디
     */
    @Override
    public void isActivityMember(String member_id) {
        if (memberMapper.isActivityMember(member_id) == 0) {
            throw new AlreadyInActiveMemberException("탈퇴한 계정의 아이디입니다.");
        }
    }

    /**
     * 회원정보 수정 메소드
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

