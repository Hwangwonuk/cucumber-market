package com.cucumber.market.dto.product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindDetailProductResponse {
    // 판매글 상세정보 글번호, 작성자, 제목, 내용, 가격, 배송비,  글상태, 수정시간, 해당글의 모든 이미지 경로, 글번호에 속한 댓글번호, 대댓글번호
    private final String productIdx;

    private final String member_id;

    private final String title;

    private final String content;

    private final String price;

    private final String deliveryPrice;

    private final String status;

    private final String updateTime;

    private final String images;

    private final String comments;

    private final String replies;

}
