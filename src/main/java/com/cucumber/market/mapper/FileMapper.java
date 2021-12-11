package com.cucumber.market.mapper;

import com.cucumber.market.dto.file.FileUploadForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    void save(List<FileUploadForm> storeImageFiles);

    int getMyThumbnailIdx(int productIdx);
}
