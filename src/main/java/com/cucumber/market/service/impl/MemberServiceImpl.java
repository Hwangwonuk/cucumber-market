package com.cucumber.market.service.impl;

import com.cucumber.market.controller.dto.*;
import com.cucumber.market.exception.MemberNotFoundException;
import com.cucumber.market.exception.PasswordMismatchException;
import com.cucumber.market.mapper.MemberMapper;
import com.cucumber.market.model.member.MemberDTO;
import com.cucumber.market.service.MemberService;
import com.cucumber.market.util.SHA256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
     * @param memberMyInfoRequest 회원조회시 필요한 정보
     */
    @Override
    public MemberDTO findMemberInfo(MemberMyInfoRequest memberMyInfoRequest) {
        MemberDTO memberDTO = memberMapper.findByMemberId(memberMyInfoRequest.getMember_id());
        if(memberDTO == null) {
            throw new MemberNotFoundException("회원을 찾을 수 없습니다.");
        }

        String encryptedPassword = SHA256Util.encryptSHA256(memberMyInfoRequest.getPassword());
        if(!memberDTO.getPassword().equals(encryptedPassword)) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        // Password를 넘겨주지 않게 하기 위해 새로운 MemberDTO를 Builder 생성자로 생성
        return MemberDTO.builder()
                .member_id(memberDTO.getMember_id())
                .name(memberDTO.getName())
                .address(memberDTO.getAddress())
                .phone(memberDTO.getPhone())
                .isactive(memberDTO.getIsactive())
                .isadmin(memberDTO.getIsadmin())
                .build();
    }


    /**
     * 회원정보 수정 메소드
     * @param memberUpdateInfoRequest 정보 수정시 필요한 회원정보
     */
    @Override
    public MemberUpdateInfoResponse updateMemberInfo(MemberUpdateInfoRequest memberUpdateInfoRequest) {
        MemberDTO memberDTO = memberMapper.findByMemberId(memberUpdateInfoRequest.getMember_id());
        if(memberDTO == null) {
            throw new MemberNotFoundException("회원을 찾을 수 없습니다.");
        }

        String encryptedOldPassword = SHA256Util.encryptSHA256(memberUpdateInfoRequest.getOldPassword());
        if(!memberDTO.getPassword().equals(encryptedOldPassword)) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        String encryptedNewPassword = SHA256Util.encryptSHA256(memberUpdateInfoRequest.getNewPassword());


        MemberUpdateInfoRequest updatedMemberInfo = MemberUpdateInfoRequest.builder()
                .member_id(memberUpdateInfoRequest.getMember_id())
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
    public MemberInactivateResponse inactivateMember(MemberIdPasswordRequest memberIdPasswordRequest) {
        MemberDTO memberDTO = memberMapper.findByMemberId(memberIdPasswordRequest.getMember_id());
        if(memberDTO == null) {
            throw new MemberNotFoundException("회원을 찾을 수 없습니다.");
        }

        String encryptedPassword = SHA256Util.encryptSHA256(memberIdPasswordRequest.getPassword());
        if(!memberDTO.getPassword().equals(encryptedPassword)) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        memberMapper.inactivateMember(
                MemberIdPasswordRequest.builder()
                        .member_id(memberIdPasswordRequest.getMember_id())
                        .password(encryptedPassword)
                        .build()
        );

        return MemberInactivateResponse.builder()
                .redirectUrl(loginUrl)
                .build();
    }

    /**
     * 로그인 메소드
     * @param memberIdPasswordRequest 로그인시 필요한 회원정보
     */
    @Override
    public MemberSignInResponse signInMember(MemberIdPasswordRequest memberIdPasswordRequest) {
        String encryptedPassword = SHA256Util.encryptSHA256(memberIdPasswordRequest.getPassword());
        return memberMapper.findByMemberIdAndPassword(
                MemberIdPasswordRequest.builder()
                        .member_id(memberIdPasswordRequest.getMember_id())
                        .password(encryptedPassword)
                        .build()
        );
    }
}

