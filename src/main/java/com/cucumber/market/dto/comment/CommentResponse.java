package com.cucumber.market.dto.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {
    private int commentIdx;
}