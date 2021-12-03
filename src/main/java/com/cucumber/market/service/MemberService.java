package com.cucumber.market.service;

import com.cucumber.market.dto.*;

import java.lang.reflect.Member;

public interface MemberService {

    MemberSignUpResponse signUpMember(MemberSignUpRequest memberSignUpRequest);

    void isDuplicateMemberId(String member_id);

    void findMemberIdCount(String member_id);

    void isMatchIdAndPassword(String member_id, String password);

    void isActivityMember(String member_id);

    MemberUpdateInfoResponse updateMemberInfo(MemberUpdateInfoRequest memberUpdateInfoRequest, MemberDTO currentMember);

    void inactivateMember(MemberIdPasswordRequest memberInactivateRequest, MemberDTO currentMember);

    MemberDTO findMemberInfo(String member_id);

//    MemberDTO signInMember(MemberIdPasswordRequest memberIdPasswordRequest);
}
