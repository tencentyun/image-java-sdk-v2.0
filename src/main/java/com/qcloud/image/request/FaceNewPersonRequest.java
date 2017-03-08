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

/**
 *
 * @author serenazhao  个体创建
 */
public class FaceNewPersonRequest extends AbstractBaseRequest {
        //是否是url
        private boolean isUrl = true;
        
    	// url
	private String url = "";
        
	// 图片内容列表
        private String image = "";
        
        //加入到组的列表
        private String[] groupIds;
        
        //指定的个体id 
        private String personId = "";
        
        //指定的个体名字
        private String personName = "";
        
        //指定的个体标签
        private String personTag = "";
        
        public FaceNewPersonRequest(String bucketName, String personId, String[] groupIds, String url, String personName, String personTag) {
		super(bucketName);
		this.isUrl = true;
                this.url = url;
                this.groupIds = groupIds;
                this.personId = personId;
                this.personName = personName;
                this.personTag = personTag;
	}

        public FaceNewPersonRequest(String bucketName, String personId, String[] groupIds, String fileName, String image, String personName, String personTag) {
		super(bucketName);
		this.isUrl = false;
                this.image = image;
                this.groupIds = groupIds;
                this.personId = personId;
                this.personName = personName;
                this.personTag = personTag;            
	}
        
        public boolean isUrl() {
            return isUrl;
        }
        
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
        
        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }
        
        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }
        
        public String getPersonTag() {
            return personTag;
        }

        public void setPersonTag(String personTag) {
            this.personTag = personTag;
        }
        
        public String[] getGroupIds() {
            return groupIds;
        }

        public void setGroupIds(String[] groupIds) {
            this.groupIds = groupIds;
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
