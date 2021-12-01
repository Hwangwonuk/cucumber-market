package com.cucumber.market.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDTO {
    private String member_id;
    private String password;
    private String name;
    private String phone;
    private String address;

    private String isactive;
    private String isadmin;
}
