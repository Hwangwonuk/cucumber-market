package com.cucumber.market.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSignUpResponse {
    private String redirectUrl;
}