/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcloud.image.request;

import com.qcloud.image.ClientConfig;
import com.qcloud.image.common_utils.CommonParamCheckUtils;
import com.qcloud.image.exception.ParamException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author serenazhao 身份证ocr识别请求
 */
public class IdcardDetectRequest extends AbstractBaseRequest {
        //是否是url
        private boolean isUrl = true;
        
        //0为身份证有照片的一面，1为身份证有国徽的一面            
        private int cardType = 0;
        
    	// url列表
	private ArrayList<String> urlList = new ArrayList<String>();
        
        //设置列表传参的key
        private HashMap<String, String> keyList =  new HashMap<String, String>();
        
	// 图片内容列表,key=image name
        private HashMap<String, File> imageList = new HashMap<String, File>();
              
        /**
         * @param bucketName bucketName
         * @param urlList urlList
         * @param cardType 0为身份证有照片的一面，1为身份证有国徽的一面 
         */
        public IdcardDetectRequest(String bucketName, String[] urlList, int cardType) {
		super(bucketName);
		this.isUrl = true;
                this.cardType = cardType;
                for (int i = 0; i < urlList.length; i++) {
                    this.urlList.add(urlList[i]);
                }
	}

        /**
         * 
         * @param bucketName bucketName
         * @param image 图片内容
         * @param cardType  0为身份证有照片的一面，1为身份证有国徽的一面   
         */
        public IdcardDetectRequest(String bucketName, File[] image, int cardType) {
            super(bucketName);
            this.isUrl = false;
            this.cardType = cardType;
            for (int i = 0; i < image.length; i++) {
                this.imageList.put(i + "", image[i]);
                this.keyList.put(i + "", String.format("image[%d]", i));
            }
        }
        
        public boolean isUrl() {
            return isUrl;
        }
        
        public ArrayList<String> getUrlList() {
            return urlList;
        }
        
        public int getCardType() {
            return cardType;
        }
        
        public HashMap<String, String> getKeyList() {
            return keyList;
        }

        public void setUrlList(ArrayList<String> urlList) {
            this.urlList = urlList;
        }

        public HashMap<String, File> getImageList() {
            return imageList;
        }

        public void setImageList(HashMap<String, File> imageList) {
            this.imageList = imageList;
        }

	@Override
	public void check_param() throws ParamException {
		super.check_param();
                if(isUrl){
                    CommonParamCheckUtils.AssertNotZero("url list", urlList.size());
                    CommonParamCheckUtils.AssertExceed("url list", urlList.size(), ClientConfig.getMaxDetectionNum());
                }else{
                    CommonParamCheckUtils.AssertNotZero("image list", imageList.size());
                    CommonParamCheckUtils.AssertExceed("image list", imageList.size(), ClientConfig.getMaxDetectionNum());
                }
                
                if (cardType != 0 && cardType!= 1) {
                    throw new ParamException( "param cardType error, please check!");
                }
	}
}
