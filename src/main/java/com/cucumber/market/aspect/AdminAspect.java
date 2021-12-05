package com.cucumber.market.aspect;

import com.cucumber.market.exception.NoAdminAuthorityException;
import com.cucumber.market.service.SessionSignInService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
@Order(2)
public class AdminAspect {

    private final SessionSignInService sessionSignInService;

    @Before("@annotation(com.cucumber.market.annotation.CheckAdmin)")
    public void checkAdmin() {
        if (sessionSignInService.getCurrentMemberInfo().getIsAdmin().equals("n")) {
            throw new NoAdminAuthorityException("ADMIN 권한이 없습니다.");
        }
    }
}
