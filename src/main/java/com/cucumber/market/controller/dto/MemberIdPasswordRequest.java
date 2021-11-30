package com.cucumber.market.controller.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class MemberIdPasswordRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String member_id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
