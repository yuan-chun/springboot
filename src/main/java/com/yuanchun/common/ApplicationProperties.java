package com.yuanchun.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private String imageUploadPath;

    public String getImageUploadPath() {
        return imageUploadPath;
    }

    public void setImageUploadPath(String imageUploadPath) {
        this.imageUploadPath = imageUploadPath;
    }
}
