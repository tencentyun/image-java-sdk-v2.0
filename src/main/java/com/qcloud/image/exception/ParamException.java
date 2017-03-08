package com.qcloud.image.exception;

// 参数异常
public class ParamException extends AbstractImageException {

    private static final long serialVersionUID = 216921496331691543L;

    public ParamException(String message) {
        super(ImageExceptionType.PARAM_EXCEPTION, message);
    }
    
}
