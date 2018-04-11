package com.qcloud.image;

import java.net.Proxy;

public class ClientConfig {
    // 多次签名的默认过期时间,单位秒
    private static final int DEFAULT_SIGN_EXPIRED = 300;
    // 默认的最大重试次数(发生了socketException时)
    private static final int DEFAULT_MAX_RETRIES = 3;
    // 默认的获取连接的超时时间
    private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = 60 * 1000;
    // 默认连接超时, 单位ms
    private static final int DEFAULT_CONNECTION_TIMEOUT = 60 * 1000;
    // 默认的SOCKET读取超时时间, 默认毫秒
    private static final int DEFAULT_SOCKET_TIMEOUT = 60 * 1000;
    // 默认的维护最大HTTP连接数
    private static final int DEFAULT_MAX_CONNECTIONS_COUNT = 100;
    // 默认的user_agent标识
    private static final String DEFAULT_USER_AGENT = "image-java-sdk-v4.2";
    //图片服务域名
    private static final String QCLOUD_IMAGE_DOMAIN = "service.image.myqcloud.com";
    //OCR服务域名
    private static final String QCLOUD_OCR_DOMAIN = "recognition.image.myqcloud.com";

    //黄图识别服务api
    private static final String DETECTION_PORN = "/detection/porn_detect";
    //最大的识别限制
    private static final int MAX_DETECTION_NUM = 20;   
    //最大的列表限制
    private static final int MAX_LIST_NUM = 20;
    
    //标签识别服务api
    private static final String DETECTION_TAG = "/v1/detection/imagetag_detect";
    
    //身份证识别服务api
    private static final String DETECTION_IDCARD = "/ocr/idcard";
    
     //名片识别服务api
    private static final String DETECTION_NAMECARD = "/ocr/businesscard";

    /** 行驶证驾驶证识别 */
    public static final String OCR_DRIVINGLICENCE = "/ocr/drivinglicence";
    /** 车牌号识别 */
    public static final String OCR_PLATE = "/ocr/plate";
    /** 银行卡识别 */
    public static final String OCR_BANKCARD = "/ocr/bankcard";
    /** 营业执照识别 */
    public static final String OCR_BIZLICENSE= "/ocr/bizlicense";
    /** 通用印刷体识别 */
    public static final String OCR_GENERAL= "/ocr/general";
    
    //人脸识别服务api
    private static final String DETECTION_FACE = "/face/detect";
    
    //人脸定位服务api
    private static final String FACE_SHAPE = "/face/shape";
    
    //个体创建api
    private static final String FACE_NEW_PERSON = "/face/newperson";
      
    //个体删除api
    private static final String FACE_DEL_PERSON = "/face/delperson";
    
    //增加人脸api
    private static final String FACE_ADD_FACE = "/face/addface";
    
    //人脸删除api
    private static final String FACE_DEL_FACE = "/face/delface";
    
    //个体设置信息api
    private static final String FACE_SET_INFO = "/face/setinfo";
    
    //个体获取信息api
    private static final String FACE_GET_INFO = "/face/getinfo";
    
    //获取组列表api
    private static final String FACE_GET_GROUPIDS = "/face/getgroupids";
    
    //获取人列表api
    private static final String FACE_GET_PERSONIDS = "/face/getpersonids";
    
    //获取人脸列表api
    private static final String FACE_GET_FACEIDS = "/face/getfaceids";
    
    //获取人脸信息api
    private static final String FACE_GET_FACEINFO = "/face/getfaceinfo";
    
    //人脸识别api
    private static final String FACE_IDENTIFY = "/face/identify";
    
    //人脸验证api
    private static final String FACE_VERIFY = "/face/verify";
    
    //人脸对比api
    private static final String FACE_COMPARE = "/face/compare";
    
    //身份证识别对比api
    private static final String FACE_IDCARD_COMPARE = "/face/idcardcompare";
    
    //获取验证码api
    private static final String FACE_LIVE_GET_FOUR = "/face/livegetfour";
    
    //检测api
    private static final String FACE_LIVE_DETECT_FOUR = "/face/livedetectfour";
    
    //对比指定身份信息api
    private static final String FACE_IDCARD_LIVE_DETECT_FOUR = "/face/idcardlivedetectfour";
    
    private int signExpired = DEFAULT_SIGN_EXPIRED;
    private int maxFailedRetry = DEFAULT_MAX_RETRIES;
    private int connectionRequestTimeout = DEFAULT_CONNECTION_REQUEST_TIMEOUT;
    private int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
    private int socketTimeout = DEFAULT_SOCKET_TIMEOUT;
    private int maxConnectionsCount = DEFAULT_MAX_CONNECTIONS_COUNT;
    private String userAgent = DEFAULT_USER_AGENT;
    private Proxy mProxy;

    public void setProxy(Proxy proxy) {
        mProxy = proxy;
    }

    public Proxy getProxy() {
        return mProxy;
    }
    

    public String getQCloudOcrDomain() {
        return QCLOUD_OCR_DOMAIN;
    }

    public int getMaxFailedRetry() {
        return maxFailedRetry;
    }

    public void setMaxFailedRetry(int maxFailedRetry) {
        this.maxFailedRetry = maxFailedRetry;
    }
  
    public int getSignExpired() {
	return signExpired;
    }

    public void setSignExpired(int signExpired) {
        this.signExpired = signExpired;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getMaxConnectionsCount() {
        return maxConnectionsCount;
    }

    public void setMaxConnectionsCount(int maxConnectionsCount) {
        this.maxConnectionsCount = maxConnectionsCount;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    public String getQCloudImageDomain() {
        return QCLOUD_IMAGE_DOMAIN;
    }

    public String getDetectionPorn() {
        return DETECTION_PORN;
    }
    
    public static int getMaxDetectionNum() {
        return MAX_DETECTION_NUM;
    }
    
    public static int getMaxListNum() {
        return MAX_LIST_NUM;
    }
    
    public String getDetectionTag() {
        return DETECTION_TAG;
    }
    
    public String getDetectionIdcard() {
        return DETECTION_IDCARD;
    }
    
    public String getDetectionNamecard() {
        return DETECTION_NAMECARD;
    }
    
    public String getDetectionFace() {
        return DETECTION_FACE;
    }
    
    public String getFaceShape() {
        return FACE_SHAPE;
    }
    
    public String getFaceNewPerson() {
        return FACE_NEW_PERSON;
    }
    
    public String getFaceDelPerson() {
        return FACE_DEL_PERSON;
    }
    
    public String getFaceAddFace() {
        return FACE_ADD_FACE;
    }
    
    public String getFaceDelFace() {
        return FACE_DEL_FACE;
    }
    
    public String getFaceSetInfo(){
        return FACE_SET_INFO;
    }  
    
    public String getFaceGetInfo() {
        return FACE_GET_INFO; 
    } 
    
    public String getFaceGetGroupIdsInfo() {
        return FACE_GET_GROUPIDS;
    } 
    
    public String getFaceGetPersonIdsInfo() {
        return FACE_GET_PERSONIDS;
    } 
    
    public String getFaceGetFaceIdsInfo() {
        return FACE_GET_FACEIDS;
    } 
    
    public String getFaceGetFaceInfo() {
        return FACE_GET_FACEINFO;
    }
    
    public String getFaceIdentify() {
        return FACE_IDENTIFY;
    }
    
    public String getFaceVerify() {
        return FACE_VERIFY;
    }
    
    public String getFaceCompare() {
        return FACE_COMPARE;
    }
    
    public String getFaceIdcardCompare() {
        return FACE_IDCARD_COMPARE;
    }
    
    public String getFaceLiveGetFour() {
        return FACE_LIVE_GET_FOUR;
    }
    
    public String getFaceLiveDetectFour() {
        return FACE_LIVE_DETECT_FOUR;
    }
    
    public String getFaceIdCardLiveDetectFour() {
        return FACE_IDCARD_LIVE_DETECT_FOUR;
    }
}
