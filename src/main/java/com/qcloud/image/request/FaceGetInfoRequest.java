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
 * @author serenazhao 个体获取信息
 */
public class FaceGetInfoRequest extends AbstractBaseRequest {
    
    	//个体Id
	private String personId = "";
        
        public FaceGetInfoRequest(String bucketName, String personId) {
		super(bucketName);
		this.personId = personId;
	}
        
        public String getPersonId() {
            return personId;
         }              
        
	@Override
	public void check_param() throws ParamException {
		super.check_param();
                CommonParamCheckUtils.AssertNotNull("personId", personId);
	}
}
