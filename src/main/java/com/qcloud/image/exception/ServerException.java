package com.qcloud.image.exception;

// 服务端异常(如返回404deng)
public class ServerException extends AbstractImageException {

    private static final long serialVersionUID = -4536038808919814914L;

    public ServerException(String message) {
        super(ImageExceptionType.SERVER_EXCEPTION, message);
    }

}
