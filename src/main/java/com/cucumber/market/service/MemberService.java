package com.cucumber.market.service;

import com.cucumber.market.controller.dto.*;
import com.cucumber.market.model.member.MemberDTO;

public interface MemberService {

    MemberSignUpResponse signUpMember(MemberSignUpRequest memberSignUpRequest);

    void isDuplicateMemberId(String member_id);

    MemberUpdateInfoResponse updateMemberInfo(MemberUpdateInfoRequest memberUpdateInfoRequest);

    MemberInactivateResponse inactivateMember(MemberIdPasswordRequest memberInactivateRequest);

    MemberDTO findMemberInfo(MemberMyInfoRequest memberMyInfoRequest);

    MemberSignInResponse signInMember(MemberIdPasswordRequest memberIdPasswordRequest);
}
