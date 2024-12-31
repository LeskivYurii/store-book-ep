package com.epam.rd.autocode.spring.project.azure.service;

import org.springframework.web.multipart.MultipartFile;

public interface BlobService {

    String generateLink(String fileName);
    String uploadImage(MultipartFile multipartFile);
    void deleteImage(String fileName);

}
