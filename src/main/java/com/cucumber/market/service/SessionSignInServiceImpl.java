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
    public void signInMember(String memberId) {
        if (getCurrentMemberId().equals(memberId)) {
            throw new AlreadySignInException();
        }

        httpSession.setAttribute(SessionKeys.USER_ID, memberId);
    }

    @Override
    public MemberSignOutResponse signOutMember() {
        if (getCurrentMemberId() == null) {
            throw new AlreadySignOutException();
        }

        httpSession.invalidate();
        return MemberSignOutResponse.builder()
                .redirectUrl(loginUrl)
                .build();
    }

    @Override
    public String getCurrentMemberId() {
        return (String) httpSession.getAttribute(SessionKeys.USER_ID);
    }
}
