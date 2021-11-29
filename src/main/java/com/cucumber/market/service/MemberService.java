package com.cucumber.market.service;

import com.cucumber.market.controller.dto.MemberSignUpRequest;

public interface MemberService {

    void singUpMember(MemberSignUpRequest memberSignUpRequest);

    void isDuplicateMemberId(String member_id);

}
