package com.cucumber.market.service;

public interface HopeService {
    void registerHope(int productIdx, String member_id);

    void checkDuplicateHope(int productIdx, String member_id);

    void checkAlreadyHope(int productIdx, String member_id);

    void cancelHope(int productIdx, String member_id);
}
