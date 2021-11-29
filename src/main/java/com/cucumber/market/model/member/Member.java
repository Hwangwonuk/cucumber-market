package com.cucumber.market.model.member;

import lombok.*;

import java.time.LocalDateTime;

/*
@Getter
@Builder
@AllArgsConstructor
 */

@Getter
@Builder
@AllArgsConstructor
public class Member {

    private String member_id;
    private String password;
    private String name;
    private String phone;
    private String address;

    private String isactive;
    private String isadmin;

    private LocalDateTime createtime;
    private LocalDateTime updatetime;
}
