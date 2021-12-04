package com.cucumber.market.dto.member;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class RegisterAdminRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    private final String member_id;
}
