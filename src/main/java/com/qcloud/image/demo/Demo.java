/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.qcloud.image.demo;

import com.qcloud.image.ImageClient;
import com.qcloud.image.exception.AbstractImageException;
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

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
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

        
        ImageClient imageClient = new ImageClient(appId, secretId, secretKey, ImageClient.NEW_DOMAIN_recognition_image_myqcloud_com/*根据文档说明选择域名*/);

        /*设置代理服务器*/
        //Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("127.0.0.1", 8080));
        //imageClient.setProxy(proxy);


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
        
        /* 人脸识别系列
         * 因为数据不能跨域名共享, 请根据文档说明选择域名初始化ImageClient. 文档见: https://cloud.tencent.com/document/product/867/17582
         * ImageClient imageClient = new ImageClient(appId, secretId, secretKey, ImageClient.NEW_DOMAIN_recognition_image_myqcloud_com); //根据文档说明选择域名初始化ImageClient
         */
        faceDetect(imageClient, bucketName);
        faceShape(imageClient, bucketName);
        String personId = faceNewPerson(imageClient, bucketName);
        faceDelPerson(imageClient, bucketName, personId);
        faceAddFace(imageClient, bucketName);
        faceDelFace(imageClient, bucketName);
        faceSetInfo(imageClient, bucketName);
        faceGetInfo(imageClient, bucketName);
        faceGetGroupId(imageClient, bucketName);
        faceAddGroupId(imageClient, bucketName);
        faceDelGroupId(imageClient, bucketName);
        faceGetPersonId(imageClient, bucketName);
        faceGetFaceIdList(imageClient, bucketName);
        faceGetFaceInfo(imageClient, bucketName);
        faceFaceVerify(imageClient, bucketName);
        faceFaceIdentify(imageClient, bucketName);
        faceFaceCompare(imageClient, bucketName);
        faceMultiIdentifyRequest(imageClient,bucketName);//多脸检索

        /*人脸核身系列*/
        faceIdCardCompare(imageClient, bucketName);
        String validate = faceLiveGetFour(imageClient, bucketName);
        faceIdCardLiveDetectFour(imageClient, bucketName, validate);
        faceLiveDetectFour(imageClient, bucketName, validate);
        faceLiveDetectPicture(imageClient, bucketName);//人脸静态活体检测
        
        imageClient.shutdown();
    }

    /**
     * 人脸静态活体检测，文档 https://cloud.tencent.com/document/product/641/12558
     */
    private static void faceLiveDetectPicture(ImageClient imageClient, String bucketName) {
        String result = null;
        FaceLiveDetectPictureRequest request;

        // 1. url方式
        System.out.println("====================================================");
        String imageUrl = "http://open.youtu.qq.com/app/img/experience/face_img/face_34.jpg";//照片url
        request = new FaceLiveDetectPictureRequest(bucketName, imageUrl);
        try {
            result = imageClient.faceLiveDetectPicture(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face  live detect picture result:" + result);

        //2. 图片内容方式
        System.out.println("====================================================");
        File image = new File("assets", "face_34.jpg");
        request = new FaceLiveDetectPictureRequest(bucketName, image);
        try {
            result = imageClient.faceLiveDetectPicture(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face  live detect picture result:" + result);
        
        //3. 图片内容方式(byte[])
        System.out.println("====================================================");
        request = new FaceLiveDetectPictureRequest(bucketName, getFileBytes(image));
        try {
            result = imageClient.faceLiveDetectPicture(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face  live detect picture result:" + result);
    }

    /**
     * 检测视频和身份证是否对上操作
     */
    private static void faceLiveDetectFour(ImageClient imageClient, String bucketName, String validate) {
        String ret = null;
        System.out.println("====================================================");
        boolean compareFlag = true;
        File liveDetectImage = new File("F:\\pic\\zhao2.jpg");
        File video = new File("/home/video.mp4");

        FaceLiveDetectFourRequest faceLiveDetectReq = new FaceLiveDetectFourRequest(bucketName, validate, compareFlag, video, liveDetectImage, "seq");
        try {
            ret = imageClient.faceLiveDetectFour(faceLiveDetectReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face  live detect four ret:" + ret);
    }

    /**
     * 通过视频对比指定身份信息接口
     */
    private static void faceIdCardLiveDetectFour(ImageClient imageClient, String bucketName, String validate) {
        String ret = null;
        System.out.println("====================================================");
        String liveDetectIdcardNumber = "330782198802084329";
        String liveDetectIdcardName = "季锦锦";
        File video = new File("/home/video.mp4");

        FaceIdCardLiveDetectFourRequest liveDetectReq = new FaceIdCardLiveDetectFourRequest(bucketName, validate, video, liveDetectIdcardNumber, liveDetectIdcardName, "seq");
        try {
            ret = imageClient.faceIdCardLiveDetectFour(liveDetectReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face idCard live detect four ret:" + ret);
    }

    /**
     * 获取验证码接口
     */
    private static String faceLiveGetFour(ImageClient imageClient, String bucketName) {
        System.out.println("====================================================");
        String seq = "";
        FaceLiveGetFourRequest getFaceFourReq = new FaceLiveGetFourRequest(bucketName, seq);
        String ret = null;
        try {
            ret = imageClient.faceLiveGetFour(getFaceFourReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }

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
        String ret = null;
        // 1. url方式
        System.out.println("====================================================");
        String idcardNumber = "IDCARD NUM";
        String idcardName = "NAME";
        String idcardCompareUrl = "YOUR URL";
        String sessionId = "";
        FaceIdCardCompareRequest idCardCompareReq = new FaceIdCardCompareRequest(bucketName, idcardNumber, idcardName, idcardCompareUrl, sessionId);

        try {
            ret = imageClient.faceIdCardCompare(idCardCompareReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
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
        try {
            ret = imageClient.faceIdCardCompare(idCardCompareReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face idCard Compare ret:" + ret);
    }

    /**
     * 人脸对比操作
     */
    private static void faceFaceCompare(ImageClient imageClient, String bucketName) {
        String ret = null;
        // 1. url方式
        System.out.println("====================================================");
        String urlA = "YOUR URL A";
        String urlB = "YOUR URL B";
        FaceCompareRequest faceCompareReq = new FaceCompareRequest(bucketName, urlA, urlB);

        try {
            ret = imageClient.faceCompare(faceCompareReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face compare ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        File imageA = new File("F:\\pic\\zhao1.jpg");
        File imageB = new File("F:\\pic\\zhao2.jpg");
        faceCompareReq = new FaceCompareRequest(bucketName, imageA, imageB);
        try {
            ret = imageClient.faceCompare(faceCompareReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face compare ret:" + ret);
    }

    /**
     * 多脸检索
     * <a href="https://cloud.tencent.com/document/product/641/14349">点击查看文档</a>
     */
    private static void faceMultiIdentifyRequest(ImageClient imageClient, String bucketName) {
        FaceMultiIdentifyRequest request;
        String result = null;

        // 1. url方式
        System.out.println("====================================================");
        String imageUrl = "http://youtu.qq.com/app/img/experience/face_img/icon_face_01.jpg";
        request = new FaceMultiIdentifyRequest(bucketName, imageUrl, "tencent", "group_id_A", "group_id_B", "group_id_C");
        try {
            result = imageClient.faceMultiIdentify(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face compare result:" + result);

        //2. 图片内容方式
        System.out.println("====================================================");
        File imageFile = new File("assets","icon_face_01.jpg");
        request = new FaceMultiIdentifyRequest(bucketName, imageFile, "tencent", "group_id_A", "group_id_B", "group_id_C");
        try {
            result = imageClient.faceMultiIdentify(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face compare result:" + result);
    }

    /**
     * 人脸识别操作
     */
    private static void faceFaceIdentify(ImageClient imageClient, String bucketName) {
        String ret = null;
        // 1. url方式
        System.out.println("====================================================");
        String groupId = "groupA";
        String[] groupIds = {"groupA", "groupB"};
        String faceImageUrl = "YOUR URL";
        FaceIdentifyRequest faceIdentifyReq = new FaceIdentifyRequest(bucketName, groupId, faceImageUrl);// 一个 groupId
        //FaceIdentifyRequest  faceIdentifyReq = new FaceIdentifyRequest(bucketName, groupIds, faceImageUrl);// 多个 groupId
        try {
            ret = imageClient.faceIdentify(faceIdentifyReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face identify ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        File faceImageFile = new File("assets", "icon_face_01.jpg");
        FaceIdentifyRequest faceIdentifyReq2 = new FaceIdentifyRequest(bucketName, groupId, faceImageFile);// 一个 groupId
        //FaceIdentifyRequest    faceIdentifyReq2 = new FaceIdentifyRequest(bucketName, groupIds, faceImageFile);// 多个 groupId
        try {
            ret = imageClient.faceIdentify(faceIdentifyReq2);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face identify ret:" + ret);

        //3. 图片内容方式(byte[])
        System.out.println("====================================================");
        byte[] imgBytes = getFileBytes(faceImageFile);
        if (imgBytes != null) {
            FaceIdentifyRequest faceIdentifyReq3 = new FaceIdentifyRequest(bucketName, groupId, imgBytes);// 一个 groupId
            //FaceIdentifyRequest faceIdentifyReq3 = new FaceIdentifyRequest(bucketName, groupIds, imgBytes);// 多个 groupId
            try {
                ret = imageClient.faceIdentify(faceIdentifyReq3);
            } catch (AbstractImageException e) {
                e.printStackTrace();
            }
            System.out.println("face identify ret:" + ret);
        } else {
            System.out.println("face identify ret: get image content fail");
        }
    }

    /**
     * 人脸验证操作
     */
    private static void faceFaceVerify(ImageClient imageClient, String bucketName) {
        String ret = null;
        // 1. url方式
        System.out.println("====================================================");
        String faceVerifyPersonId = "person1";
        String faceVerifyUrl = "YOUR URL";
        FaceVerifyRequest faceVerifyReq = new FaceVerifyRequest(bucketName, faceVerifyPersonId, faceVerifyUrl);

        try {
            ret = imageClient.faceVerify(faceVerifyReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
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
        try {
            ret = imageClient.faceVerify(faceVerifyReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face verify ret:" + ret);
    }

    /**
     * 获取人脸信息
     */
    private static void faceGetFaceInfo(ImageClient imageClient, String bucketName) {
        String ret = null;
        System.out.println("====================================================");
        String getFaceId = "1830582165978517027";
        FaceGetFaceInfoRequest getFaceInfoReq = new FaceGetFaceInfoRequest(bucketName, getFaceId);

        try {
            ret = imageClient.faceGetFaceInfo(getFaceInfoReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face get face info  ret:" + ret);
    }

    /**
     * 获取人脸列表
     */
    private static void faceGetFaceIdList(ImageClient imageClient, String bucketName) {
        String ret = null;
        System.out.println("====================================================");
        String getFacePersonId = "personY";
        FaceGetFaceIdsRequest getFaceIdsReq = new FaceGetFaceIdsRequest(bucketName, getFacePersonId);

        try {
            ret = imageClient.faceGetFaceIds(getFaceIdsReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face get face ids  ret:" + ret);
    }

    /**
     * 获取人列表
     */
    private static void faceGetPersonId(ImageClient imageClient, String bucketName) {
        String ret = null;
        System.out.println("====================================================");
        String getPersonGroupId = "group1";
        FaceGetPersonIdsRequest getPersonIdsReq = new FaceGetPersonIdsRequest(bucketName, getPersonGroupId);

        try {
            ret = imageClient.faceGetPersonIds(getPersonIdsReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face get person ids  ret:" + ret);
    }

    /**
     * 获取组列表
     */
    private static void faceGetGroupId(ImageClient imageClient, String bucketName) {
        String ret = null;
        System.out.println("====================================================");
        FaceGetGroupIdsRequest getGroupReq = new FaceGetGroupIdsRequest(bucketName);

        try {
            ret = imageClient.faceGetGroupIds(getGroupReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face get group ids  ret:" + ret);
    }

    /**
     * Person新增组信息, 文档 https://cloud.tencent.com/document/product/641/12417
     */
    private static void faceAddGroupId(ImageClient imageClient, String bucketName) {
        String ret = null;
        System.out.println("====================================================");
        FaceAddGroupIdsRequest request = new FaceAddGroupIdsRequest(bucketName, "personId1", "group2");

        try {
            ret = imageClient.faceAddGroupIds(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face add group ids  ret:" + ret);
    }

    /**
     * Person删除组信息, 文档 https://cloud.tencent.com/document/product/641/12417
     */
    private static void faceDelGroupId(ImageClient imageClient, String bucketName) {
        String ret = null;
        System.out.println("====================================================");
        FaceDelGroupIdsRequest request = new FaceDelGroupIdsRequest(bucketName, "personId1", "group2");

        try {
            ret = imageClient.faceDelGroupIds(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face del group ids  ret:" + ret);
    }

    /**
     * 个体获取信息
     */
    private static void faceGetInfo(ImageClient imageClient, String bucketName) {
        String ret = null;
        System.out.println("====================================================");
        String getInfoPersonId = "personY";
        FaceGetInfoRequest getInfoReq = new FaceGetInfoRequest(bucketName, getInfoPersonId);

        try {
            ret = imageClient.faceGetInfo(getInfoReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face get info  ret:" + ret);
    }

    /**
     * 个体设置信息
     */
    private static void faceSetInfo(ImageClient imageClient, String bucketName) {
        String ret = null;
        System.out.println("====================================================");
        String setInfoPersonId = "personY";
        String setInfoPersonName = "mimi";
        String setInfoTag = "actress";
        FaceSetInfoRequest setInfoReq = new FaceSetInfoRequest(bucketName, setInfoPersonId, setInfoPersonName, setInfoTag);

        try {
            ret = imageClient.faceSetInfo(setInfoReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face set info  ret:" + ret);
    }

    /**
     * 人脸删除操作
     */
    private static void faceDelFace(ImageClient imageClient, String bucketName) {
        String ret = null;
        System.out.println("====================================================");
        String delFacePersonId = "personY";
        String[] delFaceIds = new String[2];
        delFaceIds[0] = "1831408218312574949";
        delFaceIds[1] = "1831408248150847230";
        FaceDelFaceRequest delFaceReq = new FaceDelFaceRequest(bucketName, delFacePersonId, delFaceIds);

        try {
            ret = imageClient.faceDelFace(delFaceReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face del  ret:" + ret);
    }

    /**
     * 增加人脸操作
     */
    private static void faceAddFace(ImageClient imageClient, String bucketName) {
        String ret = null;
        // 1. url方式
        System.out.println("====================================================");
        String[] addFaceUrlList = new String[2];
        addFaceUrlList[0] = "YOUR URL A";
        addFaceUrlList[1] = "YOUR URL B";
        String addfacePersonId = "personY";
        String addfacePersonTag = "star1";
        FaceAddFaceRequest addFaceReq = new FaceAddFaceRequest(bucketName, addFaceUrlList, addfacePersonId, addfacePersonTag);
        try {
            ret = imageClient.faceAddFace(addFaceReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("add face ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        File[] addFaceImageList = new File[2];
        addfacePersonId = "personY";
        addfacePersonTag = "actor";
        addFaceImageList[0] = new File("F:\\pic\\yang2.jpg");
        addFaceImageList[1] = new File("F:\\pic\\yang3.jpg");
        addFaceReq = new FaceAddFaceRequest(bucketName, addFaceImageList, addfacePersonId, addfacePersonTag);
        try {
            ret = imageClient.faceAddFace(addFaceReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("add face ret:" + ret);
    }

    /**
     * 个人删除操作
     */
    private static void faceDelPerson(ImageClient imageClient, String bucketName, String personId) {
        String ret = null;
        System.out.println("====================================================");
        FaceDelPersonRequest delPersonReq = new FaceDelPersonRequest(bucketName, personId);

        try {
            ret = imageClient.faceDelPerson(delPersonReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face del  person ret:" + ret);
    }

    /**
     * 个体添加操作
     */
    private static String faceNewPerson(ImageClient imageClient, String bucketName) {
        String ret = null;
        FaceNewPersonRequest personNewReq;
        String[] groupIds = new String[2];
        groupIds[0] = "group0";
        groupIds[1] = "group1";
        String personName = "yangmi1";
        String personId = "personId1";
        String personTag = "star1";
        
        // 1. url方式
        System.out.println("====================================================");
        String personNewUrl = "YOUR URL";
        personNewReq = new FaceNewPersonRequest(bucketName, personId, groupIds, personNewUrl, personName, personTag);
        try {
            ret = imageClient.faceNewPerson(personNewReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("person new  ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        File personNewImage = new File("assets","icon_face_01.jpg");
        personNewReq = new FaceNewPersonRequest(bucketName, personId, groupIds, personNewImage, personName, personTag);
        try {
            ret = imageClient.faceNewPerson(personNewReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("person new ret:" + ret);
        
        //3. 图片内容方式(byte[])
        System.out.println("====================================================");
        byte[] imageContent = getFileBytes(personNewImage);
        if (imageContent != null) {
            personNewReq = new FaceNewPersonRequest(bucketName, personId, groupIds, imageContent, personName, personTag);
            try {
                ret = imageClient.faceNewPerson(personNewReq);
            } catch (AbstractImageException e) {
                e.printStackTrace();
            }
            System.out.println("person new ret:" + ret);
        } else {
            System.out.println("person new ret: get image content fail");
        }
        
        return personId;
    }

    /**
     * 五官定位操作
     */
    private static void faceShape(ImageClient imageClient, String bucketName) {
        String ret = null;
        // 1. url方式
        System.out.println("====================================================");
        String faceShapeUrl = "YOUR URL";
        FaceShapeRequest faceShapeReq = new FaceShapeRequest(bucketName, faceShapeUrl, 1);

        try {
            ret = imageClient.faceShape(faceShapeReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
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
        try {
            ret = imageClient.faceShape(faceShapeReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face shape ret:" + ret);
    }

    /**
     * 人脸检测操作
     */
    private static void faceDetect(ImageClient imageClient, String bucketName) {
        String ret = null;
        // 1. url方式
        System.out.println("====================================================");
        String faceDetectUrl = "http://youtu.qq.com/app/img/experience/face_img/icon_face_01.jpg";
        FaceDetectRequest faceDetectReq = new FaceDetectRequest(bucketName, faceDetectUrl, 1);

        try {
            ret = imageClient.faceDetect(faceDetectReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
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
        try {
            ret = imageClient.faceDetect(faceDetectReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("face detect ret:" + ret);
    }

    /**
     * 名片ocr识别操作
     */
    private static void ocrNameCard(ImageClient imageClient, String bucketName) {
        String ret = null;
        // 1. url方式
        System.out.println("====================================================");
        String[] namecardUrlList = new String[2];
        namecardUrlList[0] = "http://youtu.qq.com/app/img/experience/char_general/ocr_namecard_01.jpg";
        namecardUrlList[1] = "http://youtu.qq.com/app/img/experience/char_general/ocr_namecard_02.jpg";
        NamecardDetectRequest nameReq = new NamecardDetectRequest(bucketName, namecardUrlList, 0);

        try {
            ret = imageClient.namecardDetect(nameReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("namecard detect ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        File[] namecardImageList = new File[2];
        namecardImageList[0] = new File("assets", "ocr_namecard_01.jpg");
        namecardImageList[1] = new File("assets", "ocr_namecard_02.jpg");
        nameReq = new NamecardDetectRequest(bucketName, namecardImageList, 0);
        try {
            ret = imageClient.namecardDetect(nameReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("namecard detect ret:" + ret);
    }

    /**
     * 通用印刷体OCR
     */
    private static void ocrGeneral(ImageClient imageClient, String bucketName) {
        String ret = null;
        // 1. url方式
        System.out.println("====================================================");
        GeneralOcrRequest request = new GeneralOcrRequest(bucketName, "http://youtu.qq.com/app/img/experience/char_general/ocr_common09.jpg");
        try {
            ret = imageClient.generalOcr(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("ocrGeneral:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        request = new GeneralOcrRequest(bucketName, new File("assets", "ocr_common09.jpg"));
        try {
            ret = imageClient.generalOcr(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("ocrGeneral:" + ret);
    }

    /**
     * OCR-营业执照识别
     */
    private static void ocrBizLicense(ImageClient imageClient, String bucketName) {
        String ret = null;
        // 1. url方式
        System.out.println("====================================================");
        OcrBizLicenseRequest request = new OcrBizLicenseRequest(bucketName, "http://youtu.qq.com/app/img/experience/char_general/ocr_yyzz_02.jpg");
        try {
            ret = imageClient.ocrBizLicense(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("ocrBizLicense:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        request = new OcrBizLicenseRequest(bucketName, new File("assets", "ocr_yyzz_02.jpg"));
        try {
            ret = imageClient.ocrBizLicense(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("ocrBizLicense:" + ret);
    }

    /**
     * OCR-银行卡识别
     */
    private static void ocrBankCard(ImageClient imageClient, String bucketName) {
        String ret = null;
        // 1. url方式
        System.out.println("====================================================");
        OcrBankCardRequest request = new OcrBankCardRequest(bucketName, "http://youtu.qq.com/app/img/experience/char_general/icon_ocr_card_1.jpg");
        try {
            ret = imageClient.ocrBankCard(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("ocrBankCard:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        request = new OcrBankCardRequest(bucketName, new File("assets", "icon_ocr_card_1.jpg"));
        try {
            ret = imageClient.ocrBankCard(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("ocrBankCard:" + ret);
    }

    /**
     * OCR-车牌识别
     */
    private static void ocrPlate(ImageClient imageClient, String bucketName) {
        String ret = null;
        // 1. url方式
        System.out.println("====================================================");
        OcrPlateRequest request = new OcrPlateRequest(bucketName, "http://youtu.qq.com/app/img/experience/char_general/icon_ocr_license_3.jpg");
        try {
            ret = imageClient.ocrPlate(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("ocrPlate:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        request = new OcrPlateRequest(bucketName, new File("assets", "icon_ocr_license_3.jpg"));
        try {
            ret = imageClient.ocrPlate(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("ocrPlate:" + ret);
    }

    /**
     * OCR-行驶证驾驶证识别
     */
    private static void ocrDrivingLicence(ImageClient imageClient, String bucketName) {
        String ret = null;
        // 1. 驾驶证 url方式
        System.out.println("====================================================");
        OcrDrivingLicenceRequest request = new OcrDrivingLicenceRequest(bucketName, OcrDrivingLicenceRequest.TYPE_DRIVER_LICENSE, "http://youtu.qq.com/app/img/experience/char_general/icon_ocr_jsz_01.jpg");
        try {
            ret = imageClient.ocrDrivingLicence(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("ocrDrivingLicence:" + ret);

        //2. 驾驶证 图片内容方式
        System.out.println("====================================================");
        request = new OcrDrivingLicenceRequest(bucketName, OcrDrivingLicenceRequest.TYPE_DRIVER_LICENSE, new File("assets", "icon_ocr_jsz_01.jpg"));
        try {
            ret = imageClient.ocrDrivingLicence(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("ocrDrivingLicence:" + ret);

        // 1. 行驶证 url方式
        System.out.println("====================================================");
        request = new OcrDrivingLicenceRequest(bucketName, OcrDrivingLicenceRequest.TYPE_VEHICLE_LICENSE, "http://youtu.qq.com/app/img/experience/char_general/icon_ocr_xsz_01.jpg");
        try {
            ret = imageClient.ocrDrivingLicence(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("ocrDrivingLicence:" + ret);

        //2. 行驶证 图片内容方式
        System.out.println("====================================================");
        request = new OcrDrivingLicenceRequest(bucketName, OcrDrivingLicenceRequest.TYPE_VEHICLE_LICENSE, new File("assets", "icon_ocr_xsz_01.jpg"));
        try {
            ret = imageClient.ocrDrivingLicence(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("ocrDrivingLicence:" + ret);
    }

    /**
     * 身份证ocr识别操作
     */
    private static void ocrIdCard(ImageClient imageClient, String bucketName) {
        String ret = null;
        // 1. url方式,识别身份证正面
        System.out.println("====================================================");
        String[] idcardUrlList = new String[2];
        idcardUrlList[0] = "http://youtu.qq.com/app/img/experience/char_general/icon_id_01.jpg";
        idcardUrlList[1] = "http://youtu.qq.com/app/img/experience/char_general/icon_id_02.jpg";
        IdcardDetectRequest idReq = new IdcardDetectRequest(bucketName, idcardUrlList, 0);
        try {
            ret = imageClient.idcardDetect(idReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("idcard detect ret:" + ret);
        //识别身份证反面
        idcardUrlList[0] = "https://gss0.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/314e251f95cad1c89e04bea2763e6709c83d51f3.jpg";
        idcardUrlList[1] = "http://image2.sina.com.cn/dy/c/2004-03-29/U48P1T1D3073262F23DT20040329135445.jpg";
        idReq = new IdcardDetectRequest(bucketName, idcardUrlList, 1);
        try {
            ret = imageClient.idcardDetect(idReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("idcard detect ret:" + ret);

        //2. 图片内容方式,识别身份证正面
        System.out.println("====================================================");
        File[] idcardImageList = new File[2];
        idcardImageList[0] = new File("assets", "icon_id_01.jpg");
        idcardImageList[1] = new File("assets", "icon_id_02.jpg");
        idReq = new IdcardDetectRequest(bucketName, idcardImageList, 0);
        try {
            ret = imageClient.idcardDetect(idReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("idcard detect ret:" + ret);
        //识别身份证反面
        idcardImageList[0] = new File("assets", "icon_id_03.jpg");
        idcardImageList[1] = new File("assets", "icon_id_04.jpg");
        idReq = new IdcardDetectRequest(bucketName,  idcardImageList, 1);
        try {
            ret = imageClient.idcardDetect(idReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("idcard detect ret:" + ret);
    }

    /**
     * 标签识别操作
     */
    private static void imageTag(ImageClient imageClient, String bucketName) {
        String ret = null;
        // 1. url方式
        System.out.println("====================================================");
        String tagUrl = "http://youtu.qq.com/app/img/experience/image/icon_imag_01.jpg";
        TagDetectRequest tagReq = new TagDetectRequest(bucketName, tagUrl);
        try {
            ret = imageClient.tagDetect(tagReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
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
        try {
            ret = imageClient.tagDetect(tagReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("tag detect ret:" + ret);
    }

    /**
     * 黄图识别操作
     */
    private static void imagePorn(ImageClient imageClient, String bucketName) {
        String ret = null;
        // 1. url方式
        System.out.println("====================================================");
        String[] pornUrlList = new String[3];
        pornUrlList[0] = "http://youtu.qq.com/app/img/experience/porn/icon_porn04.jpg";
        pornUrlList[1] = "http://youtu.qq.com/app/img/experience/porn/icon_porn05.jpg";
        pornUrlList[2] = "http://youtu.qq.com/app/img/experience/porn/icon_porn06.jpg";
        PornDetectRequest pornReq = new PornDetectRequest(bucketName, pornUrlList);

        try {
            ret = imageClient.pornDetect(pornReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("porn detect ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        File[] pornImageList = new File[3];
        pornImageList[0] = new File("assets", "icon_porn04.jpg");
        pornImageList[1] = new File("assets", "icon_porn05.jpg");
        pornImageList[2] = new File("assets", "icon_porn06.jpg");
        pornReq = new PornDetectRequest(bucketName,  pornImageList);
        try {
            ret = imageClient.pornDetect(pornReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("porn detect ret:" + ret);
    }

    private static byte[] getFileBytes(File file) {
        byte[] imgBytes = null;
        try {
            RandomAccessFile f = new RandomAccessFile(file, "r");
            imgBytes = new byte[(int) f.length()];
            f.readFully(imgBytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgBytes;
    }

}
