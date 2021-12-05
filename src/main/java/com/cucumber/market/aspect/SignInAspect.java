package com.cucumber.market.aspect;

import com.cucumber.market.exception.NotYetSignInException;
import com.cucumber.market.service.SessionSignInService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
@Order(1)
public class SignInAspect {

    private final SessionSignInService sessionSignInService;

    @Before("@annotation(com.cucumber.market.annotation.CheckSignIn)")
    public void checkLogin() {
        if (sessionSignInService.getCurrentMemberInfo() == null) {
            throw new NotYetSignInException("아직 로그인되지 않았습니다.");
        }
    }
}
