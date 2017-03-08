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
 * @author serenazhao设置信息
 */
public class FaceSetInfoRequest extends AbstractBaseRequest {
    
    	//个体Id
	private String personId = "";
        
        //指定的个体名字
        private String personName = "";
        
        //指定的个体标签
        private String personTag = "";
              
        public FaceSetInfoRequest(String bucketName, String personId, String personName, String personTag) {
		super(bucketName);
		this.personId = personId;
                this.personName = personName;
                this.personTag = personTag;
	}
        
        public String getPersonId() {
            return personId;
         }
                
        public String getPersonName() {
            return personName;
        }
        
        public String getPersonTag() {
            return personTag;
        }
        
	@Override
	public void check_param() throws ParamException {
		super.check_param();
                CommonParamCheckUtils.AssertNotNull("personId", personId);
	}
}
