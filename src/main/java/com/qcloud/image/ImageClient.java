package com.qcloud.image;

import com.qcloud.image.exception.AbstractImageException;
import com.qcloud.image.exception.UnknownException;
import com.qcloud.image.http.AbstractImageHttpClient;
import com.qcloud.image.http.DefaultImageHttpClient;
import com.qcloud.image.op.DetectionOp;
import com.qcloud.image.request.AbstractBaseRequest;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Proxy;

/**
 * @author chengwu 封装Image JAVA SDK暴露给用户的接口函数
 */
public class ImageClient implements Image {

    private static final Logger LOG = LoggerFactory.getLogger(ImageClient.class);

    private ClientConfig config;
    private Credentials cred;
    private AbstractImageHttpClient client;

    private DetectionOp detectionOp;

    public ImageClient(String appId, String secretId, String secretKey) {
        this(new Credentials(appId, secretId, secretKey));
    }

    public ImageClient(Credentials cred) {
        this(new ClientConfig(), cred);
    }

    public void setConfig(ClientConfig config) {
        this.config = config;
        this.detectionOp.setConfig(config);
        this.client.shutdown();
        this.client = new DefaultImageHttpClient(config);
        this.detectionOp.setHttpClient(this.client);
    }

    public void setCred(Credentials cred) {
        this.cred = cred;
        this.detectionOp.setCred(cred);
    }

    public void setProxy(Proxy proxy) {
        this.config.setProxy(proxy);
    }

    public ImageClient(ClientConfig config, Credentials cred) {
        this.config = config;
        this.cred = cred;
        this.client = new DefaultImageHttpClient(config);
        detectionOp = new DetectionOp(this.config, this.cred, this.client);
    }

    private void recordException(String methodName, AbstractBaseRequest request, String message) {
        LOG.error(methodName + "occur a exception, request:{}, message:{}", request, message);
    }

    @Override
    public String pornDetect(PornDetectRequest request) {
        try {
            return detectionOp.pornDetect(request);
        } catch (AbstractImageException e) {
            recordException("pornDetect", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("pornDetect", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String tagDetect(TagDetectRequest request) {
        try {
            return detectionOp.tagDetect(request);
        } catch (AbstractImageException e) {
            recordException("tagDetect", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("tagDetect", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String idcardDetect(IdcardDetectRequest request) {
        try {
            return detectionOp.idcardDetect(request);
        } catch (AbstractImageException e) {
            recordException("idcardDetect", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("idcardDetect", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String namecardDetect(NamecardDetectRequest request) {
        try {
            return detectionOp.namecardDetect(request);
        } catch (AbstractImageException e) {
            recordException("namecardDetect", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("namecardDetect", request, e1.toString());
            return e1.toString();
        }
    }

    @Override
    public String ocrBizLicense(OcrBizLicenseRequest request) {
        try {
            return detectionOp.ocrBizLicense(request);
        } catch (AbstractImageException e) {
            recordException("generalOcr", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("generalOcr", request, e1.toString());
            return e1.toString();
        }
    }

    @Override
    public String ocrBankCard(OcrBankCardRequest request) {
        try {
            return detectionOp.ocrBankCard(request);
        } catch (AbstractImageException e) {
            recordException("generalOcr", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("generalOcr", request, e1.toString());
            return e1.toString();
        }
    }
    @Override
    public String ocrPlate(OcrPlateRequest request) {
        try {
            return detectionOp.ocrPlate(request);
        } catch (AbstractImageException e) {
            recordException("generalOcr", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("generalOcr", request, e1.toString());
            return e1.toString();
        }
    }

    @Override
    public String ocrDrivingLicence(OcrDrivingLicenceRequest request) {
        try {
            return detectionOp.ocrDrivingLicence(request);
        } catch (AbstractImageException e) {
            recordException("generalOcr", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("generalOcr", request, e1.toString());
            return e1.toString();
        }
    }

    @Override
    public String generalOcr(GeneralOcrRequest request) {
        try {
            return detectionOp.generalOcr(request);
        } catch (AbstractImageException e) {
            recordException("generalOcr", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("generalOcr", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String faceDetect(FaceDetectRequest request) {
        try {
            return detectionOp.faceDetect(request);
        } catch (AbstractImageException e) {
            recordException("faceDetect", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceDetect", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String faceShape(FaceShapeRequest request) {
        try {
            return detectionOp.faceShape(request);
        } catch (AbstractImageException e) {
            recordException("faceShape", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceShape", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String faceNewPerson(FaceNewPersonRequest request) {
        try {
            return detectionOp.faceNewPerson(request);
        } catch (AbstractImageException e) {
            recordException("faceNewPerson", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceNewPerson", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String faceDelPerson(FaceDelPersonRequest request) {
        try {
            return detectionOp.faceDelPerson(request);
        } catch (AbstractImageException e) {
            recordException("faceDelPerson", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceDelPerson", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String faceAddFace(FaceAddFaceRequest request) {
        try {
            return detectionOp.faceAddFace(request);
        } catch (AbstractImageException e) {
            recordException("faceAddFace", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceAddFace", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String faceDelFace(FaceDelFaceRequest request) {
        try {
            return detectionOp.faceDelFace(request);
        } catch (AbstractImageException e) {
            recordException("faceDelFace", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceDelFace", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String faceSetInfo(FaceSetInfoRequest request) {
        try {
            return detectionOp.faceSetInfo(request);
        } catch (AbstractImageException e) {
            recordException("faceSetInfo", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceSetInfo", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String faceGetInfo(FaceGetInfoRequest request) {
        try {
            return detectionOp.faceGetInfo(request);
        } catch (AbstractImageException e) {
            recordException("faceGetInfo", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceGetInfo", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String faceGetGroupIds(FaceGetGroupIdsRequest request) {
        try {
            return detectionOp.faceGetGroupIds(request);
        } catch (AbstractImageException e) {
            recordException("faceGetGroupIds", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceGetGroupIds", request, e1.toString());
            return e1.toString();
        }
    }

    @Override
    public String faceAddGroupIds(FaceAddGroupIdsRequest request, boolean useNewDomain) {
        try {
            return detectionOp.faceAddGroupIds(request, useNewDomain);
        } catch (AbstractImageException e) {
            recordException("faceGetGroupIds", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceGetGroupIds", request, e1.toString());
            return e1.toString();
        }
    }

    @Override
    public String faceDelGroupIds(FaceDelGroupIdsRequest request, boolean useNewDomain) {
        try {
            return detectionOp.faceDelGroupIds(request, useNewDomain);
        } catch (AbstractImageException e) {
            recordException("faceGetGroupIds", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceGetGroupIds", request, e1.toString());
            return e1.toString();
        }
    }

    @Override
    public String faceGetPersonIds(FaceGetPersonIdsRequest request) {
        try {
            return detectionOp.faceGetPersonIds(request);
        } catch (AbstractImageException e) {
            recordException("faceGetPersonIds", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceGetPersonIds", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String faceGetFaceIds(FaceGetFaceIdsRequest request) {
        try {
            return detectionOp.faceGetFaceIds(request);
        } catch (AbstractImageException e) {
            recordException("faceGetFaceIds", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceGetFaceIds", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String faceGetFaceInfo(FaceGetFaceInfoRequest request) {
        try {
            return detectionOp.faceGetFaceInfo(request);
        } catch (AbstractImageException e) {
            recordException("faceGetInfo", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceGetInfo", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String faceIdentify(FaceIdentifyRequest request) {
        try {
            return detectionOp.faceIdentify(request);
        } catch (AbstractImageException e) {
            recordException("faceIdentify", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceIdentify", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String faceVerify(FaceVerifyRequest request) {
        try {
            return detectionOp.faceVerify(request);
        } catch (AbstractImageException e) {
            recordException("faceVerify", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceVerify", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String faceCompare(FaceCompareRequest request) {
        try {
            return detectionOp.faceCompare(request);
        } catch (AbstractImageException e) {
            recordException("faceCompare", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceCompare", request, e1.toString());
            return e1.toString();
        }
    }

    @Override
    public String faceMultiIdentify(FaceMultiIdentifyRequest request, boolean useNewDomain) {
        try {
            return detectionOp.faceMultiIdentify(request,useNewDomain);
        } catch (AbstractImageException e) {
            recordException("faceMultiIdentify", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceMultiIdentify", request, e1.toString());
            return e1.toString();
        }
    }

    @Override
    public String faceIdCardCompare(FaceIdCardCompareRequest request) {
        try {
            return detectionOp.faceIdCardCompare(request);
        } catch (AbstractImageException e) {
            recordException("faceIdCardCompare", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceIdCardCompare", request, e1.toString());
            return e1.toString();
        }
    }
    
     @Override
    public String faceLiveGetFour(FaceLiveGetFourRequest request) {
        try {
            return detectionOp.faceLiveGetFour(request);
        } catch (AbstractImageException e) {
            recordException("faceLiveGetFour", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceLiveGetFour", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String faceIdCardLiveDetectFour(FaceIdCardLiveDetectFourRequest request) {
        try {
            return detectionOp.faceIdCardLiveDetectFour(request);
        } catch (AbstractImageException e) {
            recordException("faceIdCardLiveDetectFour", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceIdCardLiveDetectFour", request, e1.toString());
            return e1.toString();
        }
    }
    
    @Override
    public String faceLiveDetectFour(FaceLiveDetectFourRequest request) {
        try {
            return detectionOp.faceLiveDetectFour(request);
        } catch (AbstractImageException e) {
            recordException("faceLiveDetectFour", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceLiveDetectFour", request, e1.toString());
            return e1.toString();
        }
    }
    @Override
    public String faceLiveDetectPicture(FaceLiveDetectPictureRequest request,boolean useNewDomain) {
        try {
            return detectionOp.faceLiveDetectPicture(request,useNewDomain);
        } catch (AbstractImageException e) {
            recordException("faceLiveDetectFour", request, e.toString());
            return e.toString();
        } catch (Exception e) {
            UnknownException e1 = new UnknownException(e.toString());
            recordException("faceLiveDetectFour", request, e1.toString());
            return e1.toString();
        }
    }

    @Override
    public void shutdown() {
        this.client.shutdown();
    }

}
