package com.qcloud.image.exception;

// 网络异常(如网络故障，导致无法连接服务端)
public class NetworkException extends AbstractImageException {

    private static final long serialVersionUID = -6662661467437143397L;

    public NetworkException(String message) {
        super(ImageExceptionType.NETWORK_EXCEPITON, message);
    }

}
