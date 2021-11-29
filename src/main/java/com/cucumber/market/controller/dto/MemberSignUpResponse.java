package com.cucumber.market.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSignUpResponse implements MemberSignUp {
    private String redirectUrl;
}