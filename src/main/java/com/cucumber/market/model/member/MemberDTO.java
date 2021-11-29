package com.cucumber.market.model.member;

import lombok.Getter;


@Getter
public class MemberDTO {
    private String member_id;
    private String password;
    private String name;
    private String phone;
    private String address;

    private String isactive;
    private String isadmin;
}
