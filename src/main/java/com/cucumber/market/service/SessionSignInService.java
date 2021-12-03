package com.cucumber.market.service;

import com.cucumber.market.dto.member.MemberSignInResponse;
import com.cucumber.market.dto.member.MemberSignOutResponse;

public interface SessionSignInService {
    MemberSignInResponse signInMember(String userId);

    MemberSignOutResponse signOutMember();

    String getCurrentMemberId();
}