package com.cucumber.market.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class MemberSignUpRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    private final String member_id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private final String name;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "[0-9]{8,20}", message = "전화번호는 8~20자리의 숫자만 입력이 가능합니다.")
    private final String phone;

    @NotBlank(message = "주소를 입력해주세요.")
    private final String address;
}
