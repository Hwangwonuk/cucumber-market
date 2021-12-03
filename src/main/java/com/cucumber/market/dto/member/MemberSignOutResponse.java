package com.cucumber.market.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSignOutResponse {
    private String redirectUrl;
}