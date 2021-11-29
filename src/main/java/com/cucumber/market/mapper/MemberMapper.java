package com.cucumber.market.mapper;

import com.cucumber.market.controller.dto.MemberSignUpRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void insertMember(MemberSignUpRequest memberSignUpRequest);

    int findMemberIdCount(String member_id);
}
