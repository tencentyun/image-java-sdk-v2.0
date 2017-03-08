/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcloud.image.request;

/**
 *
 * @author serenazhao  获取人脸列表
 */
public class FaceLiveGetFourRequest extends AbstractBaseRequest {
        
        //指定一个sessionId，若使用，请确保id唯一
        private String seq;
        
        public FaceLiveGetFourRequest(String bucketName, String seq) {
		super(bucketName);
                this.seq = seq;
	}
        
        public String getSeq() {
                return seq;              
        }
}
