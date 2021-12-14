package com.cucumber.market.file;

import com.cucumber.market.dto.file.FileUploadForm;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<FileUploadForm> storeFiles(int pk, List<MultipartFile> multipartFiles)
            throws IOException {
        List<FileUploadForm> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            // 파일 확장자명이 JPG거나 PNG인 경우에만 저장한다
            if (!multipartFile.isEmpty() &&
                FilenameUtils.getExtension(multipartFile.getOriginalFilename()) != null &&
                (FilenameUtils.getExtension(multipartFile.getOriginalFilename()).equalsIgnoreCase("jpg") ||
                FilenameUtils.getExtension(multipartFile.getOriginalFilename()).equalsIgnoreCase("png"))) {
                storeFileResult.add(storeFile(pk, multipartFile));
            }
        }
        return storeFileResult;
    }

    public FileUploadForm storeFile(int pk, MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new FileUploadForm(pk, originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
