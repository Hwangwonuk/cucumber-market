package com.cucumber.market.service;

import com.cucumber.market.controller.dto.MemberInactivateRequest;
import com.cucumber.market.controller.dto.MemberSignUpRequest;

public interface MemberService {

    void signUpMember(MemberSignUpRequest memberSignUpRequest);

    void isDuplicateMemberId(String member_id);

    void inactivateMember(MemberInactivateRequest memberInactivateRequest);
}
