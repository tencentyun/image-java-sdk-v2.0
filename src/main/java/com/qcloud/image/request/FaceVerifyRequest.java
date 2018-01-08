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
 * @author serenazhao 人脸验证请求
 */
public class FaceVerifyRequest extends AbstractBaseRequest {      
        //是否是url
        private boolean isUrl = true;
        
    	// url
	private String url = "";
        
	// 图片内容列表
        private File image;
        
        //要查询的个人Id
         private String personId = "";
              
        public FaceVerifyRequest(String bucketName, String personId, String url) {
		super(bucketName);
		this.isUrl = true;
                this.personId = personId;
                this.url = url;
	}
 
        public FaceVerifyRequest(String bucketName, String personId, String name, File image) {
		super(bucketName);
		this.isUrl = false;
                this.personId = personId;
                this.image = image;             
	}
        
        public boolean isUrl() {
            return isUrl;
        }
        
        public String getUrl() {
            return url;
        }
        
        public String getPersonId() {
            return personId;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public File getImage() {
            return image;
        }

        public void setImage(File image) {
            this.image = image;
        }

	@Override
	public void check_param() throws ParamException {
		super.check_param();
                if(isUrl){
                    CommonParamCheckUtils.AssertNotNull("url", url);
                }else{
                    CommonParamCheckUtils.AssertNotNull("image content", image);
                }
	}
}
