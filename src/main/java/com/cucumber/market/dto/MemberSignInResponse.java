package com.cucumber.market.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSignInResponse {
    private String member_id;
    private String name;
}