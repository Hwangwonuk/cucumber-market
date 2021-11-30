package com.cucumber.market.service;

import com.cucumber.market.controller.dto.*;
import com.cucumber.market.model.member.MemberDTO;

public interface MemberService {

    MemberSignUpResponse signUpMember(MemberSignUpRequest memberSignUpRequest);

    void isDuplicateMemberId(String member_id);

    MemberInactivateResponse inactivateMember(MemberIdPasswordRequest memberInactivateRequest);

    MemberUpdateInfoResponse updateMemberInfo(MemberUpdateInfoRequest memberUpdateInfoRequest);

    MemberDTO findMemberInfo(MemberIdPasswordRequest memberIdPasswordRequest);
}
