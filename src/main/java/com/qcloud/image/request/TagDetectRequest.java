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
 * @author serenazhao 标签识别请求
 */
public class TagDetectRequest extends AbstractBaseRequest {
        //标签识别的类型，是否是url识别
        private boolean isUrl;

    	// url
	private String url = "";
        
	// 图片内容
        private File image;
        
	public TagDetectRequest(String bucketName, String url) {
            super(bucketName);
            this.isUrl = true;
            this.url = url;
	}

        public TagDetectRequest(String bucketName, File image) {
            super(bucketName);
            this.isUrl = false;
            this.image = image;
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
