package com.qcloud.image.http;

import com.qcloud.image.ClientConfig;
import com.qcloud.image.exception.AbstractImageException;
import com.qcloud.image.exception.ParamException;
import com.qcloud.image.exception.ServerException;
import com.qcloud.image.exception.UnknownException;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * @author chengwu 封装Http发送请求类
 */
public class DefaultImageHttpClient extends AbstractImageHttpClient {

    private OkHttpClient mOkHttpClient = new OkHttpClient();

    public DefaultImageHttpClient(ClientConfig config) {
        super(config);
    }

    @Override
    public void shutdown() {
        mOkHttpClient.getDispatcher().getExecutorService().shutdown();   //清除并关闭线程池
        ConnectionPool connectionPool = mOkHttpClient.getConnectionPool();
        if (connectionPool!=null) {
            connectionPool.evictAll();                 //清除并关闭连接池
        }
        try {
            Cache cache = mOkHttpClient.getCache();
            if (cache!=null) {
                cache.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String sendPostRequest(HttpRequest httpRequest) throws AbstractImageException {
        mOkHttpClient.setProxy(config.getProxy());
        mOkHttpClient.setConnectTimeout(config.getConnectionTimeout(), TimeUnit.MILLISECONDS);
        mOkHttpClient.setReadTimeout(config.getSocketTimeout(), TimeUnit.MILLISECONDS);
        mOkHttpClient.setWriteTimeout(config.getSocketTimeout(),TimeUnit.MILLISECONDS);

        Map<String, Object> params = httpRequest.getParams();
        
        if (httpRequest.getContentType() == HttpContentType.APPLICATION_JSON) {
            String postBody = new JSONObject(params).toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postBody);
            return doRequest(httpRequest, requestBody);

        } else if (httpRequest.getContentType() == HttpContentType.MULTIPART_FORM_DATA) {
            Map<String, File> imageList;//File形式的图片
            Map<String, byte[]> bytesContentList = httpRequest.getBytesContentList();//byte[]形式的图片
            if (bytesContentList != null && bytesContentList.size() > 0) {//优先使用byte[]形式的图片
                imageList = Collections.emptyMap();//如果有byte[]形式的图片, 则清空File形式的图片
            } else {
                imageList = httpRequest.getImageList();
            }
            MultipartBuilder multipartBuilder = new MultipartBuilder();
            try {
                setMultiPartEntity(multipartBuilder, params, imageList, bytesContentList);
            } catch (FileNotFoundException e) {
                throw new ParamException(e);
            }
            RequestBody requestBody = multipartBuilder.build();
            return doRequest(httpRequest, requestBody);
        } else {
            throw new ParamException("Unknown ContentType, httpRequest.getContentType():" + httpRequest.getContentType());
        }
    }

    private String doRequest(HttpRequest httpRequest, RequestBody requestBody) throws ServerException, UnknownException {
        Builder requestBuilder = new Builder()
                .url(httpRequest.getUrl())
                .post(requestBody);

        for (Entry<String, String> kv : httpRequest.getHeaders().entrySet()) {
            requestBuilder.addHeader(kv.getKey(), kv.getValue());
        }
        
        Response response;
        try {
            response = mOkHttpClient.newCall(requestBuilder.build()).execute();
        } catch (IOException e) {
            throw new ServerException(e);
        }

        String string;
        if (response.isSuccessful()) {
            try {
                string = response.body().string();
            } catch (IOException e) {
                String msg = String.format("IOException while reading response string, com.squareup.okhttp.Response = %s, IOException msg = %s", response.toString(), e.getMessage());
                throw new ServerException(msg);
            }
            try {
                new JSONObject(string);
            } catch (JSONException e) {
                throw new UnknownException("response is not json: " + string, e);
            }

        } else {// HTTP Code Error
            try {
                string = response.body().string();
            } catch (IOException e) {
                String msg = String.format("Unexpected response code and IOException while reading response string, com.squareup.okhttp.Response = %s, IOException msg = %s", response.toString(), e.getMessage());
                throw new ServerException(msg);
            }
        }
        return string;
    }

    private void setMultiPartEntity(MultipartBuilder multipartBuilder, Map<String, Object> params, Map<String, File> files, Map<String, byte[]> fileContents)
            throws FileNotFoundException {

        multipartBuilder.type(MultipartBuilder.FORM);
        for (String paramKey : params.keySet()) {
            multipartBuilder.addFormDataPart(paramKey, String.valueOf(params.get(paramKey)));
        }

        if (files.size() > 0) {
            for (String key : files.keySet()) {
                File file = files.get(key);
                if (file == null) {
                    throw new FileNotFoundException("File is null: " + key);
                }
                if (!file.exists()) {
                    throw new FileNotFoundException("File Not Exists: " + file.getAbsolutePath());
                }
                multipartBuilder.addPart(
                        Headers.of("Content-Disposition", String.format("form-data; name=\"%s\"; filename=\"%s\"", key, file.getName())),
                        RequestBody.create(MediaType.parse("image/jpg"), file)
                );
            }
        }

        for (String key : fileContents.keySet()) {
            byte[] content = fileContents.get(key);
            if (content != null && content.length > 0) {
                multipartBuilder.addPart(
                        //TODO file name not resolved
                        Headers.of("Content-Disposition", String.format("form-data; name=\"%s\"; filename=\"%s\"", key, "bytes" + System.currentTimeMillis())),
                        RequestBody.create(MediaType.parse("image/jpg"), content)
                );
            }
        }
        
    }
}
