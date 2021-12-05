package com.cucumber.market.service;

import com.cucumber.market.dto.member.*;

import java.util.List;

public interface MemberService {
    MemberSignUpResponse signUpMember(MemberSignUpRequest memberSignUpRequest);

    void isDuplicateMemberId(String member_id);

    void findMemberIdCount(String member_id);

    void isMatchIdAndPassword(String member_id, String password);

    void isActivityMember(String member_id);

    MemberUpdateInfoResponse updateMemberInfo(MemberUpdateInfoRequest memberUpdateInfoRequest, CurrentMemberInfo currentMemberInfo);

    void inactivateMember(MemberIdPasswordRequest memberInactivateRequest, CurrentMemberInfo currentMemberInfo);

    MemberInfo findMemberInfo(String member_id);

    CurrentMemberInfo getCurrentMemberInfo(String member_id);

    List<Member> findMemberPagination(Integer pageNum, Integer contentNum);

    void registerAdmin(String member_id);
}
