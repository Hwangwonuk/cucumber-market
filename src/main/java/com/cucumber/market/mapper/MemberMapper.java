package com.cucumber.market.mapper;

import com.cucumber.market.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    void insertMember(MemberSignUpRequest memberSignUpRequest);

    int findMemberIdCount(String member_id);

    // 왜 여기만 @Param을 해주어야 되는가?
    int isMatchIdAndPassword(@Param("member_id")String member_id, @Param("password")String password);

    int isActivityMember(String member_id);

    MemberDTO findByMemberId(String member_id);

    void updateMemberInfo(MemberUpdateInfoRequest memberUpdateInfoRequest);

    void inactivateMember(MemberIdPasswordRequest memberIdPasswordRequest);

//    MemberDTO findByMemberIdAndPassword(MemberIdPasswordRequest memberIdPasswordRequest);
}
