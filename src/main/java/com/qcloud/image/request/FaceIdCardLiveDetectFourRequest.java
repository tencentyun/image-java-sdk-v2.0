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
 * @author serenazhao 对比指定身份信息请求
 */
public class FaceIdCardLiveDetectFourRequest extends AbstractBaseRequest {     
	// 图片内容
        private File video ;
        
        //要查询的身份证号码
         private String idcardNumber = "";
         
         //要查询的身份证名字
         private String idcardName = "";
         
         //验证码
         private String validate = "";
         
         //seq
         private String seq = "";
         
        
        public FaceIdCardLiveDetectFourRequest( String bucketName, String validate, File video, String idcardNumber, String idcardName, String seq) {
		super(bucketName);
                this.idcardNumber = idcardNumber;
                this.idcardName = idcardName;
                this.validate = validate;
                this.seq = seq;
                this.video = video;               
	}

       public String getIdcardNumber() {
                return idcardNumber;
        }
        
        public String getIdcardName() {
                return idcardName;
        }
        
        public String getValidate() {
                return validate;
        }
        
        public File getVideo() {
                return video;
        }
        
        public String getSeq() {
                return seq;
        }

	@Override
	public void check_param() throws ParamException {
		super.check_param();
                CommonParamCheckUtils.AssertNotNull("idcardName", idcardName);
                CommonParamCheckUtils.AssertNotNull("idcardNumber", idcardNumber);
                CommonParamCheckUtils.AssertNotNull("validate", validate);
	}
}
