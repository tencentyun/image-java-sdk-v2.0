/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcloud.image.request;

import com.qcloud.image.common_utils.CommonParamCheckUtils;
import com.qcloud.image.exception.ParamException;

import java.io.File;

/**
 *
 * OCR-行驶证驾驶证识别 识别请求
 */
public class OcrDrivingLicenceRequest extends AbstractBaseRequest {

    /** 行驶证 */
    public static final int TYPE_VEHICLE_LICENSE = 0;
    /** 驾驶证 */
    public static final int TYPE_DRIVER_LICENSE = 1;
    
    private final int mType;
    private boolean isUrl;
    private String url = "";
    private File image;

    /**
     * @param type {@link #TYPE_VEHICLE_LICENSE} 或 {@link #TYPE_DRIVER_LICENSE}
     */
    public OcrDrivingLicenceRequest(String bucketName, int type, String url) {
        super(bucketName);
        mType = type;
        this.isUrl = true;
        this.url = url;
    }
    
    /**
     * @param type {@link #TYPE_VEHICLE_LICENSE} 或 {@link #TYPE_DRIVER_LICENSE}
     */
    public OcrDrivingLicenceRequest(String bucketName, int type, File image) {
        super(bucketName);
        mType = type;
        this.isUrl = false;
        this.image = image;
    }

    @Override
    public void check_param() throws ParamException {
        super.check_param();
        if (isUrl) {
            CommonParamCheckUtils.AssertNotNull("url", url);
        } else {
            CommonParamCheckUtils.AssertNotNull("image content", image);
        }
    }

    public boolean isUrl() {
        return isUrl;
    }

    public String getUrl() {
        return url;
    }

    public File getImage() {
        return image;
    }

    public int getType() {
        return mType;
    }
}
