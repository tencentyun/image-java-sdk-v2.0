package com.qcloud.image.http;

public enum HttpMethod {
    
    GET("GET"), 
    
    POST("POST"), 
    
    PUT("PUT"), 
    
    DELETE("DELETE");

    private String method;

    private HttpMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return this.method;
    }
}
