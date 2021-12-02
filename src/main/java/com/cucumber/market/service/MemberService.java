package com.cucumber.market.service;

import com.cucumber.market.dto.*;

public interface MemberService {

    MemberSignUpResponse signUpMember(MemberSignUpRequest memberSignUpRequest);

    void isDuplicateMemberId(String member_id);

    void findMemberIdCount(String member_id);

    void isMatchIdAndPassword(String member_id, String password);

    MemberUpdateInfoResponse updateMemberInfo(MemberUpdateInfoRequest memberUpdateInfoRequest);

    MemberInactivateResponse inactivateMember(MemberIdPasswordRequest memberInactivateRequest);

    MemberDTO findMemberInfo(MemberMyInfoRequest memberMyInfoRequest);

    MemberSignInResponse signInMember(MemberIdPasswordRequest memberIdPasswordRequest);
}
