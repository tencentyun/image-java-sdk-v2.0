/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.qcloud.image.demo;

import com.qcloud.image.*;
import com.qcloud.image.common_utils.CommonFileUtils;
import com.qcloud.image.request.PornDetectRequest;
import com.qcloud.image.request.TagDetectRequest;
import com.qcloud.image.request.IdcardDetectRequest;
import com.qcloud.image.request.NamecardDetectRequest;
import com.qcloud.image.request.FaceDetectRequest;
import com.qcloud.image.request.FaceShapeRequest;
import com.qcloud.image.request.FaceNewPersonRequest;
import com.qcloud.image.request.FaceDelPersonRequest;
import com.qcloud.image.request.FaceAddFaceRequest;
import com.qcloud.image.request.FaceDelFaceRequest;
import com.qcloud.image.request.FaceSetInfoRequest;
import com.qcloud.image.request.FaceGetInfoRequest;
import com.qcloud.image.request.FaceGetGroupIdsRequest;
import com.qcloud.image.request.FaceGetPersonIdsRequest;
import com.qcloud.image.request.FaceGetFaceIdsRequest;
import com.qcloud.image.request.FaceGetFaceInfoRequest;
import com.qcloud.image.request.FaceIdentifyRequest;
import com.qcloud.image.request.FaceVerifyRequest;
import com.qcloud.image.request.FaceCompareRequest;
import com.qcloud.image.request.FaceIdCardCompareRequest;
import com.qcloud.image.request.FaceLiveGetFourRequest;
import com.qcloud.image.request.FaceIdCardLiveDetectFourRequest;
import com.qcloud.image.request.FaceLiveDetectFourRequest; 
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;


/**
 * @author serenazhao image Demo代码
 */
public class Demo {
     
    public static void main(String[] args) {

        // 设置用户属性, 包括appid, secretId和SecretKey
        // 这些属性可以通过万象优图控制台获取(https://console.qcloud.com/ci)
        int appId = 0000000;//      YOUR_APPID
        String secretId = "YOUR_SECRETID";
        String secretKey = "YOUR_SECRETKEY";
        
        // ImageClient
        ImageClient imageClient = new ImageClient(appId, secretId, secretKey);
        
        // 设置要操作的bucket
        String bucketName = "YOUR_BUCKET";
        String ret ;
       
        ///////////////////////////////////////////////////////////////
        // 黄图识别操作 //
        ///////////////////////////////////////////////////////////////
        // 1. url方式
        System.out.println("====================================================");
        String[] pornUrlList = new String[3];
        pornUrlList[0] = "YOUR URL A";
        pornUrlList[1] = "YOUR URL B";
        pornUrlList[2] = "YOUR URL C";
        PornDetectRequest pornReq = new PornDetectRequest(bucketName, pornUrlList);
        
        ret = imageClient.pornDetect(pornReq);
        System.out.println("porn detect ret:" + ret);
        
        //2. 图片内容方式
        System.out.println("====================================================");
        String[] pornNameList = new String[3];
        String[] pornImageList = new String[3];
        try {
            pornNameList[0] = "test.jpg";
            pornImageList[0] = CommonFileUtils.getFileContent("F:\\pic\\test.jpg");
            pornNameList[1] = "hot1.jpg";
            pornImageList[1] = CommonFileUtils.getFileContent("F:\\pic\\hot1.jpg");
            pornNameList[2] = "hot2.jpg";
            pornImageList[2] = CommonFileUtils.getFileContent("F:\\pic\\hot2.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        pornReq = new PornDetectRequest(bucketName, pornNameList, pornImageList);
        ret = imageClient.pornDetect(pornReq);
        System.out.println("porn detect ret:" + ret);
       
        ///////////////////////////////////////////////////////////////
        //标签识别操作 //
        ///////////////////////////////////////////////////////////////
        // 1. url方式
        System.out.println("====================================================");
        String tagUrl = "YOUR URL";
        TagDetectRequest tagReq = new TagDetectRequest(bucketName, tagUrl);
        ret = imageClient.tagDetect(tagReq);
        System.out.println("tag detect ret:" + ret);
        
        // 2. 图片内容方式
        System.out.println("====================================================");
        byte[] tagImage = {0};
        try {
            tagImage = CommonFileUtils.getFileContentByte("F:\\pic\\test.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }

        tagReq = new TagDetectRequest(bucketName, tagImage);
        ret = imageClient.tagDetect(tagReq);
        System.out.println("tag detect ret:" + ret);
        

         ///////////////////////////////////////////////////////////////
        // 身份证ocr识别操作 //
        ///////////////////////////////////////////////////////////////
        // 1. url方式,识别身份证正面
        System.out.println("====================================================");
        String[] idcardUrlList = new String[2];
        idcardUrlList[0] = "YOUR URL A";
        idcardUrlList[1] = "YOUR URL B";
        IdcardDetectRequest idReq = new IdcardDetectRequest(bucketName, idcardUrlList, 0); 
        ret = imageClient.idcardDetect(idReq);
        System.out.println("idcard detect ret:" + ret);
        //识别身份证反面
        idcardUrlList[0] = "YOUR URL C";
        idcardUrlList[1] = "YOUR URL D";
        idReq = new IdcardDetectRequest(bucketName, idcardUrlList, 1);   
        ret = imageClient.idcardDetect(idReq);
        System.out.println("idcard detect ret:" + ret);
        
        //2. 图片内容方式,识别身份证正面
        System.out.println("====================================================");
        String[] idcardNameList = new String[2];
        String[] idcardImageList = new String[2];
        try {
            idcardNameList[0] = "id6_zheng.jpg";
            idcardImageList[0] = CommonFileUtils.getFileContent("F:\\pic\\id6_zheng.jpg");
            idcardNameList[1] = "id2_zheng.jpg";
            idcardImageList[1] = CommonFileUtils.getFileContent("F:\\pic\\id2_zheng.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        idReq = new IdcardDetectRequest(bucketName, idcardNameList, idcardImageList, 0);
        ret = imageClient.idcardDetect(idReq);
        System.out.println("idcard detect ret:" + ret);
        //识别身份证反面
        try {
            idcardNameList[0] = "id5_fan.png";
            idcardImageList[0] = CommonFileUtils.getFileContent("F:\\pic\\id5_fan.jpg");
            idcardNameList[1] = "id7_fan.jpg";
            idcardImageList[1] = CommonFileUtils.getFileContent("F:\\pic\\id7_fan.png");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        idReq = new IdcardDetectRequest(bucketName, idcardNameList, idcardImageList, 1);
        ret = imageClient.idcardDetect(idReq);
        System.out.println("idcard detect ret:" + ret);
        
        
        
        ///////////////////////////////////////////////////////////////
        // 名片ocr识别操作 //
        ///////////////////////////////////////////////////////////////
        // 1. url方式
        System.out.println("====================================================");
        String[] namecardUrlList = new String[2];
        namecardUrlList[0] = "YOUR URL A";
        namecardUrlList[1] = "YOUR URL B";
        NamecardDetectRequest nameReq = new NamecardDetectRequest(bucketName, namecardUrlList, 0);
 
        ret = imageClient.namecardDetect(nameReq);
        System.out.println("namecard detect ret:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        String[] namecardNameList = new String[2];
        String[] namecardImageList = new String[2];
        try {
            namecardNameList[0] = "name2.jpg";
            namecardImageList[0] = CommonFileUtils.getFileContent("F:\\pic\\name2.jpg");
            namecardNameList[1] = "名片.jpg";
            namecardImageList[1] = CommonFileUtils.getFileContent("F:\\pic\\名片.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        nameReq = new NamecardDetectRequest(bucketName, namecardNameList, namecardImageList, 0);
        ret = imageClient.namecardDetect(nameReq);
        System.out.println("namecard detect ret:" + ret);
         
        
        ///////////////////////////////////////////////////////////////
        // 人脸检测操作 //
        ///////////////////////////////////////////////////////////////
        // 1. url方式
        System.out.println("====================================================");
        String faceDetectUrl = "YOUR URL";
        FaceDetectRequest faceDetectReq = new FaceDetectRequest(bucketName, faceDetectUrl, 1);
 
        ret = imageClient.faceDetect(faceDetectReq);
        System.out.println("face detect ret:" + ret);
        
         //2. 图片内容方式
        System.out.println("====================================================");
        String faceDetectName  = "";
        String faceDetectImage = "";
        try {
            faceDetectName = "face1.jpg";
            faceDetectImage = CommonFileUtils.getFileContent("F:\\pic\\face1.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        faceDetectReq = new FaceDetectRequest(bucketName, faceDetectName, faceDetectImage, 1);
        ret = imageClient.faceDetect(faceDetectReq);
        System.out.println("face detect ret:" + ret);
        
        
        ///////////////////////////////////////////////////////////////
        // 五官定位操作 //
        ///////////////////////////////////////////////////////////////
        // 1. url方式
        System.out.println("====================================================");
        String faceShapeUrl = "YOUR URL";
        FaceShapeRequest faceShapeReq = new FaceShapeRequest(bucketName, faceShapeUrl, 1);
 
        ret = imageClient.faceShape(faceShapeReq);
        System.out.println("face shape ret:" + ret);
        
         //2. 图片内容方式
        System.out.println("====================================================");
        String faceShapeName  = "";
        String faceShapeImage = "";
        try {
            faceShapeName = "face1.jpg";
            faceShapeImage = CommonFileUtils.getFileContent("F:\\pic\\face1.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        faceShapeReq = new FaceShapeRequest(bucketName, faceShapeName, faceShapeImage, 1);
        ret = imageClient.faceShape(faceShapeReq);
        System.out.println("face shape ret:" + ret);        
     
        
        // 个体添加操作 //
        ///////////////////////////////////////////////////////////////
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
        String personNewName  = "";
        String personNewImage = "";
        groupIds[0] = "group11";
        groupIds[1] = "group33";
        personName = "yangmi";
        personId = "persony";
        personTag = "star";
        try {
            personNewName = "yang.jpg";
            personNewImage = CommonFileUtils.getFileContent("F:\\pic\\yang.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        personNewReq = new FaceNewPersonRequest(bucketName, personId, groupIds, personNewName, personNewImage, personName, personTag);
        ret = imageClient.faceNewPerson(personNewReq);
        System.out.println("person new ret:" + ret); 
        
        // 个人删除操作 //
        ///////////////////////////////////////////////////////////////
        System.out.println("====================================================");
            String delPersonId = "personY";
            FaceDelPersonRequest delPersonReq = new FaceDelPersonRequest(bucketName, personId);

            ret = imageClient.faceDelPerson(delPersonReq);
            System.out.println("face del  person ret:" + ret);

        
        ///////////////////////////////////////////////////////////////
        //增加人脸操作 //
        ///////////////////////////////////////////////////////////////
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
        String[] addFaceImageList = new String[2];
        addfacePersonId = "personY";
        addfacePersonTag = "actor";
        try {
            addFaceNameList[0] = "yang2.jpg";
            addFaceImageList[0] = CommonFileUtils.getFileContent("F:\\pic\\yang2.jpg");
            addFaceNameList[1] = "yang3.jpg";
            addFaceImageList[1] = CommonFileUtils.getFileContent("F:\\pic\\yang3.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        addFaceReq = new FaceAddFaceRequest(bucketName, addFaceNameList, addFaceImageList, addfacePersonId, addfacePersonTag);
        ret = imageClient.faceAddFace(addFaceReq);
        System.out.println("add face ret:" + ret);
        
        
        // 人脸删除操作 //
        ///////////////////////////////////////////////////////////////
        System.out.println("====================================================");
        String delFacePersonId = "personY";
        String[] delFaceIds = new String[2];
        delFaceIds[0] = "1831408218312574949";
        delFaceIds[1] = "1831408248150847230";
        FaceDelFaceRequest delFaceReq = new FaceDelFaceRequest(bucketName, delFacePersonId, delFaceIds);
 
        ret = imageClient.faceDelFace(delFaceReq);
        System.out.println("face del  ret:" + ret);
       
        
         // 个体设置信息 //
        ///////////////////////////////////////////////////////////////
        System.out.println("====================================================");
        String setInfoPersonId = "personY";
        String setInfoPersonName = "mimi";
        String setInfoTag = "actress";
        FaceSetInfoRequest setInfoReq = new FaceSetInfoRequest(bucketName, setInfoPersonId, setInfoPersonName, setInfoTag);
 
        ret = imageClient.faceSetInfo(setInfoReq);
        System.out.println("face set info  ret:" + ret);
        
        // 个体获取信息 //
        ///////////////////////////////////////////////////////////////
        System.out.println("====================================================");
        String getInfoPersonId = "personY";
        FaceGetInfoRequest getInfoReq = new FaceGetInfoRequest(bucketName, getInfoPersonId);
 
        ret = imageClient.faceGetInfo(getInfoReq);
        System.out.println("face get info  ret:" + ret);
        
        
        // 获取组列表 //
        ///////////////////////////////////////////////////////////////
        System.out.println("====================================================");
        FaceGetGroupIdsRequest getGroupReq = new FaceGetGroupIdsRequest(bucketName);
 
        ret = imageClient.faceGetGroupIds(getGroupReq);
        System.out.println("face get group ids  ret:" + ret);
        
            
        // 获取人列表 //
        ///////////////////////////////////////////////////////////////
        System.out.println("====================================================");
        String getPersonGroupId = "group1";
        FaceGetPersonIdsRequest getPersonIdsReq = new FaceGetPersonIdsRequest(bucketName, getPersonGroupId);
        
        ret = imageClient.faceGetPersonIds(getPersonIdsReq);
        System.out.println("face get person ids  ret:" + ret);
       
        
        // 获取人脸列表 //
        ///////////////////////////////////////////////////////////////
        System.out.println("====================================================");
        String getFacePersonId = "personY";
        FaceGetFaceIdsRequest getFaceIdsReq = new FaceGetFaceIdsRequest(bucketName, getFacePersonId);
        
        ret = imageClient.faceGetFaceIds(getFaceIdsReq);
        System.out.println("face get face ids  ret:" + ret);
         
        
        //  获取人脸信息 //
        ///////////////////////////////////////////////////////////////
        System.out.println("====================================================");
        String getFaceId = "1830582165978517027";
        FaceGetFaceInfoRequest getFaceInfoReq = new FaceGetFaceInfoRequest(bucketName, getFaceId);
        
        ret = imageClient.faceGetFaceInfo(getFaceInfoReq);
        System.out.println("face get face info  ret:" + ret);
        
         ///////////////////////////////////////////////////////////////
        //人脸验证操作 //
        ///////////////////////////////////////////////////////////////
        // 1. url方式
        System.out.println("====================================================");
        String  faceVerifyPersonId = "person1";
        String faceVerifyUrl = "YOUR URL";
        FaceVerifyRequest faceVerifyReq = new FaceVerifyRequest(bucketName, faceVerifyPersonId, faceVerifyUrl);
 
        ret = imageClient.faceVerify(faceVerifyReq);
        System.out.println("face verify ret:" + ret);
        
        //2. 图片内容方式
        System.out.println("====================================================");
        String faceVerifyName  = "";
        String faceVerifyImage = "";
        faceVerifyPersonId = "person3111";
        try {
            faceVerifyName = "yang3.jpg";
            faceVerifyImage = CommonFileUtils.getFileContent("F:\\pic\\yang3.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        faceVerifyReq = new FaceVerifyRequest(bucketName, faceVerifyPersonId, faceVerifyName, faceVerifyImage);
        ret = imageClient.faceVerify(faceVerifyReq);
        System.out.println("face verify ret:" + ret);
        
        ///////////////////////////////////////////////////////////////
        //人脸识别操作 //
        ///////////////////////////////////////////////////////////////
        // 1. url方式
        System.out.println("====================================================");
        String  faceIdentifyGroupId = "group1";
        String faceIdentifyUrl = "YOUR URL";
        FaceIdentifyRequest faceIdentifyReq = new FaceIdentifyRequest(bucketName, faceIdentifyGroupId, faceIdentifyUrl);
 
        ret = imageClient.faceIdentify(faceIdentifyReq);
        System.out.println("face identify ret:" + ret);
        
        //2. 图片内容方式
        System.out.println("====================================================");
        String faceIdentifyName  = "";
        String faceIdentifyImage = "";
        try {
            faceIdentifyName = "yang4.jpg";
            faceIdentifyImage = CommonFileUtils.getFileContent("F:\\pic\\yang4.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        faceIdentifyReq = new FaceIdentifyRequest(bucketName, faceIdentifyGroupId, faceIdentifyName, faceIdentifyImage);
        ret = imageClient.faceIdentify(faceIdentifyReq);
        System.out.println("face identify ret:" + ret);
        
        
        ///////////////////////////////////////////////////////////////
        // 人脸对比操作 //
        ///////////////////////////////////////////////////////////////
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
        String[] compareImageList = new String[2];
        try {
            compareNameList[0] = "zhao1.jpg";
            compareNameList[1] = "zhao2.jpg";
            compareImageList[0] = CommonFileUtils.getFileContent("F:\\pic\\zhao1.jpg");
            compareImageList[1] = CommonFileUtils.getFileContent("F:\\pic\\zhao2.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        faceCompareReq = new FaceCompareRequest(bucketName, compareNameList, compareImageList);
        ret = imageClient.faceCompare(faceCompareReq);
        System.out.println("face compare ret:" + ret);
        
        
        ///////////////////////////////////////////////////////////////
        //身份证识别对比接口 //
        ///////////////////////////////////////////////////////////////
        // 1. url方式
        System.out.println("====================================================");
        String  idcardNumber = "IDCARD NUM";
        String  idcardName = "NAME";       
        String idcardCompareUrl = "YOUR URL";
        String  sessionId = "";
        FaceIdCardCompareRequest idCardCompareReq = new FaceIdCardCompareRequest(bucketName, idcardNumber, idcardName, idcardCompareUrl, sessionId);
 
        ret = imageClient.faceIdCardCompare(idCardCompareReq);
        System.out.println("face idCard Compare ret:" + ret);
        
         //2. 图片内容方式
        System.out.println("====================================================");
        String idcardCompareName  = "";
        String idcardCompareImage = "";
        try {
            idcardCompareName = "idcard.jpg";
            idcardCompareImage = CommonFileUtils.getFileContent("F:\\pic\\idcard.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        idCardCompareReq = new FaceIdCardCompareRequest(bucketName, idcardNumber, idcardName, idcardCompareName, idcardCompareImage, sessionId);
        ret = imageClient.faceIdCardCompare(idCardCompareReq);
        System.out.println("face idCard Compare ret:" + ret);

       
        // 获取验证码接口 //
        ///////////////////////////////////////////////////////////////
        System.out.println("====================================================");
        String seq = "";
        FaceLiveGetFourRequest getFaceFourReq = new FaceLiveGetFourRequest(bucketName, seq);        
        ret = imageClient.faceLiveGetFour(getFaceFourReq);
 
       System.out.println("face live get four  ret:" + ret);
       String validate = "";
       JSONObject jsonObject = new JSONObject(ret);
       JSONObject data = jsonObject.getJSONObject("data");
       if (null != data) {
            validate = data.getString("validate_data");
       }
       
        
        ///////////////////////////////////////////////////////////////
        //通过视频对比指定身份信息接口 //
        ///////////////////////////////////////////////////////////////
        // 1. url方式
        System.out.println("====================================================");
        String  liveDetectIdcardNumber = "330782198802084329";
        String  liveDetectIdcardName = "季锦锦";  
        String  video = "";
        try {
            video = CommonFileUtils.getFileContent("F:\\pic\\ZOE_0171.mp4");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FaceIdCardLiveDetectFourRequest liveDetectReq = new FaceIdCardLiveDetectFourRequest(bucketName, validate, video, liveDetectIdcardNumber, liveDetectIdcardName, seq);
        ret = imageClient.faceIdCardLiveDetectFour(liveDetectReq);
        System.out.println("face idCard live detect four ret:" + ret);
        
        
        ///////////////////////////////////////////////////////////////
        // 检测视频和身份证是否对上操作 //
        ///////////////////////////////////////////////////////////////
        // 1. url方式
        System.out.println("====================================================");
        String  liveDetectVideo = "";
        String  liveDetectImage = "";
        String liveDetectVvalidate = "123456";        
        boolean compareFlag  = true;
        try {
            liveDetectVideo = CommonFileUtils.getFileContent("F:\\pic\\ZOE_0171.mp4");
            liveDetectImage = CommonFileUtils.getFileContent("F:\\pic\\zhao2.jpg");
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FaceLiveDetectFourRequest faceLiveDetectReq = new FaceLiveDetectFourRequest(bucketName, validate, compareFlag, video, liveDetectImage, seq);
        ret = imageClient.faceLiveDetectFour(faceLiveDetectReq);
        System.out.println("face  live detect four ret:" + ret);
        
        // 关闭释放资源
        imageClient.shutdown();
        System.out.println("shutdown!");
    }
    
}
