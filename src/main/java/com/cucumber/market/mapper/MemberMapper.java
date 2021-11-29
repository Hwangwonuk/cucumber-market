package com.cucumber.market.mapper;

import com.cucumber.market.controller.dto.MemberInactivateRequest;
import com.cucumber.market.controller.dto.MemberSignUpRequest;
import com.cucumber.market.model.member.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void insertMember(MemberSignUpRequest memberSignUpRequest);

    int findMemberIdCount(String member_id);

    MemberDTO findByMemberId(String member_id);

    void inactivateMember(MemberInactivateRequest memberSignUpRequest);
}
