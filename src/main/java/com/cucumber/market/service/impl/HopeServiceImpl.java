package com.cucumber.market.service.impl;

import com.cucumber.market.exception.AlreadyHopeRegisteredException;
import com.cucumber.market.exception.NotYetHopeRegisteredException;
import com.cucumber.market.mapper.HopeMapper;
import com.cucumber.market.service.HopeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HopeServiceImpl implements HopeService {

    private final HopeMapper hopeMapper;

    /**
     * 상품찜 메소드
     *
     * @param productIdx 상품번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void registerHope(int productIdx, String member_id) {
        hopeMapper.registerHope(productIdx, member_id);
    }

    /**
     * 상품찜 중복검사 메소드
     *
     * @param productIdx 상품번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void checkDuplicateHope(int productIdx, String member_id) {
        if (hopeMapper.checkDuplicateHope(productIdx, member_id) == 1) {
            throw new AlreadyHopeRegisteredException("이미 해당 상품을 찜하셨습니다.");
        }
    }

    /**
     * 상품찜 취소전 본인확인 메소드
     *
     * @param productIdx 상품번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void checkAlreadyHope(int productIdx, String member_id) {
        if (hopeMapper.checkDuplicateHope(productIdx, member_id) == 0) {
            throw new NotYetHopeRegisteredException("해당 상품을 찜한적이 없습니다.");
        }
    }

    /**
     * 상품찜 취소 메소드
     *
     * @param productIdx 상품번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void cancelHope(int productIdx, String member_id) {
        hopeMapper.cancelHope(productIdx, member_id);
    }
}
