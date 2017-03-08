package com.qcloud.image.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.qcloud.image.ClientConfig;
import com.qcloud.image.exception.AbstractImageException;
import com.qcloud.image.exception.ParamException;

public abstract class AbstractImageHttpClient {
    protected ClientConfig config;
    protected HttpClient httpClient;
    
    protected PoolingHttpClientConnectionManager connectionManager;
    protected IdleConnectionMonitorThread idleConnectionMonitor;
    
    protected RequestConfig requestConfig;

    public AbstractImageHttpClient(ClientConfig config) {
        super();
        this.config = config;
        this.connectionManager = new PoolingHttpClientConnectionManager();
        this.connectionManager.setMaxTotal(config.getMaxConnectionsCount());
        this.connectionManager.setDefaultMaxPerRoute(config.getMaxConnectionsCount());
        this.httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        this.requestConfig = RequestConfig.custom()
                                          .setConnectionRequestTimeout(this.config.getConnectionRequestTimeout())
                                          .setConnectTimeout(this.config.getConnectionTimeout())
                                          .setSocketTimeout(this.config.getSocketTimeout())
                                          .build();
        this.idleConnectionMonitor = new IdleConnectionMonitorThread(this.connectionManager);
        this.idleConnectionMonitor.start();
    }

    protected abstract String sendPostRequest(HttpRequest httpRequest) throws AbstractImageException;

    protected abstract String sendGetRequest(HttpRequest httpRequest) throws AbstractImageException;

    public String sendHttpRequest(HttpRequest httpRequest) throws AbstractImageException {

        HttpMethod method = httpRequest.getMethod();
        if (method == HttpMethod.POST) {
            return sendPostRequest(httpRequest);
        } else if (method == HttpMethod.GET) {
            return sendGetRequest(httpRequest);
        } else {
            throw new ParamException("Unsupported Http Method");
        }
    }
    
    public void shutdown() {
        this.idleConnectionMonitor.shutdown();
    }
}
