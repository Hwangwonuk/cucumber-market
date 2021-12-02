package com.cucumber.market.dto;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/*
@NoArgsConstructor(force = true)
기본 생성자는 아무 파라미터도 없기 때문에
필드가 final로 정의되어 있는 경우 필드를 초기화 할 수 없어
기본 생성자를 만들 수 없고 에러가 발생
이 때 force 옵션을 true로 설정하면 final 필드를 기본값으로 강제로 초기화 시켜 생성자를 만들 수 있음
 */

@Getter
@Builder
public class MemberUpdateInfoRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    private final String member_id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String oldPassword;

    @NotBlank(message = "변경할 비밀번호를 입력해주세요.")
    private final String newPassword;

    @NotBlank(message = "변경할 이름을 입력해주세요.")
    private final String name;

    @NotBlank(message = "변경할 전화번호를 입력해주세요.")
    @Pattern(regexp = "[0-9]{8,20}", message = "전화번호는 8~20자리의 숫자만 입력이 가능합니다.")
    private final String phone;

    @NotBlank(message = "변경할 주소를 입력해주세요.")
    private final String address;
}
