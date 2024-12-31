package com.epam.rd.autocode.spring.project.azure;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.epam.rd.autocode.spring.project.azure.property.AzureBlobProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BlobConfiguration {

    private final AzureBlobProperties azureBlobProperties;

    @Bean
    public BlobServiceClient blobServiceClient() {
        return new BlobServiceClientBuilder()
                .connectionString(azureBlobProperties.connectionString())
                .buildClient();
    }

    @Bean
    public BlobContainerClient blobClient(BlobServiceClient blobServiceClient) {
        return blobServiceClient.getBlobContainerClient(azureBlobProperties.containerName());
    }

}
