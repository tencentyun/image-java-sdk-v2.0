/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcloud.image.request;

import java.util.ArrayList;
import com.qcloud.image.common_utils.CommonParamCheckUtils;
import com.qcloud.image.exception.ParamException;
import java.util.HashMap;
import com.qcloud.image.ClientConfig;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author serenazhao 名片ocr识别请求
 */
public class NamecardDetectRequest extends AbstractBaseRequest {
        //是否是url
        private boolean isUrl = true;
        
        //0不返回图片，1返回图片         
        private int retImage = 0;
        
    	// url列表
	private ArrayList<String> urlList = new ArrayList<String>();
        
	// 图片内容列表,key=image name, key=image data
        private HashMap<String, String> imageList = new HashMap<String, String>();
        
        //设置列表传参的key
        private HashMap<String, String> keyList = new HashMap<String, String>();
        
        /**
         * @param bucketName  bucketName
         * @param urlList urlList
         * @param    retImage 0不返回图片，1返回图片
         */
        public NamecardDetectRequest(String bucketName, String[] urlList, int retImage) {
		super(bucketName);
		this.isUrl = true;
                this.retImage = retImage;
                for(int i = 0; i < urlList.length; i++){
                    this.urlList.add(urlList[i]);                   
                }
	}

        /**
         * 
         * @param bucketName bucketName
         * @param name 文件名
         * @param image 文件内容
         * @param retImage 0不返回图片，1返回图片
         */
        public NamecardDetectRequest(String bucketName, String[] name, String[] image, int retImage) {
		super(bucketName);
		this.isUrl = false;
                this.retImage = retImage;
                String pornName;
                for(int i = 0; i < name.length; i++){
                    try {
                        pornName = URLEncoder.encode(name[i],"UTF-8");
                        this.imageList.put(pornName, image[i]);
                        this.keyList.put(pornName,String.format( "image[%d]", i));
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(NamecardDetectRequest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
	}
        
        public boolean isUrl() {
            return isUrl;
        }
        
        public ArrayList<String> getUrlList() {
            return urlList;
        }
        
        public int getRetImage() {
            return retImage;
        }

        public void setUrlList(ArrayList<String> urlList) {
            this.urlList = urlList;
        }

        public HashMap<String, String> getImageList() {
            return imageList;
        }

        public HashMap<String, String> getKeyList() {
            return keyList;
        }
         
        public void setImageList(HashMap<String, String> imageList) {
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
                
                if (retImage != 0 && retImage!= 1) {
                    throw new ParamException( "param retImage error, please check!");
                }
	}
}
