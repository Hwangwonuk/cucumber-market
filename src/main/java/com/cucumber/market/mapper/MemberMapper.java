package com.cucumber.market.mapper;

import com.cucumber.market.dto.MemberIdPasswordRequest;
import com.cucumber.market.dto.MemberSignInResponse;
import com.cucumber.market.dto.MemberSignUpRequest;
import com.cucumber.market.dto.MemberUpdateInfoRequest;
import com.cucumber.market.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void insertMember(MemberSignUpRequest memberSignUpRequest);

    int findMemberIdCount(String member_id);

    MemberDTO findByMemberId(String member_id);

    void updateMemberInfo(MemberUpdateInfoRequest memberUpdateInfoRequest);

    void inactivateMember(MemberIdPasswordRequest memberIdPasswordRequest);

    MemberSignInResponse findByMemberIdAndPassword(MemberIdPasswordRequest memberIdPasswordRequest);
}
