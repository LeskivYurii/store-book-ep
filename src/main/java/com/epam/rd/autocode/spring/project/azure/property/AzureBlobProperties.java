package com.epam.rd.autocode.spring.project.azure.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("azure.storage")
public record AzureBlobProperties (String connectionString, String containerName, long linkExpiration){
}
