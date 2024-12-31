package com.epam.rd.autocode.spring.project.mapper.helper;

import com.epam.rd.autocode.spring.project.media.service.BlobService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class BookMapperHelper {

    private final BlobService blobService;

    @Named("toImage")
    public String toImage(MultipartFile multipartFile) {
        if(!multipartFile.isEmpty()){
            return blobService.uploadImage(multipartFile);
        }
        return null;
    }

    @Named("toImageLink")
    public String toImageLink(String fileName) {
        if(fileName == null) {
            return null;
        }
        return blobService.generateLink(fileName);
    }

}
