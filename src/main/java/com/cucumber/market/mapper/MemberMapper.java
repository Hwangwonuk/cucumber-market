package com.cucumber.market.mapper;

import com.cucumber.market.dto.MemberIdPasswordRequest;
import com.cucumber.market.dto.MemberSignInResponse;
import com.cucumber.market.dto.MemberSignUpRequest;
import com.cucumber.market.dto.MemberUpdateInfoRequest;
import com.cucumber.market.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    void insertMember(MemberSignUpRequest memberSignUpRequest);

    int findMemberIdCount(String member_id);

    // 왜 여기만 @Param을 해주어야 되는가?
    int isMatchIdAndPassword(@Param("member_id")String member_id, @Param("password")String password);

    MemberDTO findByMemberId(String member_id);

    void updateMemberInfo(MemberUpdateInfoRequest memberUpdateInfoRequest);

    void inactivateMember(MemberIdPasswordRequest memberIdPasswordRequest);

    MemberSignInResponse findByMemberIdAndPassword(MemberIdPasswordRequest memberIdPasswordRequest);
}
