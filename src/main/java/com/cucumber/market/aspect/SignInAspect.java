package com.cucumber.market.aspect;

import com.cucumber.market.service.SessionSignInService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@Aspect
@RequiredArgsConstructor
public class SignInAspect {

    private final SessionSignInService sessionSignInService;

    @Before("@annotation(com.cucumber.market.annotation.CheckSignIn)")
    public void checkLogin() throws HttpClientErrorException {
        if (sessionSignInService.getCurrentMemberId() == null) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }
}
