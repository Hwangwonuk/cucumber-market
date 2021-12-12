package com.cucumber.market.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileUploadForm {
    private final int pk;
    private final String uploadFileName;
    private final String storeFileName;
}