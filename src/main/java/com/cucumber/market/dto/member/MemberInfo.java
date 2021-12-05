package com.cucumber.market.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberInfo {
    private String member_id;
    private String name;
    private String phone;
    private String address;

}
