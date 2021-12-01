package com.cucumber.market.controller.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class MemberMyInfoRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    private final String member_id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;
}
