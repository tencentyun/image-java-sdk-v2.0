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
 * @author serenazhao 人脸对比请求
 */
public class FaceCompareRequest extends AbstractBaseRequest {
        //人脸对比类型，是否是url识别
        private boolean isUrl;
        
        //A图片的url
        private String urlA = "";
        
        //B图片的url
        private String urlB = "";
        
    	// url列表
	private ArrayList<String> urlList = new ArrayList<String>();
        
         //设置列表传参的key
        private HashMap<String, String> keyList = new HashMap<String, String>();
        
	// 图片内容列表,key=imageA, key=imageB
        private HashMap<String, File> imageList = new HashMap<String, File>();
        
	public FaceCompareRequest(String bucketName, String urlA, String urlB) {
		super(bucketName);
		this.isUrl = true;
                this.urlA = urlA;
                this.urlB = urlB;
	}

    public FaceCompareRequest(String bucketName, File imageA, File imageB) {
        super(bucketName);
        this.isUrl = false;
        this.keyList.put("key1", "imageA");
        this.keyList.put("key2", "imageB");
        this.imageList.put("key1", imageA);
        this.imageList.put("key2", imageB);
    }
        
        public boolean isUrl() {
            return isUrl;
        }
        
        public String getUrlA() {
            return urlA;
        }
        
        public String getUrlB() {
            return urlB;
        }

        public ArrayList<String> getUrlList() {
            return urlList;
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
                    CommonParamCheckUtils.AssertNotNull("urlA", urlA);
                    CommonParamCheckUtils.AssertNotNull("urlB", urlB);  
                }else{
                    CommonParamCheckUtils.AssertNotZero("image list", imageList.size());
                    CommonParamCheckUtils.AssertExceed("image list", imageList.size(), ClientConfig.getMaxListNum());
                }
	}

	
}
