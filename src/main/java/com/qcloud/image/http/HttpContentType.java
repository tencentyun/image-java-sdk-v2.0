package com.qcloud.image.http;

public enum HttpContentType {
    
    APPLICATION_JSON("application/json"), 
    
    MULTIPART_FORM_DATA("multipart/form-data");
    
    private String contentType;

    private HttpContentType(String contentType) {
        this.contentType = contentType;
    }
    
    @Override
    public String toString() {
        return this.contentType;
    }
}
