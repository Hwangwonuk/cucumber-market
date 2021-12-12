package com.cucumber.market.service;

import com.cucumber.market.dto.member.*;

import java.util.List;

public interface MemberService {
    MemberSignUpResponse signUpMember(MemberSignUpRequest memberSignUpRequest);

    void checkDuplicateMemberId(String member_id);

    MemberInfo getMemberInfo(String member_id);

    void checkExistMemberId(String member_id);

    void checkMatchIdAndPassword(String member_id, String password);

    void checkActivityMember(String member_id);

    MemberUpdateInfoResponse updateMemberInfo(MemberUpdateInfoRequest memberUpdateInfoRequest, CurrentMemberInfo currentMemberInfo);

    void inactivateMember(MemberIdPasswordRequest memberInactivateRequest, CurrentMemberInfo currentMemberInfo);

    CurrentMemberInfo getCurrentMemberInfo(String member_id);

    List<Member> findAllMemberByPagination(int pageNum, int contentNum);

    void registerAdmin(String member_id);

    void checkAlreadyAdmin(String member_id);
}
