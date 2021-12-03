package com.cucumber.market.service;

import com.cucumber.market.dto.MemberSignOutResponse;
import com.cucumber.market.exception.AlreadySignInException;
import com.cucumber.market.exception.AlreadySignOutException;
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

    @Override
    public void signInMember(String member_id) {
        if (getCurrentMemberId() != null/*.equals(memberId)*/) {
            throw new AlreadySignInException("이미 로그인된 상태입니다.");
        }

        httpSession.setAttribute(SessionKeys.Member_ID, member_id);
        System.out.println((String) httpSession.getAttribute(SessionKeys.Member_ID));
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
        System.out.println((String) httpSession.getAttribute(SessionKeys.Member_ID));
        return (String) httpSession.getAttribute(SessionKeys.Member_ID);
    }
}
