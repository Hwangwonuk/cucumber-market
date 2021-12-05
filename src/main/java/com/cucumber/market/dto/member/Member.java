package com.cucumber.market.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Member {
    private String member_id;
    private String name;
    private String phone;
    private String address;

    private String isactive;
    private String isadmin;

    private String createtime;
    private String updatetime;
}
