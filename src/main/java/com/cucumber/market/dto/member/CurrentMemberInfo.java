package com.cucumber.market.dto.member;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/*
* 1. 문제
     redis에 cache를 저장하려고 할때 "DefaultSerializer requires a Serializable payload but received an object of type" 에러가 발생한다.
  2. 원인
     redis는 data를 hash해 저장하기 때문에, redis에 저장할 객체는 serializable를 implements 해야한다.
  3. 해결
     redis에 저장할 object를 Serializable을 implements 하도록 변경한다.
* */
@Getter
@Builder
public class CurrentMemberInfo implements Serializable {
    private final String member_id;
    private final String isAdmin;
}
