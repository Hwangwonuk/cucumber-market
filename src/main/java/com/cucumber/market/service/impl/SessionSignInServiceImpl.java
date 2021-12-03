package com.cucumber.market.service.impl;

import com.cucumber.market.dto.member.MemberSignInResponse;
import com.cucumber.market.dto.member.MemberSignOutResponse;
import com.cucumber.market.exception.AlreadySignInException;
import com.cucumber.market.exception.AlreadySignOutException;
import com.cucumber.market.service.SessionSignInService;
import com.cucumber.market.util.SessionKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionSignInServiceImpl implements SessionSignInService {

    private final HttpSession httpSession;

    @Value("${cucumber.login.url}")
    private String loginUrl;

    @Value("${cucumber.default.url}")
    private String defaultUrl;

    @Override
    public MemberSignInResponse signInMember(String member_id) {
        if (getCurrentMemberId() != null) {
            throw new AlreadySignInException("ID " + member_id + "는 이미 로그인된 상태입니다.");
        }

        httpSession.setAttribute(SessionKeys.MEMBER_ID, member_id);

        return MemberSignInResponse.builder()
                .redirectUrl(defaultUrl)
                .build();
    }

    @Override
    public MemberSignOutResponse signOutMember() {
        if (getCurrentMemberId() == null) {
            throw new AlreadySignOutException("이미 로그아웃된 상태입니다.");
        }

        httpSession.invalidate();
        return MemberSignOutResponse.builder()
                .redirectUrl(loginUrl)
                .build();
    }

    @Override
    public String getCurrentMemberId() {
        return (String) httpSession.getAttribute(SessionKeys.MEMBER_ID);
    }
}
