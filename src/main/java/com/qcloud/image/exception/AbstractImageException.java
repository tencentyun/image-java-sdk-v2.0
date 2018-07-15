package com.qcloud.image.exception;

import com.qcloud.image.http.ResponseBodyKey;

import org.json.JSONObject;

/**
 * 封装cos异常
 * @author chengwu
 *
 */
public abstract class AbstractImageException extends Exception {

    private static final long serialVersionUID = 7547532865194837136L;
    
    private ImageExceptionType type;

    public AbstractImageException(ImageExceptionType type, String message) {
        super(message);
        this.type = type;
    }

    public ImageExceptionType getType() {
        return type;
    }
    
    @Override
    public String toString() {
        JSONObject responseObj = new JSONObject();
        responseObj.put(ResponseBodyKey.CODE, type.getErrorCode());
        responseObj.put(ResponseBodyKey.MESSAGE, getMessage());
        return responseObj.toString();
    }
    
}
