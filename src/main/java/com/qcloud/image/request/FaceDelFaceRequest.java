/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcloud.image.request;

import com.qcloud.image.common_utils.CommonParamCheckUtils;
import com.qcloud.image.exception.ParamException;

/**
 *
 * @author serenazhao 删除人脸
 */
public class FaceDelFaceRequest extends AbstractBaseRequest {
    
    	//个体Id
	private String personId = "";
        
         //要删除的人脸ID
        private String[] faceIds;
              
        public FaceDelFaceRequest(String bucketName, String personId, String[] faceIds) {
		super(bucketName);
		this.personId = personId;
                this.faceIds = faceIds;
	}

        public String getPersonId() {
            return personId;
        }
        
        public String[] getFaceIds() {
            return faceIds;
        }
        

	@Override
	public void check_param() throws ParamException {
		super.check_param();
                CommonParamCheckUtils.AssertNotNull("personId", personId);
	}
}
