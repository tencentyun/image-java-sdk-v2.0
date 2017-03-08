package com.qcloud.image.http;
/**
 * @author chengwu
 * 封装HTTP请求头中的对应的K-V对中value枚举值类
 */

public class RequestHeaderValue {
    public static class Method {
        public static final String POST = "POST";
        public static final String GET = "GET";
        public static final String PUT = "PUT";
        public static final String DELETE = "DELETE";
    }
    
    public static class ContentType {
        public static final String JSON = "application/json";
        public static final String FORM_DATA = "multipart/form-data";
    }
    
    public static class Accept {
        public static final String ALL = "*/*";
    }
    
    public static class UserAgent {
        public static final String Image_FLAG = "qcloud-java-sdk";
    }
    
    public static class Connection {
        public static final String KEEP_ALIVE = "Keep-Alive";
    }
    
}
