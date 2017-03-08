package com.qcloud.image;

/**
 * @author chengwu
 * cos返回给用户的错误码信息, code为0为成功
 * 此处的错误码是SDK包括:用户的参数错误(如路径不符合), 网络错误(无法和cos服务端通信), 服务端故障, 其他未知错误
 */
public class ErrorCode {
    public static final int PARAMS_ERROR = -1;      // 参数错误
    public static final int NETWORK_ERROR = -2;     // 网络错误
    public static final int SERVER_ERROR = -3;      // 服务端故障
    public static final int UNKNOWN_ERROR = -4;     // 其他未知错误
}
