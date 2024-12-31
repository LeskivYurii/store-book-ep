package com.epam.rd.autocode.spring.project.azure.service.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.blob.specialized.BlobClientBase;
import com.azure.storage.common.sas.SasProtocol;
import com.epam.rd.autocode.spring.project.exception.GenerateFileLinkException;
import com.epam.rd.autocode.spring.project.exception.UploadImageException;
import com.epam.rd.autocode.spring.project.azure.property.AzureBlobProperties;
import com.epam.rd.autocode.spring.project.azure.service.BlobService;
import com.epam.rd.autocode.spring.project.util.Boxed;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlobServiceImpl implements BlobService {

    private final BlobContainerClient blobContainerClient;
    private final AzureBlobProperties azureBlobProperties;

    @Override
    public String generateLink(String fileName) {
        return Boxed
                .of(BlobSasPermission.parse("r"))
                .map(this::getSasSignature)
                .map(blobSasPermission -> Pair.of(blobContainerClient.getBlobClient(fileName),
                        blobSasPermission))
                .map(pair -> pair.getFirst().getBlobUrl() + "?" +
                             pair.getFirst().generateSas(pair.getSecond()))
                .orElseThrow(() -> new GenerateFileLinkException("Couldn't generate link for file %s"
                        .formatted(fileName)));
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID() + multipartFile.getContentType().replaceAll(".*/", ".");
        return Boxed
                .of(fileName)
                .map(blobContainerClient::getBlobClient)
                .filter(blobClient -> !blobClient.exists())
                .doWith(blobClient -> blobClient.upload(toInputStream(multipartFile), multipartFile.getSize()))
                .map(BlobClient::getBlobName)
                .orElseThrow(() -> new UploadImageException("Image with %s name already exists!"));
    }

    @SneakyThrows
    private InputStream toInputStream(MultipartFile multipartFile) {
        return multipartFile.getInputStream();
    }

    private BlobServiceSasSignatureValues getSasSignature(BlobSasPermission blobSasPermission) {
        return new BlobServiceSasSignatureValues(OffsetDateTime.now().plusSeconds(
                azureBlobProperties.linkExpiration()), blobSasPermission)
                .setProtocol(SasProtocol.HTTPS_ONLY)
                .setCacheControl("no-cache");
    }

    @Override
    public void deleteImage(String fileName) {
        Boxed
                .of(fileName)
                .map(blobContainerClient::getBlobClient)
                .ifPresent(BlobClientBase::deleteIfExists);
    }
}
