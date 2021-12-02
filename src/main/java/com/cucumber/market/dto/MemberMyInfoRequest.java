package com.cucumber.market.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class MemberMyInfoRequest {
    private final String member_id;
}
