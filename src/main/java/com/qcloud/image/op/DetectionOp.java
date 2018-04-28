/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcloud.image.op;

import com.qcloud.image.ClientConfig;
import com.qcloud.image.common_utils.CommonCodecUtils;
import com.qcloud.image.common_utils.CommonFileUtils;
import com.qcloud.image.exception.AbstractImageException;
import com.qcloud.image.exception.ParamException;
import com.qcloud.image.http.AbstractImageHttpClient;
import com.qcloud.image.http.HttpContentType;
import com.qcloud.image.http.HttpMethod;
import com.qcloud.image.http.HttpRequest;
import com.qcloud.image.http.RequestBodyKey;
import com.qcloud.image.http.RequestHeaderKey;
import com.qcloud.image.request.AbstractBaseRequest.BytesContent;
import com.qcloud.image.request.FaceAddFaceRequest;
import com.qcloud.image.request.FaceAddGroupIdsRequest;
import com.qcloud.image.request.FaceCompareRequest;
import com.qcloud.image.request.FaceDelFaceRequest;
import com.qcloud.image.request.FaceDelGroupIdsRequest;
import com.qcloud.image.request.FaceDelPersonRequest;
import com.qcloud.image.request.FaceDetectRequest;
import com.qcloud.image.request.FaceGetFaceIdsRequest;
import com.qcloud.image.request.FaceGetFaceInfoRequest;
import com.qcloud.image.request.FaceGetGroupIdsRequest;
import com.qcloud.image.request.FaceGetInfoRequest;
import com.qcloud.image.request.FaceGetPersonIdsRequest;
import com.qcloud.image.request.FaceIdCardCompareRequest;
import com.qcloud.image.request.FaceIdCardLiveDetectFourRequest;
import com.qcloud.image.request.FaceIdentifyRequest;
import com.qcloud.image.request.FaceLiveDetectFourRequest;
import com.qcloud.image.request.FaceLiveDetectPictureRequest;
import com.qcloud.image.request.FaceLiveGetFourRequest;
import com.qcloud.image.request.FaceMultiIdentifyRequest;
import com.qcloud.image.request.FaceNewPersonRequest;
import com.qcloud.image.request.FaceSetInfoRequest;
import com.qcloud.image.request.FaceShapeRequest;
import com.qcloud.image.request.FaceVerifyRequest;
import com.qcloud.image.request.GeneralOcrRequest;
import com.qcloud.image.request.IdcardDetectRequest;
import com.qcloud.image.request.NamecardDetectRequest;
import com.qcloud.image.request.OcrBankCardRequest;
import com.qcloud.image.request.OcrBizLicenseRequest;
import com.qcloud.image.request.OcrDrivingLicenceRequest;
import com.qcloud.image.request.OcrPlateRequest;
import com.qcloud.image.request.PornDetectRequest;
import com.qcloud.image.request.TagDetectRequest;
import com.qcloud.image.sign.Credentials;
import com.qcloud.image.sign.Sign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;

import static com.qcloud.image.ClientConfig.OCR_BANKCARD;
import static com.qcloud.image.ClientConfig.OCR_BIZLICENSE;
import static com.qcloud.image.ClientConfig.OCR_DRIVINGLICENCE;
import static com.qcloud.image.ClientConfig.OCR_GENERAL;
import static com.qcloud.image.ClientConfig.OCR_PLATE;

/**
 *
 * @author jusisli 此类封装了图片识别操作
 */
public class DetectionOp extends BaseOp {
    private static final Logger LOG = LoggerFactory.getLogger(DetectionOp.class);

    public DetectionOp(ClientConfig config, Credentials cred, AbstractImageHttpClient client) {
        super(config, cred, client);
    }
    
    /**
     * 黄图识别请求
     * 
     * @param request 黄图识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String pornDetect(PornDetectRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getDetectionPorn();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.setMethod(HttpMethod.POST);
        if (request.isUrl()) {
            httpRequest.addParam(RequestBodyKey.URL_LIST, (request.getUrlList())); 
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON); 
        } else {         
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            HashMap<String, String> keys = request.getKeyList();
            HashMap<String, File> images = request.getImageList();
            for (String k : keys.keySet()) {
                httpRequest.addFile(keys.get(k), images.get(k));
            }
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
        /**
     * 标签识别请求
     * 
     * @param request 标签识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String tagDetect(TagDetectRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getDetectionTag();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON); 
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
        } else {
            byte[] fileContentByte = null;
            try {
                fileContentByte = CommonFileUtils.getFileContentByte(request.getImage().getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            String image = CommonCodecUtils.Base64Encode(fileContentByte);  
            httpRequest.addParam(RequestBodyKey.IMAGE, image);                 
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 身份证识别请求
     * 
     * @param request 标签识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String idcardDetect(IdcardDetectRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getDetectionIdcard();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.CARD_TYPE, String.valueOf(request.getCardType()));
        
        httpRequest.setMethod(HttpMethod.POST);
        if (request.isUrl()) {
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON); 
            httpRequest.addParam(RequestBodyKey.URL_LIST, (request.getUrlList())); 
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            HashMap<String, String> keys = request.getKeyList();
            HashMap<String, File> images = request.getImageList();
            for (String k : keys.keySet()) {
                httpRequest.addFile(keys.get(k), images.get(k));
            }
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
     /**
     * 名片识别请求
     * 
     * @param request 标签识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String namecardDetect(NamecardDetectRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudOcrDomain() + this.config.getDetectionNamecard();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.setMethod(HttpMethod.POST);
        
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.RET_IMAGE, String.valueOf(request.getRetImage()));              
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL_LIST, (request.getUrlList())); 
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON); 
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            HashMap<String, String> keys = request.getKeyList();
            HashMap<String, File> images = request.getImageList();
            for (String k : keys.keySet()) {
                httpRequest.addFile(keys.get(k), images.get(k));
            }
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
     /**
     * 通用OCR
     * 
     * @param request 标签识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String generalOcr(GeneralOcrRequest request) throws AbstractImageException {
        request.check_param();

        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudOcrDomain()+ OCR_GENERAL;

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        if (request.isUrl()) {
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            httpRequest.addFile("image",request.getImage());
        }
        return httpClient.sendHttpRequest(httpRequest);

    }
     /**
     * OCR-行驶证驾驶证识别
     * 
     * @param request 标签识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String ocrDrivingLicence(OcrDrivingLicenceRequest request) throws AbstractImageException {
        request.check_param();

        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudOcrDomain()+ OCR_DRIVINGLICENCE;

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam("type", request.getType());
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            httpRequest.addFile("image",request.getImage());
        }
        return httpClient.sendHttpRequest(httpRequest);

    }
     /**
     * OCR-营业执照识别
     * 
     * @param request 标签识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String ocrBizLicense(OcrBizLicenseRequest request) throws AbstractImageException {
        request.check_param();

        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudOcrDomain()+ OCR_BIZLICENSE;

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            httpRequest.addFile("image",request.getImage());
        }
        return httpClient.sendHttpRequest(httpRequest);

    }
     /**
     * OCR-银行卡识别
     * 
     * @param request 标签识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String ocrBankCard(OcrBankCardRequest request) throws AbstractImageException {
        request.check_param();

        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudOcrDomain()+ OCR_BANKCARD;

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            httpRequest.addFile("image",request.getImage());
        }
        return httpClient.sendHttpRequest(httpRequest);

    }
     /**
     * OCR-车牌号识别
     * 
     * @param request 标签识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String ocrPlate(OcrPlateRequest request) throws AbstractImageException {
        request.check_param();

        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudOcrDomain()+ OCR_PLATE;

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            httpRequest.addFile("image",request.getImage());
        }
        return httpClient.sendHttpRequest(httpRequest);

    }
    
    /**
     * 人脸检测请求
     * 
     * @param request 人脸检测请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceDetect(FaceDetectRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getDetectionFace();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.setMethod(HttpMethod.POST);
        
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.MODE, (request.getMode()));        
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
        } else {
            httpRequest.addFile("image",request.getImage());
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 人脸定位请求
     * 
     * @param request 人脸定位请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceShape(FaceShapeRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceShape();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.MODE, request.getMode());
        
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
        } else {
            httpRequest.addFile("image",request.getImage());
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 个体创建请求
     * 
     * @param request 个体创建请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceNewPerson(FaceNewPersonRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceNewPerson();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.PERSON_ID, request.getPersonId());
        httpRequest.addParam(RequestBodyKey.PERSON_NAME, request.getPersonName());        
        httpRequest.addParam(RequestBodyKey.TAG, request.getPersonTag());
        httpRequest.setMethod(HttpMethod.POST);
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.addParam(RequestBodyKey.GROUP_IDS, request.getGroupIds());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            int index;
            String[] groupIds = request.getGroupIds();
            for (index = 0;index < groupIds.length; index++) {
                String key =  String.format("group_ids[%d]", index);
                String data = groupIds[index];
                httpRequest.addParam(key, data); 
            }
            httpRequest.addFile("image",request.getImage());
            BytesContent bytesContent = request.getBytesContent();
            if (bytesContent != null) {
                httpRequest.addBytes(bytesContent.getKey(), bytesContent.getContent());
            }
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 个体删除请求
     * 
     * @param request 个体删除请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceDelPerson(FaceDelPersonRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceDelPerson();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.PERSON_ID, request.getPersonId());
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
    
        return httpClient.sendHttpRequest(httpRequest);
    }
    
      /**
     * 增加人脸请求
     * 
     * @param request 增加人脸请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceAddFace(FaceAddFaceRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceAddFace();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.PERSON_ID, String.valueOf(request.getPersonId()));
        httpRequest.addParam(RequestBodyKey.TAG, String.valueOf(request.getPersonTag()));        
        httpRequest.setMethod(HttpMethod.POST);       
        if (request.isUrl()) {
            httpRequest.addParam(RequestBodyKey.URLS, (request.getUrlList()));  
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON); 
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            HashMap<String, String> keys = request.getKeyList();
            HashMap<String, File> images = request.getImageList();
            for (String k : keys.keySet()) {
                httpRequest.addFile(keys.get(k), images.get(k));
            }
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
     /**
     * 人脸删除请求
     * 
     * @param request 人脸删除请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceDelFace(FaceDelFaceRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceDelFace();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.PERSON_ID, request.getPersonId());
        httpRequest.addParam(RequestBodyKey.FACE_IDS, request.getFaceIds());
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
    
        return httpClient.sendHttpRequest(httpRequest);
    }
    
     /**
     * 人脸设置信息请求
     * 
     * @param request 人脸设置信息请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceSetInfo(FaceSetInfoRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceSetInfo();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.PERSON_ID, request.getPersonId());
        httpRequest.addParam(RequestBodyKey.PERSON_NAME, request.getPersonName());
        httpRequest.addParam(RequestBodyKey.TAG, request.getPersonTag());
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
    
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 人脸获取信息请求
     * 
     * @param request 人脸获取信息请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceGetInfo(FaceGetInfoRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceGetInfo();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.PERSON_ID, request.getPersonId());
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);      
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 获取组列表请求
     * 
     * @param request 获取组列表请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */
    public String faceGetGroupIds(FaceGetGroupIdsRequest request) throws AbstractImageException {
        request.check_param();

        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceGetGroupIdsInfo();

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);

        return httpClient.sendHttpRequest(httpRequest);
    }

    /**
     * Person新增组信息, 文档见 https://cloud.tencent.com/document/product/641/12417
     * @param useNewDomain 是否使用新域名，<br>
     * true: http://recognition.image.myqcloud.com/face/addgroupids <br>
     * false: http://service.image.myqcloud.com/face/addgroupids <br>
     * 如果开发者使用的是原域名（service.image.myqcloud.com）且已产生调用，则无需更换域名。
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     * message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */
    public String faceAddGroupIds(FaceAddGroupIdsRequest request, boolean useNewDomain) throws AbstractImageException {
        request.check_param();

        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = useNewDomain ? "http://recognition.image.myqcloud.com/face/addgroupids"
                : "http://service.image.myqcloud.com/face/addgroupids";

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setUrl(url);
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);

        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam("person_id", request.getPerson_id());
        httpRequest.addParam("group_ids", request.getGroup_ids());
        String session_id = request.getSession_id();
        if (session_id != null && !session_id.isEmpty()) {
            httpRequest.addParam("session_id", session_id);
        }

        return httpClient.sendHttpRequest(httpRequest);
    }

    /**
     * Person删除组信息, 文档见 https://cloud.tencent.com/document/product/641/12417
     * @param useNewDomain 是否使用新域名，<br>
     * true: http://recognition.image.myqcloud.com/face/delgroupids <br>
     * false: http://service.image.myqcloud.com/face/delgroupids <br>
     * 如果开发者使用的是原域名（service.image.myqcloud.com）且已产生调用，则无需更换域名。
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     * message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */
    public String faceDelGroupIds(FaceDelGroupIdsRequest request, boolean useNewDomain) throws AbstractImageException {
        request.check_param();

        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = useNewDomain ? "http://recognition.image.myqcloud.com/face/delgroupids"
                : "http://service.image.myqcloud.com/face/delgroupids";

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setUrl(url);
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);

        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam("person_id", request.getPerson_id());
        httpRequest.addParam("group_ids", request.getGrooup_ids());
        String session_id = request.getSession_id();
        if (session_id != null && !session_id.isEmpty()) {
            httpRequest.addParam("session_id", session_id);
        }

        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     *  获取人列表请求
     * 
     * @param request  获取人列表请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceGetPersonIds(FaceGetPersonIdsRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceGetPersonIdsInfo();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.GROUP_ID, request.getGroupId());
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
    
        return httpClient.sendHttpRequest(httpRequest);
    }
    
     /**
     *  获取人脸列表请求
     * 
     * @param request  获取人脸列表请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceGetFaceIds(FaceGetFaceIdsRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceGetFaceIdsInfo();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.PERSON_ID, request.getPersonId());
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
    
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     *  获取人脸信息请求
     * 
     * @param request  获取人脸信息请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceGetFaceInfo(FaceGetFaceInfoRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceGetFaceInfo();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.FACE_ID, request.getFaceId());
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
    
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 人脸识别请求
     * 
     * @param request 人脸识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceIdentify(FaceIdentifyRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceIdentify();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.setMethod(HttpMethod.POST);

        String groupId = request.getGroupId();
        String[] groupIds = request.getGroupIds();
        
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
            if (groupId != null && !groupId.isEmpty()) {
                httpRequest.addParam(RequestBodyKey.GROUP_ID, groupId);
            } else if (groupIds != null && groupIds.length > 0) {
                httpRequest.addParam(RequestBodyKey.GROUP_IDS, request.getGroupIds());
            } else {
                throw new ParamException("groupId and groupIds both null or empty!!");
            }
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            httpRequest.addFile("image",request.getImage());
            BytesContent bytesContent = request.getBytesContent();
            if (bytesContent != null) {
                httpRequest.addBytes(bytesContent.getKey(), bytesContent.getContent());
            }
            if (groupId != null && !groupId.isEmpty()) {
                httpRequest.addParam(RequestBodyKey.GROUP_ID, groupId);
            } else if (groupIds != null && groupIds.length > 0) {
                int index;
                for (index = 0; index < groupIds.length; index++) {
                    String key = String.format("group_ids[%d]", index);
                    String data = groupIds[index];
                    httpRequest.addParam(key, data);
                }
            } else {
                throw new ParamException("groupId and groupIds both null or empty!!");
            }
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
     /**
     * 人脸验证请求
     * 
     * @param request 人脸验证请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceVerify(FaceVerifyRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceVerify();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.PERSON_ID, (request.getPersonId()));
        httpRequest.setMethod(HttpMethod.POST);
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
        } else {
            httpRequest.addFile("image",request.getImage());
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    /**
     * 人脸对比请求
     * 
     * @param request 人脸对比请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceCompare(FaceCompareRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceCompare();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());  
        httpRequest.setMethod(HttpMethod.POST);
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URLA, (request.getUrlA()));
            httpRequest.addParam(RequestBodyKey.URLB, (request.getUrlB()));
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            HashMap<String, String> keys = request.getKeyList();
            HashMap<String, File> images = request.getImageList();
            for (String k : keys.keySet()) {
                httpRequest.addFile(keys.get(k), images.get(k));
            }
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }

    /**
     * @param useNewDomain 是否使用新域名，<br>
     * true: http://recognition.image.myqcloud.com/face/multidentify <br>
     * false: http://service.image.myqcloud.com/face/multidentify <br>
     * 如果开发者使用的是原域名（service.image.myqcloud.com）且已产生调用，则无需更换域名。
     */
    public String faceMultiIdentify(FaceMultiIdentifyRequest request, boolean useNewDomain) throws AbstractImageException {
        request.check_param();
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = useNewDomain ? "http://recognition.image.myqcloud.com/face/multidentify"
                : "http://service.image.myqcloud.com/face/multidentify";

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setUrl(url);

        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));

        String session_id = request.getSession_id();
        if (session_id != null && !session_id.isEmpty()) {
            httpRequest.addParam("session_id", session_id);
        }

        if (request.isUrl()) {
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
            httpRequest.addParam("url", request.getImageUrl());
            String[] groupIds = request.getGroup_ids();
            if (groupIds != null && groupIds.length == 1) {
                httpRequest.addParam(RequestBodyKey.GROUP_ID, groupIds[0]);
            } else if (groupIds != null && groupIds.length > 1) {
                httpRequest.addParam(RequestBodyKey.GROUP_IDS, groupIds);
            } else {
                throw new ParamException("group_ids can not be null or empty!!");
            }

        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            httpRequest.addFile("image", request.getImageFile());
            String[] groupIds = request.getGroup_ids();

            if (groupIds != null && groupIds.length == 1) {
                httpRequest.addParam(RequestBodyKey.GROUP_ID, groupIds[0]);
            } else if (groupIds != null && groupIds.length > 1) {
                int index;
                for (index = 0; index < groupIds.length; index++) {
                    String key = String.format("group_ids[%d]", index);
                    String data = groupIds[index];
                    httpRequest.addParam(key, data);
                }
            } else {
                throw new ParamException("group_ids can not be null or empty!!");
            }

        }
        return httpClient.sendHttpRequest(httpRequest);

    }
    
    /**
     * 身份证识别对比接口
     * 
     * @param request 身份证识别对比接口参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceIdCardCompare(FaceIdCardCompareRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceIdcardCompare();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.IDCARDNUMBER, (request.getIdcardNumber()));
        httpRequest.addParam(RequestBodyKey.IDCARDNAME, (request.getIdcardName()));
        httpRequest.addParam(RequestBodyKey.SESSONID, request.getSessionId());
        
        httpRequest.setMethod(HttpMethod.POST);
        if (request.isUrl()) {
            httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
            httpRequest.addParam(RequestBodyKey.URL, request.getUrl());
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
        } else {
            httpRequest.addFile("image",request.getImage());
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        }
              
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     *  获取验证码请求
     * 
     * @param request  获取验证码请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceLiveGetFour(FaceLiveGetFourRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceLiveGetFour();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        if ( (request.getSeq()!=null) && (request.getSeq()).trim().length() != 0 ) {
            httpRequest.addParam(RequestBodyKey.SEQ, request.getSeq());
        }
        httpRequest.addHeader(RequestHeaderKey.Content_TYPE, String.valueOf(HttpContentType.APPLICATION_JSON));
        httpRequest.setContentType(HttpContentType.APPLICATION_JSON);  
    
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 人脸识别请求
     * 
     * @param request 人脸识别请求参数
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceIdCardLiveDetectFour(FaceIdCardLiveDetectFourRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceIdCardLiveDetectFour();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.IDCARDNUMBER, (request.getIdcardNumber()));
        httpRequest.addParam(RequestBodyKey.IDCARDNAME, (request.getIdcardName()));
        httpRequest.addParam(RequestBodyKey.VALIDATE_DATA, (request.getValidate()));
        if ( (request.getSeq()!=null) && (request.getSeq()).trim().length() != 0 ) {
            httpRequest.addParam(RequestBodyKey.SEQ, request.getSeq());
        }
        httpRequest.addFile("video",request.getVideo());
        httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        httpRequest.setMethod(HttpMethod.POST);  
        return httpClient.sendHttpRequest(httpRequest);
    }
    
    /**
     * 检测接口请求
     * 
     * @param request 检测接口请求
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     *         message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */  
    public String faceLiveDetectFour(FaceLiveDetectFourRequest request) throws AbstractImageException {
        request.check_param();
        
        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = "http://" + this.config.getQCloudImageDomain() + this.config.getFaceLiveDetectFour();
        
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());

        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        httpRequest.addParam(RequestBodyKey.BUCKET, request.getBucketName());
        httpRequest.addParam(RequestBodyKey.VALIDATE_DATA, request.getValidate());
        httpRequest.addParam(RequestBodyKey.COMPARE_FLAG, request.getCompareFlag());
        if ( (request.getSeq()!=null) && (request.getSeq()).trim().length() != 0 ) {
            httpRequest.addParam(RequestBodyKey.SEQ, request.getSeq());
        }

        httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        HashMap<String, String> keys = request.getKeyList();
        HashMap<String, File> images = request.getImageList();
        for (String k : keys.keySet()) {
            httpRequest.addFile(keys.get(k), images.get(k));
        }
   
        return httpClient.sendHttpRequest(httpRequest);
    }

    /**
     * 人脸静态活体检测
     * @param useNewDomain 是否使用新域名，<br>
     * true: http://recognition.image.myqcloud.com/face/livedetectpicture <br>
     * false: http://service.image.myqcloud.com/face/livedetectpicture <br>
     * 如果开发者使用的是原域名（service.image.myqcloud.com）且已产生调用，则无需更换域名。
     * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功, 其他为失败,
     * message为success或者失败原因
     * @throws AbstractImageException SDK定义的Image异常, 通常是输入参数有误或者环境问题(如网络不通)
     */
    public String faceLiveDetectPicture(FaceLiveDetectPictureRequest request, boolean useNewDomain) throws AbstractImageException {
        request.check_param();

        String sign = Sign.appSign(cred, request.getBucketName(), this.config.getSignExpired());
        String url = useNewDomain ? "http://recognition.image.myqcloud.com/face/livedetectpicture"
                : "http://service.image.myqcloud.com/face/livedetectpicture";

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader(RequestHeaderKey.Authorization, sign);
        httpRequest.addHeader(RequestHeaderKey.USER_AGENT, this.config.getUserAgent());
        httpRequest.addParam(RequestBodyKey.APPID, String.valueOf(cred.getAppId()));
        if (request.isUrl()) {
            httpRequest.setContentType(HttpContentType.APPLICATION_JSON);
            httpRequest.addParam("url", request.getImageUrl());
        } else {
            httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
            httpRequest.addFile("image",request.getImage());
            BytesContent bytesContent = request.getBytesContent();
            if (bytesContent != null) {
                httpRequest.addBytes(bytesContent.getKey(), bytesContent.getContent());
            }
        }

        return httpClient.sendHttpRequest(httpRequest);
    }
    
    
}
