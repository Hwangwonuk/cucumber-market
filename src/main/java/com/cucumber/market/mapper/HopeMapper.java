package com.cucumber.market.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HopeMapper {
    void registerHope(@Param("productIdx") int productIdx, @Param("member_id") String member_id);

    int checkDuplicateHope(@Param("productIdx") int productIdx, @Param("member_id") String member_id);

    void cancelHope(@Param("productIdx") int productIdx, @Param("member_id") String member_id);
}
