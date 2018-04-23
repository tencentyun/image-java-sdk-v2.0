package com.qcloud.image.http;

import com.qcloud.image.ClientConfig;
import com.qcloud.image.exception.AbstractImageException;
import com.qcloud.image.exception.ParamException;
import com.qcloud.image.exception.ServerException;
import com.qcloud.image.exception.UnknownException;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
    protected String sendGetRequest(HttpRequest httpRequest) throws AbstractImageException {
        mOkHttpClient.setProxy(config.getProxy());
                
        mOkHttpClient.setConnectTimeout(config.getConnectionTimeout(), TimeUnit.MILLISECONDS);
        mOkHttpClient.setReadTimeout(config.getSocketTimeout(), TimeUnit.MILLISECONDS);   
        mOkHttpClient.setWriteTimeout(config.getSocketTimeout(),TimeUnit.MILLISECONDS);

        //url
        HttpUrl.Builder urlBuilder = HttpUrl.parse(httpRequest.getUrl()).newBuilder();
        for (String paramKey : httpRequest.getParams().keySet()) {
            urlBuilder.addQueryParameter(paramKey, String.valueOf(httpRequest.getParams().get(paramKey)));
        }
        //request
        Builder requestBuilder = new Builder().url(urlBuilder.build());
        //header
        Map<String, String> headers = httpRequest.getHeaders();
        for (String headerKey : headers.keySet()) {
            requestBuilder.addHeader(headerKey, headers.get(headerKey));
        }
        //call
        Request request = requestBuilder.build();
        Response response = null;
        try {
            response = mOkHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new ServerException(e.getMessage());
        }
        String string = "";
        try {
            string = response.body().string();
        } catch (IOException e) {
            throw new ServerException(e.getMessage());
        }
        try {
            new JSONObject(string);
        } catch (JSONException e) {
            throw new UnknownException(e.getMessage());
        }
        return string;
    }

    @Override
    public void shutdown() {
    }

    @Override
    protected String sendPostRequest(HttpRequest httpRequest) throws AbstractImageException {
        mOkHttpClient.setProxy(config.getProxy());
        mOkHttpClient.setConnectTimeout(config.getConnectionTimeout(), TimeUnit.MILLISECONDS);
        mOkHttpClient.setReadTimeout(config.getSocketTimeout(), TimeUnit.MILLISECONDS);
        mOkHttpClient.setWriteTimeout(config.getSocketTimeout(),TimeUnit.MILLISECONDS);

        if (httpRequest.getContentType() == HttpContentType.APPLICATION_JSON) {
            Map<String, Object> params = httpRequest.getParams();
            JSONObject root = new JSONObject(params);
            String postBody = root.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postBody);
            Builder requestBuilder = new Builder()
                    .url(httpRequest.getUrl())
                    .post(requestBody);
            Map<String, String> headers = httpRequest.getHeaders();
            for (String headerKey : headers.keySet()) {
                requestBuilder.addHeader(headerKey, headers.get(headerKey));
            }
            Response response = null;
            try {
                response = mOkHttpClient.newCall(requestBuilder.build()).execute();
            } catch (IOException e) {
                throw new ServerException(e.getMessage());
            }
            if (!response.isSuccessful()) {
                String string = null;
                try {
                    string = response.body().string();
                } catch (IOException e) {
                    throw new ServerException(e.getMessage());
                }
                String msg = String.format("Unexpected response: %s, content: %s", response, string);
                throw new ServerException(msg);
            }

            String string = null;
            try {
                string = response.body().string();
            } catch (IOException e) {
                throw new ServerException(e.getMessage());
            }
            try {
                new JSONObject(string);
            } catch (JSONException e) {
                throw new UnknownException(e.getMessage());
            }
            return string;

        } else if (httpRequest.getContentType() == HttpContentType.MULTIPART_FORM_DATA) {
            HashMap<String, String> keyList = httpRequest.getKeyList();
            HashMap<String, File> imageList = httpRequest.getImageList();
            String imageKey = httpRequest.getImageKey();
            File image = httpRequest.getImage();
            Map<String, Object> params = httpRequest.getParams();
            MultipartBuilder multipartBuilder = new MultipartBuilder();
            try {
                setMultiPartEntity(multipartBuilder, params, imageList, httpRequest.getBytesContentList(), image, keyList, imageKey);
            } catch (FileNotFoundException e) {
                throw new ParamException(e.getMessage());
            }
            RequestBody requestBody = multipartBuilder.build();
            Builder requestBuilder = new Builder()
                    .url(httpRequest.getUrl())
                    .post(requestBody);
            Map<String, String> headers = httpRequest.getHeaders();
            for (String headerKey : headers.keySet()) {
                requestBuilder.addHeader(headerKey, headers.get(headerKey));
            }
            Response response = null;
            try {
                response = mOkHttpClient.newCall(requestBuilder.build()).execute();
            } catch (IOException e) {
                throw new ServerException(e.getMessage());
            }
            if (!response.isSuccessful()) {
                String string = null;
                try {
                    string = response.body().string();
                } catch (IOException e) {
                    throw new ServerException(e.getMessage());
                }
                String msg = String.format("Unexpected response: %s, content: %s", response, string);
                throw new ServerException(msg);
            }
            String string = null;
            try {
                string = response.body().string();
            } catch (IOException e) {
                throw new ServerException(e.getMessage());
            }
            try {
                new JSONObject(string);
            } catch (JSONException e) {
                throw new UnknownException(e.getMessage());
            }
            return string;
        } else {
            throw new ParamException("Unknown ContentType, httpRequest.getContentType():" + httpRequest.getContentType());
        }
    }

    private void setMultiPartEntity(MultipartBuilder multipartBuilder, Map<String, Object> params, Map<String, File> images, Map<String, byte[]> contentList,File imageData, Map<String, String> keyList, String imageKey)
            throws FileNotFoundException {

        multipartBuilder.type(MultipartBuilder.FORM);
        for (String paramKey : params.keySet()) {
            multipartBuilder.addFormDataPart(paramKey, String.valueOf(params.get(paramKey)));
        }

        if (images.size() > 0) {
            for (String imageFileName : images.keySet()) {
                String paramName = keyList.get(imageFileName);
                File imageFile = images.get(imageFileName);
                if (imageFile == null) {
                    throw new FileNotFoundException("File is null: " + imageFileName);
                }
                if (!imageFile.exists()) {
                    throw new FileNotFoundException("File Not Exists: " + imageFile.getAbsolutePath());
                }
                multipartBuilder.addPart(
                        Headers.of("Content-Disposition", String.format("form-data; name=\"%s\"", paramName)),
                        RequestBody.create(MediaType.parse("image/jpg"), imageFile)
                );
            }
        } else if (imageData != null) {
            if (imageData.exists()) {
                multipartBuilder.addPart(
                        Headers.of("Content-Disposition", String.format("form-data; name=\"%s\"", imageKey)),
                        RequestBody.create(MediaType.parse("image/jpg"), imageData)
                );
            } else {
                throw new FileNotFoundException("File Not Exists: " + imageData.getAbsolutePath());
            }
        }

        for (String key : contentList.keySet()) {
            byte[] content = contentList.get(key);
            if (content != null && content.length > 0) {
                multipartBuilder.addPart(
                        Headers.of("Content-Disposition", String.format("form-data; name=\"%s\"", key)),
                        RequestBody.create(MediaType.parse("image/jpg"), content)
                );
            }
        }
        
    }
}
