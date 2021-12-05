package com.cucumber.market.mapper;

import com.cucumber.market.dto.member.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    void insertMember(MemberSignUpRequest memberSignUpRequest);

    int findMemberIdCount(String member_id);

    // TODO: 2021-12-04 왜 여기만 @Param을 해주어야 되는가?
    int isMatchIdAndPassword(@Param("member_id")String member_id, @Param("password")String password);

    int isActivityMember(String member_id);

    MemberInfo findByMemberId(String member_id);

    void updateMemberInfo(MemberUpdateInfoRequest memberUpdateInfoRequest);

    void inactivateMember(MemberIdPasswordRequest memberIdPasswordRequest);

    CurrentMemberInfo getCurrentMemberInfo(String member_id);

    List<Member> findMemberByPagination(@Param("contentNum")Integer contentNum, @Param("offset")Integer offset);

    void registerAdmin(String member_id);
}
