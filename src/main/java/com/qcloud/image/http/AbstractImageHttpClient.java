package com.qcloud.image.http;

import com.qcloud.image.ClientConfig;
import com.qcloud.image.exception.AbstractImageException;
import com.qcloud.image.exception.ParamException;

public abstract class AbstractImageHttpClient {

    protected ClientConfig config;
/*    protected HttpClient httpClient;
    
    private PoolingHttpClientConnectionManager connectionManager;
    private IdleConnectionMonitorThread idleConnectionMonitor;
    
    private RequestConfig requestConfig;*/

    public AbstractImageHttpClient(ClientConfig config) {
        super();
        this.config = config;
/*        this.connectionManager = new PoolingHttpClientConnectionManager();
        this.connectionManager.setMaxTotal(config.getMaxConnectionsCount());
        this.connectionManager.setDefaultMaxPerRoute(config.getMaxConnectionsCount());
        this.httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        this.idleConnectionMonitor = new IdleConnectionMonitorThread(this.connectionManager);
        this.idleConnectionMonitor.start();*/
    }
    
/*    protected RequestConfig onGetConfig(){   
        this.requestConfig =  RequestConfig.custom()
            .setConnectionRequestTimeout(this.config.getConnectionRequestTimeout())
            .setConnectTimeout(this.config.getConnectionTimeout())
            .setSocketTimeout(this.config.getSocketTimeout())
            .setProxy(this.config.getProxy())
            .build();
        return this.requestConfig;}*/

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

    public abstract void shutdown();/* {
        this.idleConnectionMonitor.shutdown();
    }*/
}
