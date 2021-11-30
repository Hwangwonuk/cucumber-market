package com.cucumber.market.controller.dto;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class MemberUpdateInfoRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String member_id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String oldPassword;

    @NotBlank(message = "변경할 비밀번호를 입력해주세요.")
    private String newPassword;

    @NotBlank(message = "변경할 이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "변경할 전화번호를 입력해주세요.")
    @Pattern(regexp = "[0-9]{8,20}", message = "전화번호는 8~20자리의 숫자만 입력이 가능합니다.")
    private String phone;

    @NotBlank(message = "변경할 주소를 입력해주세요.")
    private String address;
}
