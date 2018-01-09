package com.qcloud.image.http;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import com.qcloud.image.ClientConfig;
import com.qcloud.image.exception.AbstractImageException;
import com.qcloud.image.exception.ParamException;
import com.qcloud.image.exception.ServerException;
import com.qcloud.image.exception.UnknownException;

/**
 * @author chengwu 封装Http发送请求类
 */
public class DefaultImageHttpClient extends AbstractImageHttpClient {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultImageHttpClient.class);

    public DefaultImageHttpClient(ClientConfig config) {
        super(config);
    }

    // 获得异常发生时的返回信息
    private String getExceptionMsg(HttpRequest httpRequest, String exceptionStr) {
        String errMsg = new StringBuilder("HttpRequest:").append(httpRequest.toString())
                .append("\nException:").append(exceptionStr).toString();
        LOG.error(errMsg);
        return errMsg;
    }

    @Override
    protected String sendGetRequest(HttpRequest httpRequest) throws AbstractImageException {
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

    }

    @Override
    protected String sendPostRequest(HttpRequest httpRequest) throws AbstractImageException {
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
                    setMultiPartEntity(httpPost, params, httpRequest.getImageList(), httpRequest.getImage(), httpRequest.getKeyList(), httpRequest.getImageKey());
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
    }

    private void setJsonEntity(HttpPost httpPost, Map<String, Object> params) {
        ContentType utf8TextPlain = ContentType.create("text/plain", Consts.UTF_8);
        JSONObject root = new JSONObject(params);
        StringEntity stringEntity = new StringEntity(root.toString(), utf8TextPlain);
        httpPost.setEntity(stringEntity);
    }

    private void setMultiPartEntity(HttpPost httpPost, Map<String, Object> params, Map<String, File> images, File imageData, Map<String, String> keyList, String imageKey)
            throws Exception {
        ContentType utf8TextPlain = ContentType.create("text/plain", Consts.UTF_8);
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        for (String paramKey : params.keySet()) {
            entityBuilder.addTextBody(paramKey, String.valueOf(params.get(paramKey)), utf8TextPlain);
        }

        if (images.size() > 0) {
            for (String imageFileName : images.keySet()) {
                String paramName = keyList.get(imageFileName);
                File imageFile = images.get(imageFileName);
                entityBuilder.addBinaryBody(paramName, imageFile, ContentType.MULTIPART_FORM_DATA, imageFileName);
            }
        } else if (imageData != null && imageData.exists()) {
            entityBuilder.addBinaryBody(imageKey, imageData);
        }

        httpPost.setEntity(entityBuilder.build());
    }

    /**
     * 设置Http头部，同时添加上公共的类型，长连接，Image SDK标识
     * @param message HTTP消息
     * @param headers 用户额外添加的HTTP头部
     */
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

}
