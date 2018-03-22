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

import org.apache.http.HttpHost;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chengwu 封装Http发送请求类
 */
public class DefaultImageHttpClient extends AbstractImageHttpClient {

    //private static final Logger LOG = LoggerFactory.getLogger(DefaultImageHttpClient.class);

    private OkHttpClient mOkHttpClient = new OkHttpClient();

    public DefaultImageHttpClient(ClientConfig config) {
        super(config);
    }

    // 获得异常发生时的返回信息
/*    private String getExceptionMsg(HttpRequest httpRequest, String exceptionStr) {
        String errMsg = new StringBuilder("HttpRequest:").append(httpRequest.toString())
                .append("\nException:").append(exceptionStr).toString();
        LOG.error(errMsg);
        return errMsg;
    }*/

    @Override
    protected String sendGetRequest(HttpRequest httpRequest) throws AbstractImageException {
        HttpHost httpHost = config.getProxy();
        Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(httpHost.getHostName(), httpHost.getPort()));
        mOkHttpClient.setProxy(proxy);
        
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

/*
        String url = httpRequest.getUrl();
        HttpGet httpGet = null;
        String responseStr = "";
        int retry = 0;
        int maxRetryCount = this.config.getMaxFailedRetry();
        while (retry < maxRetryCount) {
            try {
                URIBuilder urlBuilder = new URIBuilder(url);
                for (String paramKey : httpRequest.getParams().keySet()) {
                    urlBuilder.addParameter(paramKey, String.valueOf(httpRequest.getParams().get(paramKey)));
                }
                httpGet = new HttpGet(urlBuilder.build());
            } catch (URISyntaxException e) {
                String errMsg = "Invalid url:" + url;
                LOG.error(errMsg);
                throw new ParamException(errMsg);
            }

            httpGet.setConfig(onGetConfig());
            setHeaders(httpGet, httpRequest.getHeaders());

            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                responseStr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                new JSONObject(responseStr);
                return responseStr;
            } catch (ParseException | IOException e) {
                ++retry;
                if (retry == maxRetryCount) {
                    String errMsg = getExceptionMsg(httpRequest, e.toString());
                    throw new ServerException(errMsg);
                }
            } catch (JSONException e) {
                String errMsg = getExceptionMsg(httpRequest, e.toString());
                throw new ServerException(errMsg);
            } finally {
                httpGet.releaseConnection();
            }
        }
        return responseStr;
*/

    }

    @Override
    public void shutdown() {
        //mOkHttpClient.
    }

    @Override
    protected String sendPostRequest(HttpRequest httpRequest) throws AbstractImageException {
        HttpHost httpHost = config.getProxy();
        Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(httpHost.getHostName(), httpHost.getPort()));
        mOkHttpClient.setProxy(proxy);
        
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
                throw new ServerException("Unexpected code " + response);
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
                setMultiPartEntity(multipartBuilder, params, imageList, image, keyList, imageKey);
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
                throw new ServerException("Unexpected code " + response);
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
        
/*
        String url = httpRequest.getUrl();
        String responseStr = "";
        int retry = 0;
        int maxRetryCount = this.config.getMaxFailedRetry();
        while (retry < maxRetryCount) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(onGetConfig());

            Map<String, Object> params = httpRequest.getParams();
            setHeaders(httpPost, httpRequest.getHeaders());
            if (httpRequest.getContentType() == HttpContentType.APPLICATION_JSON) {
                setJsonEntity(httpPost, params);
            } else if (httpRequest.getContentType() == HttpContentType.MULTIPART_FORM_DATA) {
                try {
                    HashMap<String, String> keyList = httpRequest.getKeyList();
                    HashMap<String, File> imageList = httpRequest.getImageList();
                    String imageKey = httpRequest.getImageKey();
                    File image = httpRequest.getImage();
                    setMultiPartEntity(httpPost, params, imageList, image, keyList, imageKey);
                } catch (Exception e) {
                    throw new UnknownException(e.toString());
                }
            }

            try {
                HttpResponse httpResponse = httpClient.execute(httpPost);
                responseStr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                new JSONObject(responseStr);
                return responseStr;
            } catch (ParseException | IOException e) {
                ++retry;
                if (retry == maxRetryCount) {
                    String errMsg = getExceptionMsg(httpRequest, e.toString());
                    throw new ServerException(errMsg);
                }
            } catch (JSONException e) {
                String errMsg = getExceptionMsg(httpRequest, e.toString());
                throw new ServerException(errMsg);
            } finally {
                httpPost.releaseConnection();
            }
        }
        return responseStr;
*/
    }

    /*
    private void setJsonEntity(HttpPost httpPost, Map<String, Object> params) {
        ContentType utf8TextPlain = ContentType.create("text/plain", Consts.UTF_8);
        JSONObject root = new JSONObject(params);
        StringEntity stringEntity = new StringEntity(root.toString(), utf8TextPlain);
        httpPost.setEntity(stringEntity);
    }
    */

    private void setMultiPartEntity(MultipartBuilder multipartBuilder,/*HttpPost httpPost,*/ Map<String, Object> params, Map<String, File> images, File imageData, Map<String, String> keyList, String imageKey)
            throws FileNotFoundException {
        //ContentType utf8TextPlain = ContentType.create("text/plain", Consts.UTF_8);
        //MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();

        multipartBuilder.type(MultipartBuilder.FORM);
        for (String paramKey : params.keySet()) {
            //entityBuilder.addTextBody(paramKey, String.valueOf(params.get(paramKey)), utf8TextPlain);
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
                //entityBuilder.addBinaryBody(paramName, imageFile);
                multipartBuilder.addPart(
                        Headers.of("Content-Disposition", String.format("form-data; name=\"%s\"", paramName)),
                        RequestBody.create(MediaType.parse("image/jpg"), imageFile)
                );
            }
        } else if (imageData != null) {
            if (imageData.exists()) {
                //entityBuilder.addBinaryBody(imageKey, imageData);
                multipartBuilder.addPart(
                        Headers.of("Content-Disposition", String.format("form-data; name=\"%s\"", imageKey)),
                        RequestBody.create(MediaType.parse("image/jpg"), imageData)
                );
            } else {
                throw new FileNotFoundException("File Not Exists: " + imageData.getAbsolutePath());
            }
        }

        //HttpEntity entity = entityBuilder.build();
        //httpPost.setEntity(entity);
    }

    /**
     * 设置Http头部，同时添加上公共的类型，长连接，Image SDK标识
     * @param message HTTP消息
     * @param headers 用户额外添加的HTTP头部
     */
/*    
    private void setHeaders(HttpMessage message, Map<String, String> headers) {
        message.setHeader(RequestHeaderKey.ACCEPT, RequestHeaderValue.Accept.ALL);
        message.setHeader(RequestHeaderKey.CONNECTION, RequestHeaderValue.Connection.KEEP_ALIVE);
        message.setHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        if (headers != null) {
            for (String headerKey : headers.keySet()) {
                message.setHeader(headerKey, headers.get(headerKey));
            }
        }
    }
*/

}
