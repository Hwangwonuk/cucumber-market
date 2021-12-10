package com.cucumber.market.dto.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MultiPartsRequestPayload {
    private String name;
    private String brand;
    private MultipartFile file;
}




