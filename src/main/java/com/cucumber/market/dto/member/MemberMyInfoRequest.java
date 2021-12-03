package com.cucumber.market.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberMyInfoRequest {
    private final String member_id;
}
