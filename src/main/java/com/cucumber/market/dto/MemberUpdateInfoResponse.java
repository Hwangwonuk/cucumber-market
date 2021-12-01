package com.cucumber.market.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberUpdateInfoResponse {
    private String redirectUrl;
}
