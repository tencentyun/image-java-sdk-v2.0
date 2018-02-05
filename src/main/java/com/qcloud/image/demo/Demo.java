/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.qcloud.image.demo;

import com.qcloud.image.ImageClient;
import com.qcloud.image.request.FaceAddFaceRequest;
import com.qcloud.image.request.FaceCompareRequest;
import com.qcloud.image.request.FaceDelFaceRequest;
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
import com.qcloud.image.request.FaceLiveGetFourRequest;
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

import org.json.JSONObject;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author serenazhao image Demo代码
 */
public class Demo {

    public static void main(String[] args) {

        String appId = "0000000";
        String secretId = "YOUR_SECRETID";
        String secretKey = "YOUR_SECRETKEY";
        String bucketName = "YOUR_BUCKET";

        ImageClient imageClient = new ImageClient(appId, secretId, secretKey);

        /*设置代理服务器*/
        //imageClient.setProxy(new HttpHost("127.0.0.1", 8888));

        /*图像识别系列*/
        //鉴黄
        imagePorn(imageClient, bucketName);
        //图像内容
        imageTag(imageClient, bucketName);
        
        /*文字识别系列 ( OCR )*/
        //身份证
        ocrIdCard(imageClient, bucketName);
        //名片
        ocrNameCard(imageClient, bucketName);
        //通用
        ocrGeneral(imageClient, bucketName);
        //行驶证、驾驶证
        ocrDrivingLicence(imageClient, bucketName);
        //营业执照
        ocrBizLicense(imageClient, bucketName);
        //银行卡
        ocrBankCard(imageClient, bucketName);
        //车牌号
        ocrPlate(imageClient, bucketName);
        
        /*人脸识别系列*/
        faceDetect(imageClient, bucketName);
        faceShape(imageClient, bucketName);
        String personId = faceNewPerson(imageClient, bucketName);
        faceDelPerson(imageClient, bucketName, personId);
        faceAddFace(imageClient, bucketName);
        faceDelFace(imageClient, bucketName);
        faceSetInfo(imageClient, bucketName);
        faceGetInfo(imageClient, bucketName);
        faceGetGroupId(imageClient, bucketName);
        faceGetPersonId(imageClient, bucketName);
        faceGetFaceIdList(imageClient, bucketName);
        faceGetFaceInfo(imageClient, bucketName);
        faceFaceVerify(imageClient, bucketName);
        faceFaceIdentify(imageClient, bucketName);
        faceFaceCompare(imageClient, bucketName);
        faceIdCardCompare(imageClient, bucketName);
        String validate = faceLiveGetFour(imageClient, bucketName);
        File video = null;
        try {
            video = new File("F:\\pic\\ZOE_0171.mp4");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        faceIdCardLiveDetectFour(imageClient, bucketName, validate, video);
        faceLiveDetectFour(imageClient, bucketName, validate, video);

        // 关闭释放资源
        imageClient.shutdown();
        System.out.println("shutdown!");
    }

    /**
     * 检测视频和身份证是否对上操作
     */
    private static void faceLiveDetectFour(ImageClient imageClient, String bucketName, String validate, File video) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        File liveDetectImage = null;
        boolean compareFlag = true;
        try {
            liveDetectImage = new File("F:\\pic\\zhao2.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }

        FaceLiveDetectFourRequest faceLiveDetectReq = new FaceLiveDetectFourRequest(bucketName, validate, compareFlag, video, liveDetectImage, "seq");
        ret = imageClient.faceLiveDetectFour(faceLiveDetectReq);
        System.out.println("face  live detect four ret:" + ret);
    }

    /**
     * 通过视频对比指定身份信息接口
     */
    private static void faceIdCardLiveDetectFour(ImageClient imageClient, String bucketName, String validate, File video) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        String liveDetectIdcardNumber = "330782198802084329";
        String liveDetectIdcardName = "季锦锦";

        FaceIdCardLiveDetectFourRequest liveDetectReq = new FaceIdCardLiveDetectFourRequest(bucketName, validate, video, liveDetectIdcardNumber, liveDetectIdcardName, "seq");
        ret = imageClient.faceIdCardLiveDetectFour(liveDetectReq);
        System.out.println("face idCard live detect four ret:" + ret);
    }

    /**
     * 获取验证码接口
     */
    private static String faceLiveGetFour(ImageClient imageClient, String bucketName) {
        System.out.println("====================================================");
        String seq = "";
        FaceLiveGetFourRequest getFaceFourReq = new FaceLiveGetFourRequest(bucketName, seq);
        String ret = imageClient.faceLiveGetFour(getFaceFourReq);

        System.out.println("face live get four  ret:" + ret);
        String validate = "";
        JSONObject jsonObject = new JSONObject(ret);
        JSONObject data = jsonObject.getJSONObject("data");
        if (null != data) {
            validate = data.getString("validate_data");
        }
        return validate;
    }

    /**
     * 身份证识别对比接口
     */
    private static void faceIdCardCompare(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        String idcardNumber = "IDCARD NUM";
        String idcardName = "NAME";
        String idcardCompareUrl = "YOUR URL";
        String sessionId = "";
        FaceIdCardCompareRequest idCardCompareReq = new FaceIdCardCompareRequest(bucketName, idcardNumber, idcardName, idcardCompareUrl, sessionId);

        ret = imageClient.faceIdCardCompare(idCardCompareReq);
        System.out.println("face idCard Compare ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        String idcardCompareName = "";
        File idcardCompareImage = null;
        try {
            idcardCompareName = "idcard.jpg";
            idcardCompareImage = new File("F:\\pic\\idcard.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        idCardCompareReq = new FaceIdCardCompareRequest(bucketName, idcardNumber, idcardName, idcardCompareName, idcardCompareImage, sessionId);
        ret = imageClient.faceIdCardCompare(idCardCompareReq);
        System.out.println("face idCard Compare ret:" + ret);
    }

    /**
     * 人脸对比操作
     */
    private static void faceFaceCompare(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        String urlA = "YOUR URL A";
        String urlB = "YOUR URL B";
        FaceCompareRequest faceCompareReq = new FaceCompareRequest(bucketName, urlA, urlB);

        ret = imageClient.faceCompare(faceCompareReq);
        System.out.println("face compare ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        String[] compareNameList = new String[2];
        File[] compareImageList = new File[2];
        try {
            compareNameList[0] = "zhao1.jpg";
            compareNameList[1] = "zhao2.jpg";
            compareImageList[0] = new File("F:\\pic\\zhao1.jpg");
            compareImageList[1] = new File("F:\\pic\\zhao2.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        faceCompareReq = new FaceCompareRequest(bucketName, compareNameList, compareImageList);
        ret = imageClient.faceCompare(faceCompareReq);
        System.out.println("face compare ret:" + ret);
    }

    /**
     * 人脸识别操作
     */
    private static void faceFaceIdentify(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        String faceIdentifyGroupId = "group1";
        String faceIdentifyUrl = "YOUR URL";
        FaceIdentifyRequest faceIdentifyReq = new FaceIdentifyRequest(bucketName, faceIdentifyGroupId, faceIdentifyUrl);

        ret = imageClient.faceIdentify(faceIdentifyReq);
        System.out.println("face identify ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        String faceIdentifyName = "";
        File faceIdentifyImage = null;
        try {
            faceIdentifyName = "yang4.jpg";
            faceIdentifyImage = new File("F:\\pic\\yang4.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        faceIdentifyReq = new FaceIdentifyRequest(bucketName, faceIdentifyGroupId, faceIdentifyName, faceIdentifyImage);
        ret = imageClient.faceIdentify(faceIdentifyReq);
        System.out.println("face identify ret:" + ret);
    }

    /**
     * 人脸验证操作
     */
    private static void faceFaceVerify(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        String faceVerifyPersonId = "person1";
        String faceVerifyUrl = "YOUR URL";
        FaceVerifyRequest faceVerifyReq = new FaceVerifyRequest(bucketName, faceVerifyPersonId, faceVerifyUrl);

        ret = imageClient.faceVerify(faceVerifyReq);
        System.out.println("face verify ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        String faceVerifyName = "";
        File faceVerifyImage = null;
        faceVerifyPersonId = "person3111";
        try {
            faceVerifyName = "yang3.jpg";
            faceVerifyImage = new File("F:\\pic\\yang3.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        faceVerifyReq = new FaceVerifyRequest(bucketName, faceVerifyPersonId, faceVerifyName, faceVerifyImage);
        ret = imageClient.faceVerify(faceVerifyReq);
        System.out.println("face verify ret:" + ret);
    }

    /**
     * 获取人脸信息
     */
    private static void faceGetFaceInfo(ImageClient imageClient, String bucketName) {
        String ret;
        System.out.println("====================================================");
        String getFaceId = "1830582165978517027";
        FaceGetFaceInfoRequest getFaceInfoReq = new FaceGetFaceInfoRequest(bucketName, getFaceId);

        ret = imageClient.faceGetFaceInfo(getFaceInfoReq);
        System.out.println("face get face info  ret:" + ret);
    }

    /**
     * 获取人脸列表
     */
    private static void faceGetFaceIdList(ImageClient imageClient, String bucketName) {
        String ret;
        System.out.println("====================================================");
        String getFacePersonId = "personY";
        FaceGetFaceIdsRequest getFaceIdsReq = new FaceGetFaceIdsRequest(bucketName, getFacePersonId);

        ret = imageClient.faceGetFaceIds(getFaceIdsReq);
        System.out.println("face get face ids  ret:" + ret);
    }

    /**
     * 获取人列表
     */
    private static void faceGetPersonId(ImageClient imageClient, String bucketName) {
        String ret;
        System.out.println("====================================================");
        String getPersonGroupId = "group1";
        FaceGetPersonIdsRequest getPersonIdsReq = new FaceGetPersonIdsRequest(bucketName, getPersonGroupId);

        ret = imageClient.faceGetPersonIds(getPersonIdsReq);
        System.out.println("face get person ids  ret:" + ret);
    }

    /**
     * 获取组列表
     */
    private static void faceGetGroupId(ImageClient imageClient, String bucketName) {
        String ret;
        System.out.println("====================================================");
        FaceGetGroupIdsRequest getGroupReq = new FaceGetGroupIdsRequest(bucketName);

        ret = imageClient.faceGetGroupIds(getGroupReq);
        System.out.println("face get group ids  ret:" + ret);
    }

    /**
     * 个体获取信息
     */
    private static void faceGetInfo(ImageClient imageClient, String bucketName) {
        String ret;
        System.out.println("====================================================");
        String getInfoPersonId = "personY";
        FaceGetInfoRequest getInfoReq = new FaceGetInfoRequest(bucketName, getInfoPersonId);

        ret = imageClient.faceGetInfo(getInfoReq);
        System.out.println("face get info  ret:" + ret);
    }

    /**
     * 个体设置信息
     */
    private static void faceSetInfo(ImageClient imageClient, String bucketName) {
        String ret;
        System.out.println("====================================================");
        String setInfoPersonId = "personY";
        String setInfoPersonName = "mimi";
        String setInfoTag = "actress";
        FaceSetInfoRequest setInfoReq = new FaceSetInfoRequest(bucketName, setInfoPersonId, setInfoPersonName, setInfoTag);

        ret = imageClient.faceSetInfo(setInfoReq);
        System.out.println("face set info  ret:" + ret);
    }

    /**
     * 人脸删除操作
     */
    private static void faceDelFace(ImageClient imageClient, String bucketName) {
        String ret;
        System.out.println("====================================================");
        String delFacePersonId = "personY";
        String[] delFaceIds = new String[2];
        delFaceIds[0] = "1831408218312574949";
        delFaceIds[1] = "1831408248150847230";
        FaceDelFaceRequest delFaceReq = new FaceDelFaceRequest(bucketName, delFacePersonId, delFaceIds);

        ret = imageClient.faceDelFace(delFaceReq);
        System.out.println("face del  ret:" + ret);
    }

    /**
     * 增加人脸操作
     */
    private static void faceAddFace(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        String[] addFaceUrlList = new String[2];
        addFaceUrlList[0] = "YOUR URL A";
        addFaceUrlList[1] = "YOUR URL B";
        String addfacePersonId = "personY";
        String addfacePersonTag = "star1";
        FaceAddFaceRequest addFaceReq = new FaceAddFaceRequest(bucketName, addFaceUrlList, addfacePersonId, addfacePersonTag);
        ret = imageClient.faceAddFace(addFaceReq);
        System.out.println("add face ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        String[] addFaceNameList = new String[2];
        File[] addFaceImageList = new File[2];
        addfacePersonId = "personY";
        addfacePersonTag = "actor";
        try {
            addFaceNameList[0] = "yang2.jpg";
            addFaceImageList[0] = new File("F:\\pic\\yang2.jpg");
            addFaceNameList[1] = "yang3.jpg";
            addFaceImageList[1] = new File("F:\\pic\\yang3.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        addFaceReq = new FaceAddFaceRequest(bucketName, addFaceNameList, addFaceImageList, addfacePersonId, addfacePersonTag);
        ret = imageClient.faceAddFace(addFaceReq);
        System.out.println("add face ret:" + ret);
    }

    /**
     * 个人删除操作
     */
    private static void faceDelPerson(ImageClient imageClient, String bucketName, String personId) {
        String ret;
        System.out.println("====================================================");
        String delPersonId = "personY";
        FaceDelPersonRequest delPersonReq = new FaceDelPersonRequest(bucketName, personId);

        ret = imageClient.faceDelPerson(delPersonReq);
        System.out.println("face del  person ret:" + ret);
    }

    /**
     * 个体添加操作
     */
    private static String faceNewPerson(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        String personNewUrl = "YOUR URL";
        String[] groupIds = new String[2];
        groupIds[0] = "group3";
        groupIds[1] = "group22";
        String personName = "yangmi";
        String personId = "personY";
        String personTag = "star";
        FaceNewPersonRequest personNewReq = new FaceNewPersonRequest(bucketName, personId, groupIds, personNewUrl, personName, personTag);

        ret = imageClient.faceNewPerson(personNewReq);
        System.out.println("person new  ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        String personNewName = "";
        File personNewImage = null;
        groupIds[0] = "group11";
        groupIds[1] = "group33";
        personName = "yangmi";
        personId = "persony";
        personTag = "star";
        try {
            personNewName = "yang.jpg";
            personNewImage = new File("F:\\pic\\yang.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        personNewReq = new FaceNewPersonRequest(bucketName, personId, groupIds, personNewName, personNewImage, personName, personTag);
        ret = imageClient.faceNewPerson(personNewReq);
        System.out.println("person new ret:" + ret);
        return personId;
    }

    /**
     * 五官定位操作
     */
    private static void faceShape(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        String faceShapeUrl = "YOUR URL";
        FaceShapeRequest faceShapeReq = new FaceShapeRequest(bucketName, faceShapeUrl, 1);

        ret = imageClient.faceShape(faceShapeReq);
        System.out.println("face shape ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        String faceShapeName = "";
        File faceShapeImage = null;
        try {
            faceShapeName = "face1.jpg";
            faceShapeImage = new File("F:\\pic\\face1.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        faceShapeReq = new FaceShapeRequest(bucketName, faceShapeName, faceShapeImage, 1);
        ret = imageClient.faceShape(faceShapeReq);
        System.out.println("face shape ret:" + ret);
    }

    /**
     * 人脸检测操作
     */
    private static void faceDetect(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        String faceDetectUrl = "http://youtu.qq.com/app/img/experience/face_img/icon_face_01.jpg";
        FaceDetectRequest faceDetectReq = new FaceDetectRequest(bucketName, faceDetectUrl, 1);

        ret = imageClient.faceDetect(faceDetectReq);
        System.out.println("face detect ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        String faceDetectName = "";
        File faceDetectImage = null;
        try {
            faceDetectName = "face1.jpg";
            faceDetectImage = new File("F:\\pic\\face1.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        faceDetectReq = new FaceDetectRequest(bucketName, faceDetectName, faceDetectImage, 1);
        ret = imageClient.faceDetect(faceDetectReq);
        System.out.println("face detect ret:" + ret);
    }

    /**
     * 名片ocr识别操作
     */
    private static void ocrNameCard(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        String[] namecardUrlList = new String[2];
        namecardUrlList[0] = "http://youtu.qq.com/app/img/experience/char_general/ocr_namecard_01.jpg";
        namecardUrlList[1] = "http://youtu.qq.com/app/img/experience/char_general/ocr_namecard_02.jpg";
        NamecardDetectRequest nameReq = new NamecardDetectRequest(bucketName, namecardUrlList, 0);

        ret = imageClient.namecardDetect(nameReq);
        System.out.println("namecard detect ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        String[] namecardNameList = new String[2];
        File[] namecardImageList = new File[2];
        try {
            namecardNameList[0] = "ocr_namecard_01.jpg";
            namecardImageList[0] = new File("assets", namecardNameList[0]);
            namecardNameList[1] = "ocr_namecard_02.jpg";
            namecardImageList[1] = new File("assets", namecardNameList[1]);
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        nameReq = new NamecardDetectRequest(bucketName, namecardNameList, namecardImageList, 0);
        ret = imageClient.namecardDetect(nameReq);
        System.out.println("namecard detect ret:" + ret);
    }

    /**
     * 通用印刷体OCR
     */
    private static void ocrGeneral(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        GeneralOcrRequest request = new GeneralOcrRequest(bucketName, "http://youtu.qq.com/app/img/experience/char_general/ocr_common09.jpg");
        ret = imageClient.generalOcr(request);
        System.out.println("ocrGeneral:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        request = new GeneralOcrRequest(bucketName, new File("assets", "ocr_common09.jpg"));
        ret = imageClient.generalOcr(request);
        System.out.println("ocrGeneral:" + ret);
    }

    /**
     * OCR-营业执照识别
     */
    private static void ocrBizLicense(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        OcrBizLicenseRequest request = new OcrBizLicenseRequest(bucketName, "http://youtu.qq.com/app/img/experience/char_general/ocr_yyzz_02.jpg");
        ret = imageClient.ocrBizLicense(request);
        System.out.println("ocrBizLicense:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        request = new OcrBizLicenseRequest(bucketName, new File("assets", "ocr_yyzz_02.jpg"));
        ret = imageClient.ocrBizLicense(request);
        System.out.println("ocrBizLicense:" + ret);
    }

    /**
     * OCR-银行卡识别
     */
    private static void ocrBankCard(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        OcrBankCardRequest request = new OcrBankCardRequest(bucketName, "http://youtu.qq.com/app/img/experience/char_general/icon_ocr_card_1.jpg");
        ret = imageClient.ocrBankCard(request);
        System.out.println("ocrBankCard:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        request = new OcrBankCardRequest(bucketName, new File("assets", "icon_ocr_card_1.jpg"));
        ret = imageClient.ocrBankCard(request);
        System.out.println("ocrBankCard:" + ret);
    }

    /**
     * OCR-车牌识别
     */
    private static void ocrPlate(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        OcrPlateRequest request = new OcrPlateRequest(bucketName, "http://youtu.qq.com/app/img/experience/char_general/icon_ocr_license_3.jpg");
        ret = imageClient.ocrPlate(request);
        System.out.println("ocrPlate:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        request = new OcrPlateRequest(bucketName, new File("assets", "icon_ocr_license_3.jpg"));
        ret = imageClient.ocrPlate(request);
        System.out.println("ocrPlate:" + ret);
    }

    /**
     * OCR-行驶证驾驶证识别
     */
    private static void ocrDrivingLicence(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. 驾驶证 url方式
        System.out.println("====================================================");
        OcrDrivingLicenceRequest request = new OcrDrivingLicenceRequest(bucketName, OcrDrivingLicenceRequest.TYPE_DRIVER_LICENSE, "http://youtu.qq.com/app/img/experience/char_general/icon_ocr_jsz_01.jpg");
        ret = imageClient.ocrDrivingLicence(request);
        System.out.println("ocrDrivingLicence:" + ret);

        //2. 驾驶证 图片内容方式
        System.out.println("====================================================");
        request = new OcrDrivingLicenceRequest(bucketName, OcrDrivingLicenceRequest.TYPE_DRIVER_LICENSE, new File("assets", "icon_ocr_jsz_01.jpg"));
        ret = imageClient.ocrDrivingLicence(request);
        System.out.println("ocrDrivingLicence:" + ret);

        // 1. 行驶证 url方式
        System.out.println("====================================================");
        request = new OcrDrivingLicenceRequest(bucketName, OcrDrivingLicenceRequest.TYPE_VEHICLE_LICENSE, "http://youtu.qq.com/app/img/experience/char_general/icon_ocr_xsz_01.jpg");
        ret = imageClient.ocrDrivingLicence(request);
        System.out.println("ocrDrivingLicence:" + ret);

        //2. 行驶证 图片内容方式
        System.out.println("====================================================");
        request = new OcrDrivingLicenceRequest(bucketName, OcrDrivingLicenceRequest.TYPE_VEHICLE_LICENSE, new File("assets", "icon_ocr_xsz_01.jpg"));
        ret = imageClient.ocrDrivingLicence(request);
        System.out.println("ocrDrivingLicence:" + ret);
    }

    /**
     * 身份证ocr识别操作
     */
    private static void ocrIdCard(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式,识别身份证正面
        System.out.println("====================================================");
        String[] idcardUrlList = new String[2];
        idcardUrlList[0] = "http://youtu.qq.com/app/img/experience/char_general/icon_id_01.jpg";
        idcardUrlList[1] = "http://youtu.qq.com/app/img/experience/char_general/icon_id_02.jpg";
        IdcardDetectRequest idReq = new IdcardDetectRequest(bucketName, idcardUrlList, 0);
        ret = imageClient.idcardDetect(idReq);
        System.out.println("idcard detect ret:" + ret);
        //识别身份证反面
        idcardUrlList[0] = "https://gss0.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/314e251f95cad1c89e04bea2763e6709c83d51f3.jpg";
        idcardUrlList[1] = "http://image2.sina.com.cn/dy/c/2004-03-29/U48P1T1D3073262F23DT20040329135445.jpg";
        idReq = new IdcardDetectRequest(bucketName, idcardUrlList, 1);
        ret = imageClient.idcardDetect(idReq);
        System.out.println("idcard detect ret:" + ret);

        //2. 图片内容方式,识别身份证正面
        System.out.println("====================================================");
        String[] idcardNameList = new String[2];
        File[] idcardImageList = new File[2];
        try {
            idcardNameList[0] = "icon_id_01.jpg";
            idcardImageList[0] = new File("assets", idcardNameList[0]);
            idcardNameList[1] = "icon_id_02.jpg";
            idcardImageList[1] = new File("assets", idcardNameList[1]);
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        idReq = new IdcardDetectRequest(bucketName, idcardNameList, idcardImageList, 0);
        ret = imageClient.idcardDetect(idReq);
        System.out.println("idcard detect ret:" + ret);
        //识别身份证反面
        try {
            idcardNameList[0] = "icon_id_03.jpg";
            idcardImageList[0] = new File("assets", idcardNameList[0]);
            idcardNameList[1] = "icon_id_04.jpg";
            idcardImageList[1] = new File("assets", idcardNameList[1]);
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        idReq = new IdcardDetectRequest(bucketName, idcardNameList, idcardImageList, 1);
        ret = imageClient.idcardDetect(idReq);
        System.out.println("idcard detect ret:" + ret);
    }

    /**
     * 标签识别操作
     */
    private static void imageTag(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        String tagUrl = "http://youtu.qq.com/app/img/experience/image/icon_imag_01.jpg";
        TagDetectRequest tagReq = new TagDetectRequest(bucketName, tagUrl);
        ret = imageClient.tagDetect(tagReq);
        System.out.println("tag detect ret:" + ret);

        // 2. 图片内容方式
        System.out.println("====================================================");
        File tagImage = null;
        try {
            tagImage = new File("assets", "icon_imag_01.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }

        tagReq = new TagDetectRequest(bucketName, tagImage);
        ret = imageClient.tagDetect(tagReq);
        System.out.println("tag detect ret:" + ret);
    }

    /**
     * 黄图识别操作
     */
    private static void imagePorn(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        String[] pornUrlList = new String[3];
        pornUrlList[0] = "http://youtu.qq.com/app/img/experience/porn/icon_porn04.jpg";
        pornUrlList[1] = "http://youtu.qq.com/app/img/experience/porn/icon_porn05.jpg";
        pornUrlList[2] = "http://youtu.qq.com/app/img/experience/porn/icon_porn06.jpg";
        PornDetectRequest pornReq = new PornDetectRequest(bucketName, pornUrlList);

        ret = imageClient.pornDetect(pornReq);
        System.out.println("porn detect ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        String[] pornNameList = new String[3];
        File[] pornImageList = new File[3];
        try {
            pornNameList[0] = "icon_porn04.jpg";
            pornImageList[0] = new File("assets", pornNameList[0]);
            pornNameList[1] = "icon_porn05.jpg";
            pornImageList[1] = new File("assets", pornNameList[1]);
            pornNameList[2] = "icon_porn06.jpg";
            pornImageList[2] = new File("assets", pornNameList[2]);
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        pornReq = new PornDetectRequest(bucketName, pornNameList, pornImageList);
        ret = imageClient.pornDetect(pornReq);
        System.out.println("porn detect ret:" + ret);
    }

}
