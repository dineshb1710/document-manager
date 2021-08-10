package com.dinesh.app.dm.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("azure.storage")
public class AzureFileUploadConfigurationProperties {

    private String accountName;
    private String connectionString;
    private String containerName;
}
