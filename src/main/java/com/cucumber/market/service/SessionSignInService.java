package com.cucumber.market.service;

import com.cucumber.market.dto.member.CurrentMemberInfo;
import com.cucumber.market.dto.member.MemberSignInResponse;
import com.cucumber.market.dto.member.MemberSignOutResponse;

public interface SessionSignInService {
    MemberSignInResponse signInMember(CurrentMemberInfo currentMemberInfo);

    MemberSignOutResponse signOutMember();

    CurrentMemberInfo getCurrentMemberInfo();
}