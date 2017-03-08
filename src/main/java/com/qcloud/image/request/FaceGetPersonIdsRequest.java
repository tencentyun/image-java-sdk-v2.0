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
 * @author serenazhao  获取人列表
 */
public class FaceGetPersonIdsRequest extends AbstractBaseRequest {
        
        //要查询的组Id
        private String groupId;
        
        public FaceGetPersonIdsRequest(String bucketName, String groupId) {
		super(bucketName);
                this.groupId = groupId;
	}
        
        public String getGroupId() {
                return groupId;              
        }

	@Override
	public void check_param() throws ParamException {
		super.check_param();
                CommonParamCheckUtils.AssertNotNull("groupId", groupId);
	}
}
