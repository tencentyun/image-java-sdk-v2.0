package com.qcloud.image.http;

import com.qcloud.image.ClientConfig;
import com.qcloud.image.exception.AbstractImageException;
import com.qcloud.image.exception.ParamException;

public abstract class AbstractImageHttpClient {

    ClientConfig config;

    AbstractImageHttpClient(ClientConfig config) {
        super();
        this.config = config;
    }
    

    protected abstract String sendPostRequest(HttpRequest httpRequest) throws AbstractImageException;


    public String sendHttpRequest(HttpRequest httpRequest) throws AbstractImageException {

        HttpMethod method = httpRequest.getMethod();
        if (method == HttpMethod.POST) {
            return sendPostRequest(httpRequest);
        } else {
            throw new ParamException("Unsupported Http Method");
        }
    }

    public abstract void shutdown();
}
