package com.cucumber.market.service.impl;

import com.cucumber.market.controller.dto.MemberSignUpRequest;
import com.cucumber.market.mapper.MemberMapper;
import com.cucumber.market.service.MemberService;
import com.cucumber.market.util.SHA256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public void singUpMember(MemberSignUpRequest memberSignUpRequest) {
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
}

