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
 * @author serenazhao  个体创建
 */
public class FaceNewPersonRequest extends AbstractBaseRequest {
        //是否是url
        private boolean isUrl = true;
        
    	// url
	private String url = "";
        
	// 图片内容列表
        private File image;
        
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

        public FaceNewPersonRequest(String bucketName, String personId, String[] groupIds, File image, String personName, String personTag) {
		super(bucketName);
		this.isUrl = false;
                this.image = image;
                this.groupIds = groupIds;
                this.personId = personId;
                this.personName = personName;
                this.personTag = personTag;            
	}

    public FaceNewPersonRequest(String bucketName, String personId, String[] groupIds, byte[] image, String personName, String personTag) {
        super(bucketName);
        this.isUrl = false;
        setBytesContent("image", image);
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

        public File getImage() {
            return image;
        }

        public void setImage(File image) {
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
                    //CommonParamCheckUtils.AssertNotNull("image content", image);
                }
	}
}
