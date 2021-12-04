package com.cucumber.market;

import com.cucumber.market.annotation.CurrentMember;
import com.cucumber.market.dto.member.CurrentMemberInfo;
import com.cucumber.market.mapper.MemberMapper;
import com.cucumber.market.service.SessionSignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class CurrentMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final SessionSignInService sessionSignInService;

    private final MemberMapper memberMapper;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(CurrentMember.class) != null
                && methodParameter.getParameterType().equals(CurrentMemberInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                     ModelAndViewContainer modelAndViewContainer,
                                     NativeWebRequest nativeWebRequest,
                                     WebDataBinderFactory webDataBinderFactory) {
        /*try {
            String currentMemberId = sessionSignInService.getCurrentMemberInfo().getMember_id();

            return memberMapper.getCurrentMemberInfo(currentMemberId);
        } catch (IllegalArgumentException e) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }*/
        // TODO:이미 로그인 상태를 검사하기 때문에 try,catch가 의미가있나? 세션 만료를 생각해서? 이미 SignInAspect가 하기때문에 필요없을듯
        return sessionSignInService.getCurrentMemberInfo();
    }
}