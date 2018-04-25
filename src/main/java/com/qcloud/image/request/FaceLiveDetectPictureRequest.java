/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcloud.image.request;

import java.io.File;

/**
 * @author serenazhao  获取人脸列表
 */
public class FaceLiveDetectPictureRequest extends AbstractBaseRequest {

    private String mImageUrl;
    private boolean isUrl = false;
    private File mImage;

    public FaceLiveDetectPictureRequest(String bucketName, File image) {
        super(bucketName);
        mImage = image;
        isUrl = false;
    }
    public FaceLiveDetectPictureRequest(String bucketName, byte[] image) {
        super(bucketName);
        setBytesContent("image", image);
        isUrl = false;
    }

    public FaceLiveDetectPictureRequest(String bucketName, String imageUrl) {
        super(bucketName);
        mImageUrl = imageUrl;
        isUrl = true;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public File getImage() {
        return mImage;
    }

    public boolean isUrl() {
        return isUrl;
    }
}
