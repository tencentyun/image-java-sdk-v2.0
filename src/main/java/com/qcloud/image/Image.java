package com.qcloud.image;
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
import com.qcloud.image.request.FaceCompareRequest;
import com.qcloud.image.request.FaceIdCardCompareRequest;
import com.qcloud.image.request.FaceLiveGetFourRequest;
import com.qcloud.image.request.FaceIdCardLiveDetectFourRequest;
import com.qcloud.image.request.FaceLiveDetectFourRequest;
import com.qcloud.image.request.FaceLiveDetectFourRequest; 
import com.qcloud.image.request.FaceVerifyRequest;
        
/**
 * @author chengwu
 * Image提供给用户使用的API接口
 */

public interface Image {

	/**
	 * 黄图识别接口
	 * 
	 * @param request
	 *            黄图识别请求
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */
    String pornDetect(PornDetectRequest request);
    
    /**
	 *标签识别接口
	 * 
	 * @param request
	 *            标签识别请求
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */
    String tagDetect(TagDetectRequest request);
    
    /**
	 *身份证识别接口
	 * 
	 * @param request
	 *            身份证识别请求
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */
    String idcardDetect(IdcardDetectRequest request);
    
        /**
	 *名片识别接口
	 * 
	 * @param request
	 *            名片ocr识别请求
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */
    String namecardDetect(NamecardDetectRequest request);
    
        /**
	 *人脸检测接口
	 * 
	 * @param request
	 *            人连检测请求
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */
    String faceDetect(FaceDetectRequest request);
    
        /**
	 *人脸定位接口
	 * 
	 * @param request
	 *            人脸定位接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */
    String faceShape(FaceShapeRequest request);
    
        /**
	 *个体创建接口
	 * 
	 * @param request
	 *            个体创建接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceNewPerson(FaceNewPersonRequest request);
     
     /**
	 *个体删除接口
	 * 
	 * @param request
	 *            个体删除接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceDelPerson(FaceDelPersonRequest request);
     
      /**
	 *增加人脸接口
	 * 
	 * @param request
	 *            增加人脸接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceAddFace(FaceAddFaceRequest request);
     
        /**
	 *删除人脸接口
	 * 
	 * @param request
	 *            删除人脸接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceDelFace(FaceDelFaceRequest request);

     /**
	 *个体设置信息接口
	 * 
	 * @param request
	 *            人脸设置信息接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceSetInfo(FaceSetInfoRequest request);
     
     /**
	 *个体获取信息接口
	 * 
	 * @param request
	 *            个体获取信息接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceGetInfo(FaceGetInfoRequest request);
     
      /**
	 *获取组列表接口
	 * 
	 * @param request
	 *            获取组列表接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceGetGroupIds(FaceGetGroupIdsRequest request);
     
      /**
	 *获取人列表接口
	 * 
	 * @param request
	 *            获取人列表接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceGetPersonIds(FaceGetPersonIdsRequest request);
     
    /**
	 *获取人脸列表接口
	 * 
	 * @param request
	 *            获取人脸列表接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceGetFaceIds(FaceGetFaceIdsRequest request);
     
      /**
	 *获取人脸信息接口
	 * 
	 * @param request
	 *            获取人脸信息接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceGetFaceInfo(FaceGetFaceInfoRequest request);
     
       /**
	 *获取人脸识别接口
	 * 
	 * @param request
	 *            获取人脸识别接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceIdentify(FaceIdentifyRequest request);
     
     /**
	 *人脸验证接口
	 * 
	 * @param request
	 *            人脸验证接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceVerify(FaceVerifyRequest request);
     
      /**
	 *人脸对比接口
	 * 
	 * @param request
	 *            人脸对比接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceCompare(FaceCompareRequest request);
     
     /**
	 *身份证对比接口
	 * 
	 * @param request
	 *            身份证对比接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceIdCardCompare(FaceIdCardCompareRequest request);
     
        /**
	 *获取验证码接口
	 * 
	 * @param request
	 *            获取验证码接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceLiveGetFour(FaceLiveGetFourRequest request);
     
       /**
	 *身份对比接口
	 * 
	 * @param request
	 *            身份对比接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceIdCardLiveDetectFour(FaceIdCardLiveDetectFourRequest request);
     
      /**
	 *身份检测接口
	 * 
	 * @param request
	 *            身份检测接口
	 * @return JSON格式的字符串, 格式为{"code":$code, "message":"$mess"}, code为0表示成功,
	 *         其他为失败, message为success或者失败原因
	 */    
     String faceLiveDetectFour(FaceLiveDetectFourRequest request);
     
    /**
     * 关闭Image客户端连接池，释放涉及的资源，释放后，不能再使用Image的接口，必须重新生成一个新对象
     */
    void shutdown();

}
