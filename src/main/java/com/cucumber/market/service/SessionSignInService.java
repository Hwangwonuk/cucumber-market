package com.cucumber.market.service;

import com.cucumber.market.dto.MemberSignOutResponse;
import com.cucumber.market.exception.AlreadySignInException;

public interface SessionSignInService {

    public void signInMember(String userId) throws AlreadySignInException;

    public MemberSignOutResponse signOutMember();

    public String getCurrentMemberId();
}