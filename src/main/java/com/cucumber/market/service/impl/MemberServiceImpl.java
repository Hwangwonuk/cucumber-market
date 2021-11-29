package com.cucumber.market.service.impl;

import com.cucumber.market.controller.dto.MemberInactivateRequest;
import com.cucumber.market.controller.dto.MemberSignUpRequest;
import com.cucumber.market.exception.MemberNotFoundException;
import com.cucumber.market.exception.PasswordMismatchException;
import com.cucumber.market.mapper.MemberMapper;
import com.cucumber.market.model.member.Member;
import com.cucumber.market.model.member.MemberDTO;
import com.cucumber.market.service.MemberService;
import com.cucumber.market.util.SHA256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    /**
     * 회원가입 메소드
     * @param memberSignUpRequest 가입시 저장할 회원정보
     */
    @Override
    public void signUpMember(MemberSignUpRequest memberSignUpRequest) {
        String encryptedPassword = SHA256Util.encryptSHA256(memberSignUpRequest.getPassword());

        MemberSignUpRequest encryptedParam = MemberSignUpRequest.builder()
                .member_id(memberSignUpRequest.getMember_id())
                .password(encryptedPassword)
                .name(memberSignUpRequest.getName())
                .phone(memberSignUpRequest.getPhone())
                .address(memberSignUpRequest.getAddress())
                .build();

        memberMapper.insertMember(encryptedParam);
    }

    @Override
    public void isDuplicateMemberId(String member_id) {
        if (memberMapper.findMemberIdCount(member_id) == 1) {
            throw new DataIntegrityViolationException("중복된 아이디입니다.");
        }
    }

    /**
     * 회원 비활성화(탈퇴) 메소드
     * @param memberInactivateRequest 비활성화시 필요한 회원정보
     */
    @Override
    public void inactivateMember(MemberInactivateRequest memberInactivateRequest) {
        MemberDTO memberDTO = memberMapper.findByMemberId(memberInactivateRequest.getMember_id());
        if(memberDTO == null) {
            throw new MemberNotFoundException("회원을 찾을 수 없습니다.");
        }

        String encryptedPassword = SHA256Util.encryptSHA256(memberInactivateRequest.getPassword());
        if(!memberDTO.getPassword().equals(encryptedPassword)) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        memberMapper.inactivateMember(
                MemberInactivateRequest.builder()
                        .member_id(memberInactivateRequest.getMember_id())
                        .password(encryptedPassword)
                        .build()
        );
    }
}

