package com.qcloud.image.request;

import com.qcloud.image.common_utils.CommonParamCheckUtils;
import com.qcloud.image.exception.ParamException;
/**
 * @author chengwu
 * 封装了请求包含的基本元素
 */
public abstract class AbstractBaseRequest {
    // bucket名
    private String bucketName;

    public AbstractBaseRequest(String bucketName) {
        super();
        this.bucketName = bucketName;
    }

    // 获取bucket名
    public String getBucketName() {
        return bucketName;
    }

    // 设置bucket名
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    
    protected String getMemberStringValue(String member) {
    	if (member != null) {
            return member;
        } else {
            return "null";
    	}
    }
    
    // 将request转换为字符串, 用于记录信息
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("bucketName:").append(getMemberStringValue(bucketName));
        return sb.toString();
    }
     
    // 检查用户的输入参数
    public void check_param() throws ParamException {
    	CommonParamCheckUtils.AssertNotNull("bucketName", this.bucketName);
    }
}
