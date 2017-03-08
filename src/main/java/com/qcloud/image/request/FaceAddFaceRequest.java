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

/**
 *
 * @author serenazhao 增加人脸
 */
public class FaceAddFaceRequest extends AbstractBaseRequest {
        //是否是url
        private boolean isUrl = true;
        
    	// url列表
	private ArrayList<String> urlList = new ArrayList<String>();
        
	// 图片内容列表
        private HashMap<String, String> imageList = new HashMap<String, String>();
            
        //指定的个体id 
        private String personId = "";
        
        //指定的个体标签
        private String personTag = "";
        
        //设置列表传参的key
        private  HashMap<String, String> keyList = new HashMap<String, String>();
  
        public FaceAddFaceRequest(String bucketName, String[] urlList, String personId, String personTag) {
		super(bucketName);
		this.isUrl = true;
                this.personTag = personTag;
                this.personId = personId;
                for(int i = 0; i < urlList.length; i++){
                    this.urlList.add(urlList[i]);
                }
	}

        public FaceAddFaceRequest(String bucketName, String[] name, String[] image, String personId, String personTag) {
		super(bucketName);
		this.isUrl = false;
                this.personTag = personTag;
                this.personId = personId;
                for(int i = 0; i < name.length; i++){
                    this.imageList.put(name[i], image[i]);
                    this.keyList.put(name[i],String.format( "images[%d]", i));
                }
	}
        
        public boolean isUrl() {
            return isUrl;
        }
        
        public ArrayList<String> getUrlList() {
            return urlList;
        }
        
        public String getPersonId() {
            return personId;
        }
        
        public String getPersonTag() {
            return personTag;
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
                    CommonParamCheckUtils.AssertExceed("url list", urlList.size(), ClientConfig.getMaxListNum());
                }else{
                    CommonParamCheckUtils.AssertNotZero("image list", imageList.size());
                    CommonParamCheckUtils.AssertExceed("image list", imageList.size(), ClientConfig.getMaxListNum());
                }
	}
}
