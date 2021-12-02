package com.cucumber.market;

import com.cucumber.market.annotation.CurrentMember;
import com.cucumber.market.mapper.MemberMapper;
import com.cucumber.market.service.MemberService;
import com.cucumber.market.service.SessionSignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class CurrentMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final SessionSignInService sessionSignInService;

    private final MemberService memberService;

    private final MemberMapper memberMapper;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CurrentMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        try {
            String currentMemberId = sessionSignInService.getCurrentMemberId();

            return memberMapper.findByMemberId(currentMemberId);
        } catch (IllegalArgumentException e) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }
}